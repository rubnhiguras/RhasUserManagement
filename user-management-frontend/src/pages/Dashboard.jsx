import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { getCurrentUser } from '../services/auth';

export default function Dashboard() {
  const navigate = useNavigate();
  const user = getCurrentUser();

  useEffect(() => {
    if (!user) navigate('/login');
  }, [user, navigate]);

  return (
    <div className="dashboard">
      <h1>Bienvenido, {user?.name}!</h1>
      <div className="user-info">
        <img src={user?.avatarUrl || '/default-avatar.png'} alt="Avatar" />
        <p>Email: {user?.email}</p>
        <p>Teléfono: {user?.phone}</p>
      </div>
    </div>
  );
}