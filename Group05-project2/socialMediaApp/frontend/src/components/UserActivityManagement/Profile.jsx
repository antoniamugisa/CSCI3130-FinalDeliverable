import React, { useState, useEffect } from "react";
import axios from "axios";
import "../css/Profile.css";
import { useNavigate } from "react-router-dom";

const Profile = () => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [interests, setInterests] = useState("");
    const [status, setStatus] = useState("");
    const [statusOptions] = useState(["","Away", "Busy", "Available"]);
    const [role, setRole] = useState("");
    const [roleOptions] = useState(["","Employee", "Student"]);
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    useEffect(() => {
        // Fetch user data from the server
        const fetchUserData = async () => {
            try {
                const userEmail = localStorage.getItem("userEmail");
                if (userEmail) {
                    const response = await axios.get("http://localhost:8080/api/user-profile", {
                        params: { email: userEmail }
                    });
                    const userData = response.data;
                    setUsername(userData.username);
                    setEmail(userData.email);
                    setInterests(userData.interests);
                    setStatus(userData.status);
                    setRole(userData.role);
                } else {
                    setMessage("User email not found. Please log in again.");
                }
            } catch (error) {
                console.error("Error fetching user data", error);
            }
        };
        fetchUserData();
    }, []);

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post("http://localhost:8080/api/update-profile", {
                username,
                email,
                interests,
                status,
                role
            });
            if (response.data === "Profile updated successfully") {
                setMessage("Profile updated successfully");
            } else {
                setMessage("Error updating profile");
            }
        } catch (error) {
            console.error("Error updating profile", error);
            setMessage("Error updating profile");
        }
    };

    const goToHome = () => {
        navigate("/home");
    };

    return (
        <div className="profile-container">
            <h2>User Profile</h2>
            <form onSubmit={handleSubmit}>
                <div className="input-group">
                    <label>Username</label>
                    <input
                        type="text"
                        placeholder="Username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div className="input-group">
                    <label>Email</label>
                    <input
                        type="email"
                        placeholder="Email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        readOnly
                    />
                </div>
                <div className="input-group">
                    <label>Interests</label>
                    <textarea
                        placeholder="Interests"
                        value={interests}
                        onChange={(e) => setInterests(e.target.value)}
                        required
                    ></textarea>
                </div>
                <div className="input-group">
                    <label>Status</label>
                    <select value={status} onChange={(e) => setStatus(e.target.value)} required>
                        {statusOptions.map((status) => (
                            <option key={status} value={status}>
                                {status}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="input-group">
                    <label>Role</label>
                    <select value={role} onChange={(e) => setRole(e.target.value)} required>
                        {roleOptions.map((role) => (
                            <option key={role} value={role}>
                                {role}
                            </option>
                        ))}
                    </select>
                </div>
                <button className="home-button" type="submit">Save</button>
                {message && <p className="message">{message}</p>}
            </form>
            <button className="home-button" onClick={goToHome}>Back to Home</button>
        </div>
    );
};

export default Profile;
