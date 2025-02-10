import groupContext from "../context/GroupContext";

const ChatGroupHeader = () => {
    const {
        currentGroupName
      } = groupContext();

    return (
        <div className="bg-blue-200 m-2 p-4 rounded-md">
            <p className="font-mono text-blue-600 text-lg antialiased font-stretch-expanded font-medium tracking-wide text-left">{currentGroupName}</p>
        </div>
    );
};

export default ChatGroupHeader;