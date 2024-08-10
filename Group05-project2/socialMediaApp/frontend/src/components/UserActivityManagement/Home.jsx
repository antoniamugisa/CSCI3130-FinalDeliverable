import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import '../css/Home.css';
import axios from 'axios';

const Home = () => {
    const navigate = useNavigate();
    const [username, setUsername] = useState('');
    const [searchTerm, setSearchTerm] = useState('');
    const [searchResult, setSearchResult] = useState(null);
    const [message, setMessage] = useState('');

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
                }
            } catch (error) {
                console.error("Error fetching user data", error);
            }
        };
        fetchUserData();
    }, []);

    const handleLogout = () => {
        localStorage.removeItem("isLoggedIn");
        localStorage.removeItem("userEmail");
        navigate("/login");
    }

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    const handleSearchSubmit = async () => {
        try {
            const response = await axios.get("http://localhost:8080/api/search-user", {
                params: { email: searchTerm }
            });
            setSearchResult(response.data);
            setMessage('');
        } catch (error) {
            console.error("Error searching user", error);
            setMessage('No user found with that email');
            setSearchResult(null);
        }
    };

    const goToProfile = () => {
        navigate("/profile");
    }

    const goToAddFriend = () => {
        navigate("/add-friend");
    }

    const goToDeleteFriend = () => {
        navigate("/delete-friend");
    }

    const goToFriendsList = () => {
        navigate("/friends");
    }

    const admin = () => {
        navigate("/admin");
    }

    return (
        <div className="home-container">
            <h1>Welcome to the Home Page, {username}</h1>
            <div className="button-group">
                <button onClick={goToProfile}>Profile</button>
                <button onClick={goToAddFriend}>Add Friend</button>
                <button onClick={goToDeleteFriend}>Delete Friend</button>
                <button onClick={goToFriendsList}>Friends List</button>
                <button onClick={admin}>Admin</button>
                <button onClick={handleLogout}>Logout</button>
            </div>
            <div className="search-container">
                <input
                    type="text"
                    placeholder="Search by email..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
                <button onClick={handleSearchSubmit}>Search</button>
            </div>
            {message && <p className="message">{message}</p>}
            {searchResult && (
                <div className="search-result">
                    <p>Username: {searchResult.username}</p>
                    <p>Email: {searchResult.email}</p>
                </div>
            )}
        </div>
    );
}

export default Home;


