import chatContext from "../context/ChatContext";

export const Message = ({message}) => {
    const {
        currentUser
      } = chatContext();
    
    const isMyMessage = currentUser === message.senderId;
    return (
        <div className={`bg-blue-200  ${isMyMessage ? 'rounded-l-xl rounded-br-xl' : 'rounded-r-xl rounded-bl-xl'} m-3 min-w-1/4 max-w-3/4 min-h-10 ${isMyMessage === true ? 'place-self-end' : 'place-self-start'}`}>
            {
                !isMyMessage && <p className="font-mono text-xs ml-2 text-blue-700">{message.senderId}</p>
            }
            <p className="font-mono text-md m-1 font-medium text-blue-700">{message.content}</p>
        </div>
    );
};

export default Message;