import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import '../css/FriendList.css';
import axios from "axios";

const FriendsList = () => {
    const [friends, setFriends] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [searchInterest, setSearchInterest] = useState("");
    const [filteredFriends, setFilteredFriends] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        fetchFriends();
    }, []);

    const fetchFriends = async () => {
        try {
            const userEmail = localStorage.getItem("userEmail");
            const response = await axios.get("http://localhost:8080/api/friends", {
                params: { userEmail }
            });
            setFriends(response.data);
            setFilteredFriends(response.data); // 初始显示所有朋友
        } catch (error) {
            console.error("Error fetching friends", error);
        }
    };

    const filterFriends = () => {
        const filtered = friends.filter(friend =>
            (friend.username.toLowerCase().includes(searchTerm.toLowerCase()) ||
                friend.email.toLowerCase().includes(searchTerm.toLowerCase())) &&
            (searchInterest === "" || friend.interests.toLowerCase().includes(searchInterest.toLowerCase()))
        );
        setFilteredFriends(filtered);
    };

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const handleInterestChange = (event) => {
        setSearchInterest(event.target.value);
    };

    const handleSearchSubmit = (event) => {
        event.preventDefault();  // 防止默认提交行为
        filterFriends();
    };

    const goToHome = () => {
        navigate("/home");
    };

    const viewFriendProfile = (id) => {
        navigate(`/friend-profile/${id}`);
    };

    return (
        <div className="friends-list-container">
            <h2>My Friends</h2>
            <form onSubmit={handleSearchSubmit}>
                <input
                    type="text"
                    placeholder="Search friends..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
                <input
                    type="text"
                    placeholder="Search by interests..."
                    value={searchInterest}
                    onChange={handleInterestChange}
                />
                <button type="submit">Search</button>
            </form>
            <ul>
                {filteredFriends.map((friend) => (
                    <li key={friend.id}>
                        {friend.username} ({friend.email})
                        <button onClick={() => viewFriendProfile(friend.id)}>View Profile</button>
                    </li>
                ))}
            </ul>
            <button className="home-button" onClick={goToHome}>Back to Home</button>
        </div>
    );
};

export default FriendsList;




