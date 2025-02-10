import React, { useState, useEffect } from "react";
import Group from "./Group";
import { getAllGroupsForUser } from "../services/UserService";
import chatContext from "../context/ChatContext";
import { useNavigate } from "react-router";

const SideBar = () => {
    const [groups, setGroups] = useState([]);
    
    const {
        currentUser,
        isUserConnected
    } = chatContext();

    const navigate = useNavigate();

    useEffect(() => {
        async function loadGroups(userId) {
            try {
                const groupsData = await getAllGroupsForUser(userId);
                setGroups(groupsData.pageData);
            } catch (error) {
                console.error(error);
            }
        }
        if(isUserConnected)
        {
            loadGroups(currentUser);
        }
    }, []); 

    return (
        <div className="flex flex-col place-content-between bg-blue-200 m-2 basis-1/4 md:basis-1.5/3 rounded-md">
            <p className="text-blue-600 text-center font-mono p-2 m-2 text-xl">Groups</p>
            <div className="flex flex-col">
                {
                    groups.map(group => (
                        <Group key={group.groupId} group={group}/>
                    ))
                }
            </div>

            <div>
                <button className="bg-blue-500 shadow-lg shadow-blue-300 hover:bg-blue-400 m-3 p-4 rounded-full cursor-pointer" onClick={() => {navigate("/group")}}>
                    <p className="font-mono text-white text-base antialiased font-stretch-expanded font-medium tracking-wide text-center">Create Group</p>
                </button>
            </div>
        </div>
    );
};

export default SideBar;
