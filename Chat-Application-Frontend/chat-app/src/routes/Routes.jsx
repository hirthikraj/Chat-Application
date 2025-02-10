import React from "react";
import {Routes,Route} from "react-router";
import App from "../App";
import Chat from "../components/Chat";
import Login from "../components/Login";
import JoinOrCreateGroup from "../components/JoinOrCreateGroup";

const AppRoutes = () => {
    return (
        <Routes>
            <Route path="/" element={<App />} />
            <Route path="/login" element={<Login/>} />
            <Route path="/chat" element={<Chat/>} />
            <Route path="/group" element={<JoinOrCreateGroup />} />
        </Routes>
    );
};

export default AppRoutes;
