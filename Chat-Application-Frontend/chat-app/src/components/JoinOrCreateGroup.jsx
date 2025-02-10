import { useEffect, useState } from "react";
import { createOrJoinGroup } from "../services/GroupService";
import chatContext from "../context/ChatContext";
import { useNavigate } from "react-router";
import { toast } from "react-hot-toast";
import groupContext from "../context/GroupContext";


export const JoinOrCreateGroup = () => {

    const {
        isUserConnected,
        currentUser
    } = chatContext();

    const {
        setCurrentGroupId,
        setCurrentGroupName
    } = groupContext();

    const [groupForm, setGroupForm] = useState({
        groupName: '',
        userId: currentUser
    });

    const navigate = useNavigate();
    useEffect(() => {
        if (!isUserConnected) {
            navigate("/login");
        }
    }, [isUserConnected]);
    
    const handleSubmit = (e) => {
        e.preventDefault();
        const joinOrCreate = async () => {
            try {
                const groupData = await createOrJoinGroup(groupForm);
                setCurrentGroupId(groupData.data.groupId);
                setCurrentGroupName(groupData.data.groupName);
                navigate("/chat");
            } catch (error) {
                toast.error("Invalid group name");
            }
        };

        joinOrCreate();
    };

    const handleChange = (e) => {
        const { name, value } = e.target;
        setGroupForm({
            ...groupForm,
            [name]: value
        });
    };

    return (
        <div className="bg-blue-500 h-screen flex justify-center items-center">
            <div className="bg-white p-4 m-4 rounded-lg shadow-lg">
                <h1 className="text-2xl font-bold text-center">Join / Create Group</h1>
                <form className="flex flex-col gap-4 mt-4" onSubmit={handleSubmit}>
                    <input type="text" placeholder="Group Name" className="p-2 rounded-lg border border-gray-300" name="groupName" value={groupForm.groupName} onChange={handleChange}/>
                    <button type="submit" className="p-2 bg-blue-500 text-white rounded-lg cursor-pointer">Join / Create</button>
                </form>
            </div>
        </div>
    );
};

export default JoinOrCreateGroup;