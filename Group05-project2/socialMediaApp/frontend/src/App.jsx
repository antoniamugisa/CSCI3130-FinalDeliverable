import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import SignUp from './components/UserAccount/SignUp';
import Login from './components/UserAccount/Login';
import ForgotPassword from './components//UserAccount/ForgotPassword';
import Home from './components/UserActivityManagement/Home';
import ProtectedRoute from './components/UserAccount/ProtectedRoute';
import Profile from './components/UserActivityManagement/Profile';
import FriendsList from './components/UserActivityManagement/FriendsList';
import AddFriend from './components/UserActivityManagement/AddFriend';
import DeleteFriend from './components/UserActivityManagement/DeleteFriend';
import FriendProfile from './components/UserActivityManagement/FriendProfile';
import Admin from './components/Admin/Admin';

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/signup" element={<SignUp />} />
                <Route path="/login" element={<Login />} />
                <Route path="/forgot-password" element={<ForgotPassword />} />
                <Route path="/" element={<ProtectedRoute><Home /></ProtectedRoute>} />
                <Route path="/home" element={<ProtectedRoute><Home /></ProtectedRoute>} />
                <Route path="/profile" element={<ProtectedRoute><Profile /></ProtectedRoute>} />
                <Route path="/friends" element={<ProtectedRoute><FriendsList /></ProtectedRoute>} />
                <Route path="/add-friend" element={<ProtectedRoute><AddFriend /></ProtectedRoute>} />
                <Route path="/delete-friend" element={<ProtectedRoute><DeleteFriend /></ProtectedRoute>} />
                <Route path="/friend-profile/:id" element={<ProtectedRoute><FriendProfile /></ProtectedRoute>} />
                <Route path="/admin" element={<ProtectedRoute><Admin /></ProtectedRoute>} />

            </Routes>
        </Router>
    );
}

export default App;
