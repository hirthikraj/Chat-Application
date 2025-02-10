import { createContext, useContext, useState } from "react";

const GroupContext = createContext();

export const GroupProvider = ({ children }) => {
    const [currentGroupId, setCurrentGroupId] = useState("");
    const [currentGroupName, setCurrentGroupName] = useState("");

    return (
        <GroupContext.Provider
            value={
                {
                    currentGroupId,
                    setCurrentGroupId,
                    currentGroupName,
                    setCurrentGroupName,
                }
            }>
            {children}
        </GroupContext.Provider>
    );
};

const groupContext = () => useContext(GroupContext);
export default groupContext;