import React, { useState, useEffect } from 'react'; 
import logo from './logo_p.svg';
import './App.css';
import { LoginForm } from './components/LoginForm';
import { RegisterForm } from './components/RegisterForm';
import { UserProfile } from './components/UserProfile';  

const App = () => {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [view, setView] = useState('login');

  const handleLogin = (newToken) => {
    setToken(newToken);
    setView('profile');
  };

  const handleRegister = (newToken) => {
    setToken(newToken);
    setView('profile');
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setView('login');
  };

  useEffect(() => {
    if (token) {
      setView('profile');
    }
  }, [token]);

  if (!token) {
    return (
      <div className="App">
        
        <div className="App-background">
          <h3>App User Management</h3>
          <img src={logo} className="App-logo" alt="logo" />
          {view === 'login' ? (
            <>
              <LoginForm onLogin={handleLogin} />
              <p>Don't have an account? <button 
              className="mt-2 px-4 py-2 bg-green-600 hover:bg-green-700 rounded text-white" onClick={() => setView('register')}>Register</button></p>
            </>
          ) : (
            <>
              <RegisterForm onRegister={handleRegister} />
              <p>Already have an account? <button 
              className="mt-2 px-4 py-2 bg-green-600 hover:bg-green-700 rounded text-white" onClick={() => setView('login')}>Login</button></p>
            </>
          )}
        </div>
      </div>
    );
  }

  return (
    <div className="p-4">
      <UserProfile onLogout={handleLogout} />
    </div>
  );
};

export default App;