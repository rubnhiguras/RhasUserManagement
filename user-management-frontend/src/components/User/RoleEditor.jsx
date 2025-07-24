import { useState } from 'react';
import api from '../../services/api';

export default function RoleEditor({ users, onUpdate }) {
  const [selectedUser, setSelectedUser] = useState(null);
  const [availableRoles, setAvailableRoles] = useState(['ADMIN', 'EDITOR', 'VIEWER']);

  const handleRoleUpdate = async (userId, newRoles) => {
    try {
      await api.put(`/admin/users/${userId}/roles`, { roles: newRoles });
      const updatedUsers = users.map(user => 
        user.id === userId ? { ...user, roles: newRoles } : user
      );
      onUpdate(updatedUsers);
    } catch (error) {
      console.error('Error updating roles:', error);
    }
  };

  return (
    <div className="role-editor">
      <h3>Editor de Roles</h3>
      <select onChange={(e) => setSelectedUser(users.find(u => u.id == e.target.value))}>
        <option value="">Seleccionar usuario</option>
        {users.map(user => (
          <option key={user.id} value={user.id}>
            {user.name} ({user.email})
          </option>
        ))}
      </select>

      {selectedUser && (
        <div className="role-selection">
          {availableRoles.map(role => (
            <label key={role}>
              <input
                type="checkbox"
                checked={selectedUser.roles?.includes(role)}
                onChange={(e) => {
                  const newRoles = e.target.checked
                    ? [...(selectedUser.roles || []), role]
                    : selectedUser.roles?.filter(r => r !== role);
                  handleRoleUpdate(selectedUser.id, newRoles);
                }}
              />
              {role}
            </label>
          ))}
        </div>
      )}
    </div>
  );
}