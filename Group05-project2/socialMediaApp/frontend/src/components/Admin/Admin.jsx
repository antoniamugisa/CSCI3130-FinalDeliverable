import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from "react-router-dom";

const Admin = () => {
    const [email, setEmail] = useState("");
    const [username, setUsername] = useState("");
    const [message, setMessage] = useState("");
    const [pendingRequests, setPendingRequests] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchPendingRequests = async () => {
            try {
                const response = await axios.get("http://localhost:8080/api/pending-requests");
                setPendingRequests(response.data);
            } catch (error) {
                console.error("Error fetching pending requests", error);
                setMessage("Error fetching pending requests");
            }
        };

        fetchPendingRequests();
    }, []);

    const handleAddUser = async () => {
        try {
            const response = await axios.post("http://localhost:8080/api/add-user", {
                email,
                username,
            });
            setMessage(response.data);
        } catch (error) {
            console.error("Error adding user", error);
            setMessage("Error adding user");
        }
    };

    const handleRemoveUser = async () => {
        try {
            const response = await axios.delete("http://localhost:8080/api/remove-user", {
                params: { email }
            });
            setMessage(response.data);
        } catch (error) {
            console.error("Error removing user", error);
            setMessage("Error removing user");
        }
    };

    const handleApprove = async (email) => {
        try {
            const response = await axios.post("http://localhost:8080/api/approve-user", null, {
                params: { email }
            });
            setMessage(response.data);
            // Optionally refetch pending requests
        } catch (error) {
            console.error("Error approving user", error);
            setMessage("Error approving user");
        }
    };

    const handleReject = async (email) => {
        try {
            const response = await axios.post("http://localhost:8080/api/reject-user", null, {
                params: { email }
            });
            setMessage(response.data);
            // Optionally refetch pending requests
        } catch (error) {
            console.error("Error rejecting user", error);
            setMessage("Error rejecting user");
        }
    };

    const goToHome = () => {
        navigate("/home");
    };

    return (
        <div>
            <h2>Admin</h2>
            <div>
                <h3>Add User</h3>
                <input
                    type="text"
                    placeholder="Username"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                />
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <button onClick={handleAddUser}>Add User</button>
            </div>
            <div>
                <h3>Remove User</h3>
                <input
                    type="email"
                    placeholder="Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <button onClick={handleRemoveUser}>Remove User</button>
            </div>
            <div>
                <h3>Pending Requests</h3>
                {pendingRequests.map((user) => (
                    <div key={user.email}>
                        <span>{user.username} ({user.email})</span>
                        <button onClick={() => handleApprove(user.email)}>Approve</button>
                        <button onClick={() => handleReject(user.email)}>Reject</button>
                    </div>
                ))}
            </div>
            {message && <p>{message}</p>}
            <button className="home-button" onClick={goToHome}>Back to Home</button>
        </div>
    );
};

export default Admin;
