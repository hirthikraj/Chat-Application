import SideBar from './SideBar';
import MessageBar from  "./MessageBar"
import { useNavigate } from 'react-router';
import chatContext from '../context/ChatContext';
import { useEffect } from 'react';
const Chat = () => {

    const {
        isUserConnected
    } = chatContext();

    const navigate = useNavigate();
    useEffect(() => {
        if (!isUserConnected) {
            navigate("/login");
        }
    }, [isUserConnected]);

    return (
        <div className="bg-blue-500 h-dvh flex flex-row">
            <SideBar/>
            <MessageBar/>
        </div>
    );
};

export default Chat;