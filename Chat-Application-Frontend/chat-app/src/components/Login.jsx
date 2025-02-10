import { useState } from "react";
import { createNewUser } from "../services/UserService";
import chatContext from "../context/ChatContext";
import { useNavigate } from "react-router";
import { toast } from "react-hot-toast";

export const Login = () => {

    const [userForm, setUserForm] = useState({
        userName: '',
        password: ''
    });

    const {
        setIsUserConnected,
        setCurrentUser
    } = chatContext();

    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        const createUser = async () => {
            try {
                const newUserData = await createNewUser(userForm);
                setIsUserConnected(true);
                setCurrentUser(newUserData.data.userName);
                navigate("/chat");
            } catch (error) {
                toast.error("Invalid username or password");
            }
        };

        createUser();
    };

    const handleFormChange = (e) => {
        const { name, value } = e.target;
        setUserForm({
            ...userForm,
            [name]: value
        });
    };

    return (
        <div className="bg-blue-500 h-screen flex justify-center items-center">
            <div className="bg-white p-4 rounded-lg shadow-lg">
                <h1 className="text-2xl font-bold text-center">Login / Register</h1>
                <form className="flex flex-col gap-4 mt-4" onSubmit={handleSubmit}>
                    <input type="text" placeholder="Username" className="p-2 rounded-lg border border-gray-300" name="userName" value={userForm.userName} onChange={handleFormChange} />
                    <input type="password" placeholder="Password" className="p-2 rounded-lg border border-gray-300" name="password" value={userForm.password} onChange={handleFormChange} />
                    <button type="submit" className="p-2 bg-blue-500 text-white rounded-lg cursor-pointer" onClick={handleSubmit}>Login / Register</button>
                </form>
            </div>
        </div>
    );
};

export default Login;