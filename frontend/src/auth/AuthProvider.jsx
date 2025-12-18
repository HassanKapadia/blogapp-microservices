import { useEffect, useState } from "react";
import { AuthContext } from "./AuthContext";
import { parseJwt } from "./jwtUtils";
import api from "../api/apiClient";

export default function AuthProvider({ children }) {
  const [token, setToken] = useState(() => localStorage.getItem("token"));
  const [user, setUser] = useState(null);

  const login = (jwt) => {
    localStorage.setItem("token", jwt);
    setToken(jwt);
  };

  const logout = () => {
    localStorage.removeItem("token");
    setToken(null);
    setUser(null);
  };

  /* eslint-disable react-hooks/set-state-in-effect */
  useEffect(() => {
    if (!token) {
      setUser(null);
      return;
    }

    const parsed = parseJwt(token);
    if (!parsed?.userId) {
      localStorage.removeItem("token");
      setToken(null);
      setUser(null);
      return;
    }

    let cancelled = false;

    api.get(`/api/users/${parsed.userId}`)
      .then(res => {
        if (!cancelled) setUser(res.data);
      })
      .catch(() => {
        localStorage.removeItem("token");
        setToken(null);
        setUser(null);
      });

    return () => {
      cancelled = true;
    };
  }, [token]);

  return (
    <AuthContext.Provider value={{ token, user, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}
