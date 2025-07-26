// src/components/RegisterForm.jsx
import React, { useState } from 'react';
import './styles.css';
import axios from 'axios';

export const RegisterForm = ({ onRegister }) => {
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [surname, setSurname] = useState('');
  const [phone, setPhone] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const handleRegister = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/api/auth/register', { name, surname, phone, email, password });
      const token = response.data.token;
      localStorage.setItem('token', token);
      onRegister(token);
    } catch (err) {
      setError('Registration failed.');
    }
  };

  return (
    <div className="bg-[#282c34] min-h-screen flex flex-col items-center justify-center">
      <form onSubmit={handleRegister} className="bg-gray-900 p-8 rounded-lg shadow-md w-80 space-y-4">
        <h2 className="text-white text-2xl font-semibold text-center">Register</h2>
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
          className="p-2 w-full bg-gray-800 border border-gray-600 rounded text-white placeholder-gray-400"
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="p-2 w-full bg-gray-800 border border-gray-600 rounded text-white placeholder-gray-400"
        />
        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          className="p-2 w-full bg-gray-800 border border-gray-600 rounded text-white placeholder-gray-400"
        />
        <input
          type="name"
          placeholder="name"
          value={name}
          onChange={(e) => setName(e.target.value)}
          required
          className="p-2 w-full bg-gray-800 border border-gray-600 rounded text-white placeholder-gray-400"
        />
        <input
          type="surname"
          placeholder="surname"
          value={password}
          onChange={(e) => setSurname(e.target.value)}
          required
          className="p-2 w-full bg-gray-800 border border-gray-600 rounded text-white placeholder-gray-400"
        />
        <input
          type="phone"
          placeholder="phone"
          value={phone}
          onChange={(e) => setPhone(e.target.value)}
          required
          className="p-2 w-full bg-gray-800 border border-gray-600 rounded text-white placeholder-gray-400"
        />
        <button type="submit" className="w-full px-4 py-2 bg-green-600 hover:bg-green-700 rounded text-white">
          Register
        </button>
        {error && <p className="text-red-400 text-sm text-center mt-2">{error}</p>}
      </form>
    </div>
  );
};