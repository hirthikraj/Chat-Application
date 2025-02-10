import { useState } from "react";

const ChatMessageTyper = ({sendMessage}) => {

    const [input,setInput] = useState("");

    const callSendMessage = (e) => {
        e.preventDefault();
        sendMessage(input);
        setInput("");
    }

    return (
        <form className="bg-blue-200 h-1/10 rounded-md flex" onSubmit={callSendMessage}>
            <input 
                    value={input}
                    onChange={(e) => {
                        setInput(e.target.value);
                    }}
                    onKeyDown={(e) => {
                        if (e.key === "Enter") {
                            sendMessage();
                        }
                    }}
                type="text" className="rounded-xl px-4 py-4 m-3 w-9/10" placeholder="Type a message"/> 
            <button type="submit" className="font-mono text-xl bg-blue-500 shadow-lg shadow-blue-300 hover:bg-green-500 m-3 rounded-full w-1/10 text-white cursor-pointer" onClick={callSendMessage}>SEND</button>
        </form>
    );
};

export default ChatMessageTyper;

