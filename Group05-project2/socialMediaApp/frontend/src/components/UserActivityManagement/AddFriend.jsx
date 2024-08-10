import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";


const AddFriend = () => {
    const [friendEmail, setFriendEmail] = useState("");
    const [message, setMessage] = useState("");
    const navigate = useNavigate();

    const handleAddFriend = async (e) => {
        e.preventDefault();
        try {
            const userEmail = localStorage.getItem("userEmail");
            const response = await axios.post("http://localhost:8080/api/add-friend", null, {
                params: { userEmail, friendEmail }
            });
            setMessage(response.data);
            navigate("/friends");
        } catch (error) {
            console.error("Error adding friend", error);
            setMessage("Error adding friend");
        }
    };

    const goToHome = () => {
        navigate("/home");
    };

    return (
        <div className="add-friend-container">
            <form onSubmit={handleAddFriend}>
                <div className="input-group">
                    <label>Friend's Email</label>
                    <input
                        type="email"
                        placeholder="Friend's Email"
                        value={friendEmail}
                        onChange={(e) => setFriendEmail(e.target.value)}
                        required
                    />
                </div>
                <button className="home-button" type="submit">Add Friend</button>
                {message && <p className="message">{message}</p>}
            </form>
            <button className="home-button" onClick={goToHome}>Back to Home</button>
        </div>
    );
};

export default AddFriend;

