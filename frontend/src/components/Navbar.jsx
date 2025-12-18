import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/useAuth";

export default function Navbar() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  const doLogout = () => {
    logout();
    navigate("/login");
  };

  return (
    <nav style={{ marginBottom: "20px" }}>
      <Link to="/">Home</Link> |{" "}
      <Link to="/article/new">New Article</Link> |{" "}
      <Link to="/account">My Account</Link> |{" "}
      <span>Welcome {user?.name}</span> |{" "}
      <button onClick={doLogout}>Logout</button>
    </nav>
  );
}
