import React, { useState } from "react";
import '../css/LoginSignUp.css';
import axios from 'axios';
import { Link, useNavigate } from "react-router-dom";

const ForgotPassword = () => {
    const [email, setEmail] = useState("");
    const [securityAnswer, setSecurityAnswer] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [error, setError] = useState("");
    const [success, setSuccess] = useState("");
    const navigate = useNavigate();

    const validatePassword = (password) => {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        return regex.test(password);
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validatePassword(newPassword)) {
            setError("Password must be at least 8 characters long and include at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.");
            return;
        }
        try {
            const response = await axios.post("http://localhost:8080/api/forgot-password", {
                email,
                securityAnswer,
                password : newPassword
            });
            console.log(response.data);
            if (response.data === "Password reset successful") {
                alert('Password reset successful');
                navigate("/home");
            } else {
                setError("Wrong email or password");
            }
        } catch (error) {
            console.error(error);
            alert('An error occurred. Please try again later');
        }
    }

    return (
        <div className="forgot-password-container">
            <form onSubmit={handleSubmit}>
                <h2>Forgot Password</h2>
                <div className="input-group">
                    <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div className="input-group">
                    <input type="text" placeholder="Security Answer" value={securityAnswer} onChange={(e) => setSecurityAnswer(e.target.value)} required />
                </div>
                <div className="input-group">
                    <input type="password" placeholder="New Password" value={newPassword} onChange={(e) => setNewPassword(e.target.value)} required />
                </div>
                {error && <p className="error">{error}</p>}
                {success && <p className="success">{success}</p>}
                <button className="button" type="submit">Reset Password</button>
                
                <Link className="linktag" to="/login" style={{ color: 'black', marginLeft: '10px' }}>Back to login</Link> 
                
            </form>
        </div>
    );
}

export default ForgotPassword;
