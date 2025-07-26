// src/components/UserProfile.jsx
import React, { useEffect, useState } from 'react';
import './styles.css';
import axios from 'axios';

export const UserProfile = ({ onLogout }) => {
  const [user, setUser] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchUser = async () => {
      const token = localStorage.getItem('token');
      try {
        const response = await axios.get('http://localhost:8080/api/users/me', {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setUser(response.data);
      } catch (err) {
        setError('Failed to load user profile.');
        localStorage.removeItem('token');
      }
    };
    fetchUser();
  }, []);

  if (error) return <p>{error}</p>;
  if (!user) return <p>Loading...</p>;

  return (
    <div className="profile-container">
      <button className="logout-button" onClick={onLogout}>Logout</button>
      <h2>¡Bienvenido, {user.name} {user.surname}!</h2>
      <div className="profile-info">
        <p><strong>Email:</strong> {user.email}</p>
        <p><strong>Nombre:</strong> {user.name} {user.surname}</p>
        <p><strong>Teléfono:</strong> {user.phone}</p>
      </div>
    </div>
  );
};