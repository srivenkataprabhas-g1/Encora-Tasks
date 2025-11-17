import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import LoginForm from './LoginForm.jsx';
import RegisterForm from './RegisterForm.jsx';
import './index.css';
import App from './App.jsx';

// Login page
function Login() {
  return (
    <div className="login-container">
      <h1>Login</h1>
      <LoginForm />
      <a href="/register">New User?Register</a>
    </div>
  );
}

// Register page
function Register() {
  return (
    <div className="register-container">
      <h1>Register</h1>
      <RegisterForm />
    </div>
  );
}

// Optional Home page (redirects to login)
function Home() {
  return <Navigate to="/login" />;
}

// Render the app
ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/app" element={<App />} />
      </Routes>
    </BrowserRouter>
  </React.StrictMode>
);