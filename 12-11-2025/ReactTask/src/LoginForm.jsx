import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function LoginForm() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const validateForm = () => {
    const newErrors = {};
    if (!email.trim()) newErrors.email = 'Email is required';
    else if (!/\S+@\S+\.\S+/.test(email)) newErrors.email = 'Email format is invalid';

    if (!password) newErrors.password = 'Password is required';
    else if (password.length < 6) newErrors.password = 'Password must be at least 6 characters';

    return newErrors;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const validationErrors = validateForm();
    if (Object.keys(validationErrors).length > 0) {
      setErrors(validationErrors);
    } else {
      // Save login info to sessionStorage
      sessionStorage.setItem('email', email);
      sessionStorage.setItem('password', password);
      sessionStorage.setItem('isLoggedIn', 'true');
      sessionStorage.setItem('loginTime', new Date().toISOString());

      navigate('/app'); // Navigate to App component
    }
  };

  return (
    <div className="login-container">
      <h1>Login</h1>
      <form onSubmit={handleSubmit}>
        <label>Email:
          <input type="text" value={email} onChange={e => setEmail(e.target.value)} />
        </label>
        {errors.email && <p className="error">{errors.email}</p>}

        <label>Password:
          <input type="password" value={password} onChange={e => setPassword(e.target.value)} />
        </label>
        {errors.password && <p className="error">{errors.password}</p>}

        <button type="submit">Login</button>
      </form>
    </div>
  );
}

export default LoginForm;