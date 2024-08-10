import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate, useParams } from 'react-router-dom';
import '../css/FriendProfile.css';

const FriendProfile = () => {
    const [friend, setFriend] = useState(null);
    const { id } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        const fetchFriendProfile = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/api/user-profile/${id}`);
                setFriend(response.data);
            } catch (error) {
                console.error("Error fetching friend profile", error);
            }
        };

        fetchFriendProfile();
    }, [id]);

    const goToHome = () => {
        navigate("/home");
    };

    if (!friend) {
        return <div>Loading...</div>;
    }

    return (
        <div className="friend-profile-container">
            <h2>{friend.username}'s Profile</h2>
            <p>Email: {friend.email}</p>
            <p>Interests: {friend.interests}</p>
            <p>Status: {friend.status}</p>
            <p>Role: {friend.role}</p>
            <button className="home-button" onClick={goToHome}>Back to Home</button>
        </div>
    );
};

export default FriendProfile;

