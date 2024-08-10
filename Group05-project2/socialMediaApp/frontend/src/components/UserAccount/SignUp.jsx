import React, { useState } from "react";
import '../css/LoginSignUp.css';
import user_icon from '../Assets/user.jpg';
import email_icon from '../Assets/email.jpg';
import password_icon from '../Assets/lock.png';
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios';

const SignUp = () => {
    const [username, setUsername] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [securityQuestion, setSecurityQuestion] = useState("");
    const [securityAnswer, setSecurityAnswer] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    const validateEmail = (email) => {
        return email.endsWith("@dal.ca");
    }

    const validatePassword = (password) => {
        const regex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        return regex.test(password);
    }

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validateEmail(email)) {
            setError("Email must include '@dal.ca'");
            return;
        }
        if (!validatePassword(password)) {
            setError("Password must be at least 8 characters long and include at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character.");
            return;
        }
        try{
        const response = await axios.post("http://localhost:8080/api/signup", {
            username,
            email,
            password,
            securityQuestion,
            securityAnswer
        });console.log(response.data);

        if(response.data == "Email is already registered"){
             alert('Email is already registered');
        } else{
        alert('Sign up successfully');
        navigate("/login");
        }
      } catch (error) {
        console.error(error);
        alert('An error occurred. Please try again later');
      }
    }

    return (
        <div className="signup-container">
            <form onSubmit={handleSubmit}>
                <h2>Sign Up</h2>
                <div className="authentication-input-group">
                    <img src={user_icon} alt="user icon" />
                    <input type="text" placeholder="Username" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </div>
                <div className="authentication-input-group">
                    <img src={email_icon} alt="email icon" />
                    <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div className="authentication-input-group">
                    <img src={password_icon} alt="password icon" />
                    <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <div className="security-input">
                    <input type="text" placeholder="Security Question" value={securityQuestion} onChange={(e) => setSecurityQuestion(e.target.value)} required />
                </div>
                <div className="security-input">
                    <input type="text" placeholder="Security Answer" value={securityAnswer} onChange={(e) => setSecurityAnswer(e.target.value)} required />
                </div>
                {error && <p className="error">{error}</p>}
                <button className="button" type="submit">Sign Up</button>
                <Link to="/login" style={{ color: 'black', marginLeft: '10px' }}>Already have an Account? Go to login</Link> 
            </form>
        </div>
    );
}

export default SignUp;
