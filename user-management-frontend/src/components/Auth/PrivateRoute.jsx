import { Navigate } from "react-router-dom";

export default function PrivateRoute({ children, roles }) {
  const token = localStorage.getItem("token");
  const userRoles = JSON.parse(localStorage.getItem("userRoles")) || [];

  if (!token) {
    return <Navigate to="/login" replace />;
  }

  if (roles && !roles.some(role => userRoles.includes(role))) {
    return <Navigate to="/unauthorized" replace />;
  }

  return children;
}