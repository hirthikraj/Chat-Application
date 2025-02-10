import { useEffect } from "react";
import { useNavigate } from "react-router";
import chatContext from "./context/ChatContext";

function App() {
  const {
    isUserConnected,
  } = chatContext();
  
  const navigate = useNavigate();
  useEffect(() => {
    if (!isUserConnected) {
      navigate("/login");
    }
    else
    {
      navigate("/chat");
    }
  }, [isUserConnected]);
}

export default App;
