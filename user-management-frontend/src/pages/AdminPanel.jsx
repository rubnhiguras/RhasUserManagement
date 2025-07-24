import { useState, useEffect } from 'react';
import api from '../services/api';
import UserTable from '../components/User/UserTable';
import RoleEditor from '../components/User/RoleEditor';

export default function AdminPanel() {
  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUsers = async () => {
      try {
        const response = await api.get('/admin/users');
        setUsers(response.data);
      } catch (error) {
        console.error('Error fetching users:', error);
      } finally {
        setLoading(false);
      }
    };
    fetchUsers();
  }, []);

  if (loading) return <div>Cargando usuarios...</div>;

  return (
    <div className="admin-panel">
      <h2>Administración de Usuarios</h2>
      <UserTable users={users} />
      <RoleEditor users={users} onUpdate={setUsers} />
    </div>
  );
}