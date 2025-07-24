export default function UserTable({ users }) {
  return (
    <table className="user-table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Nombre</th>
          <th>Email</th>
          <th>Roles</th>
        </tr>
      </thead>
      <tbody>
        {users.map(user => (
          <tr key={user.id}>
            <td>{user.id}</td>
            <td>{user.name} {user.surname}</td>
            <td>{user.email}</td>
            <td>{user.roles?.join(', ') || 'Usuario'}</td>
          </tr>
        ))}
      </tbody>
    </table>
  );
}s