import React, { useState } from 'react';
import { useNavigate, Link } from 'react-router-dom';
import api from './api';
import './reset.css';
import './Login.css'; // Import the CSS file

function Login() {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handleUsernameChange = (e) => {
        setUsername(e.target.value);
    };

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await api.post('/login', { username, password });
            localStorage.setItem('token', response.data.token); // assuming the token is in response.data.token
            navigate('/mypage');
        } catch (error) {
            console.error('Login failed', error);
            // Handle login error (e.g., show an error message to the user)
        }
        setUsername('');
        setPassword('');
    };

    return (
        <div className="container">
            <div className="form">
                <h1>K-trip</h1>
                <form onSubmit={handleSubmit}>
                    <div>
                        <label>Username:</label>
                        <input
                            type="text"
                            value={username}
                            onChange={handleUsernameChange}
                            required
                        />
                    </div>
                    <div>
                        <label>Password:</label>
                        <input
                            type="password"
                            value={password}
                            onChange={handlePasswordChange}
                            required
                        />
                    </div>
                    <button type="submit">Login</button>
                    <div className="extra-links">
                        <Link to="/forgot-password">Forgot ID or Password?</Link>
                        <span> | </span>
                        <Link to="/signup">Sign Up</Link>
                    </div>
                </form>
            </div>
        </div>
    );
}

export default Login;