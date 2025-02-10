import { useEffect, useState } from "react";
import Message from "./Message";
import { getAllMessagesForGroup } from "../services/GroupService";
import chatContext from "../context/ChatContext";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";
import { baseURL } from "../helper/AxiosHelper";

const ChatBox = ({messages}) => {

return (
    <div className="bg-blue-500 rounded-sm h-8/10 flex flex-col">
        {
            messages.map((message,index) => (
                <Message key={index} message={message} />
            ))
        }
    </div>
);
};

export default ChatBox;