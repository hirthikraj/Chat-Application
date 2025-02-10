import chatContext from "../context/ChatContext";
import groupContext from "../context/GroupContext";


const Group = ({group}) => {
    const {
        currentGroupId,
        setCurrentGroupId,
        setCurrentGroupName
    } = groupContext();

    const openGroupChat = (e) => {
        e.preventDefault();
        setCurrentGroupId(group.groupId);
        setCurrentGroupName(group.groupName);
        console.log(currentGroupId);
    }
    
    return (
        <button className="bg-blue-500 shadow-lg shadow-blue-300 hover:bg-blue-400 m-3 p-4 rounded-full cursor-pointer" onClick={openGroupChat}>
            <p className="font-mono text-white text-base antialiased font-stretch-expanded font-medium tracking-wide text-center">{group.groupName}</p>
        </button>
    );
}

export default Group;