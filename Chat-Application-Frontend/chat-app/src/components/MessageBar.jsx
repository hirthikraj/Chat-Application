import ChatGroupHeader from './ChatGroupHeader';
import ChatBox from './ChatBox';
import ChatMessageTyper from './ChatMessageTyper';
import chatContext from '../context/ChatContext';
import { useEffect, useState } from 'react';
import { getAllMessagesForGroup } from "../services/GroupService";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { baseURL } from "../helper/AxiosHelper";
import groupContext from '../context/GroupContext';

const MessageBar = () => {
    const {
        currentUser,
        isUserConnected,
        setStompClient,
        stompClient
    } = chatContext();

    const {
        currentGroupId
    } = groupContext();

    const [messages, setMessages] = useState([]);
    const [subscription,setSubscription] = useState(null);

    // Load messages when the group changes
    useEffect(() => {
        async function loadMessages() {
            if (!currentGroupId) return; // No group selected
            try {
                const messagesData = await getAllMessagesForGroup(currentGroupId);
                setMessages(messagesData.pageData);
            } catch (error) {
                console.error("Error loading messages:", error);
            }
        }
        console.log("LoadMessages - Current group Id:", currentGroupId);
        loadMessages();
    }, [currentGroupId]);

    // Initialize WebSocket connection
    useEffect(() => {

        const connectWebSocket = () => {
            const sock = new SockJS(`${baseURL}/chat`);
            const client = Stomp.over(sock);
            setStompClient(client);

            client.connect({}, () => {
                console.log("Stomp client connected");
                setSubscription(client.subscribe(`/topic/user/${currentUser}`, (message) => {
                    console.log(currentGroupId);

                    if(message.groupId === currentGroupId)
                    {
                        const newMessage = JSON.parse(message.body);
                        setMessages((prevMessages) => [...prevMessages, newMessage]);
                    }
                    
                }));
            });
        };

        if(isUserConnected)
        {
            connectWebSocket();
        }

        return () => {
            if (stompClient && subscription) {
                stompClient.disconnect(() => {
                    console.log("WebSocket disconnected");
                });
                setStompClient(null);
                subscription.unsubscribe();
                setSubscription(null);
            }
            setMessages([]);
        };

    }, []);

    // Send message logic
    const sendMessage = (input) => {
        if (!stompClient) {
            console.error("WebSocket client not connected");
            return;
        }
        const message = {
            groupId: currentGroupId,
            senderId: currentUser,
            content: input
        };
        stompClient.send(`/app/sendMessage/${currentGroupId}`, {}, JSON.stringify(message));
    };

    return (
        <div className="basis-3/4 m-2">
            <div className="bg-blue-500 h-full">
                {
                    currentGroupId === "" ?
                        <div className="flex flex-col items-center justify-center h-full">
                            <h1 className="text-3xl text-white">Welcome to Chat App</h1>
                            <p className="text-white">Please select a group to start chatting</p>
                        </div> :
                        <div className="flex flex-col h-full">
                            <ChatGroupHeader />
                            <ChatBox messages={messages} />
                            <ChatMessageTyper sendMessage={sendMessage} />
                        </div>
                }
            </div>
        </div>
    );
};

export default MessageBar;
