import { createRoot } from 'react-dom/client'
import { BrowserRouter } from 'react-router'
import { Toaster } from 'react-hot-toast'
import { ChatProvider } from './context/ChatContext.jsx'
import { GroupProvider } from './context/GroupContext.jsx'
import "./index.css";
import AppRoutes from './routes/routes.jsx'

createRoot(document.getElementById('root')).render(
  <BrowserRouter>
    <Toaster position="top-center"/>
    <ChatProvider>
      <GroupProvider>
        <AppRoutes />
      </GroupProvider>
    </ChatProvider>
  </BrowserRouter>
)
