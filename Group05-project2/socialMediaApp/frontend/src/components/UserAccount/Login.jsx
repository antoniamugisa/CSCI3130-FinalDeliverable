import React, { useState } from "react";
import '../css/LoginSignUp.css';
import email_icon from '../Assets/email.jpg';
import password_icon from '../Assets/lock.png';
import axios from 'axios';
import { Link, useNavigate } from "react-router-dom";

const Login = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const [error, setError] = useState("");



    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post("http://localhost:8080/api/login", {
                email,
                password
            });
            console.log(response.data);
            if (response.data === "Your account is pending approval") {
                alert('Your account is pending approval');
            }
            else if (response.data === "Your account has been rejected") {
                alert('Your account has been rejected to join');
            }  
            else if (response.data === "Login successful") {
                alert('Login successful');
                localStorage.setItem("isLoggedIn", true);
                localStorage.setItem("userEmail", email);
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
        <div className="login-container">
            <form onSubmit={handleSubmit}>
                <h2 className="loginheading">Login</h2>
                <div className="authentication-input-group">
                    <img src={email_icon} alt="email icon" />
                    <input type="email" placeholder="Email" value={email} onChange={(e) => setEmail(e.target.value)} required />
                </div>
                <div className="authentication-input-group">
                    <img src={password_icon} alt="password icon" />
                    <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                {error && <p className="error">{error}</p>}
                <button className="button" type="submit">Login</button>
                </form>
                <Link to="/forgot-password" style={{ color: 'black', marginLeft: '10px' }}>Forgot Password?</Link> 
                <Link to="/signup" style={{ color: 'black', marginLeft: '10px' }}>No Account? Go to Sign up</Link> 
        </div>
    );
}

export default Login;
