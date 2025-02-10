import { createContext, useContext, useState } from "react";

const ChatContext = createContext();

export const ChatProvider = ({ children }) => {
    const [currentUser, setCurrentUser] = useState("6796275e426f0c753206d295");
    const [isUserConnected, setIsUserConnected] = useState(false);
    const [stompClient, setStompClient] = useState(null);

    return (
        <ChatContext.Provider
            value={
                {
                    currentUser,
                    setCurrentUser,
                    isUserConnected,
                    setIsUserConnected,
                    stompClient,
                    setStompClient
                }
            }>
            {children}
        </ChatContext.Provider>
    );
};

const chatContext = () => useContext(ChatContext);
export default chatContext;