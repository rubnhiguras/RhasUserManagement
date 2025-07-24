import { BrowserRouter, Routes, Route } from "react-router-dom";
import PrivateRoute from "./components/Auth/PrivateRoute";
import Login from "./components/Auth/Login";
import Dashboard from "./pages/Dashboard";
import AdminPanel from "./pages/AdminPanel";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/dashboard" element={
          <PrivateRoute>
            <Dashboard />
          </PrivateRoute>
        }/>
        <Route path="/admin" element={
          <PrivateRoute roles={["ADMIN"]}>
            <AdminPanel />
          </PrivateRoute>
        }/>
      </Routes>
    </BrowserRouter>
  );
}