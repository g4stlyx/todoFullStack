import { executeJwtAuthenticationService } from "../api/AuthenticationApiService";
import { apiClient } from "../api/ApiClient";
import { jwtDecode } from "jwt-decode";
const { createContext, useState, useContext, useEffect } = require("react");

const AuthContext = createContext();
export const useAuth = () => useContext(AuthContext);

export default function AuthProvider({ children }) {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [username, setUsername] = useState(null);
  const [token, setToken] = useState(null);
  const [isAdmin, setIsAdmin] = useState(false)

  const storedToken = sessionStorage.getItem("token");

  useEffect(() => {
    if (storedToken) {
      const decodedToken = jwtDecode(storedToken.replace('Bearer ', ''));
      const currentTime = Date.now() / 1000;

      if (decodedToken.exp > currentTime) {
        setToken(storedToken);
        setIsAuthenticated(true);
        setUsername(decodedToken.sub);
        setIsAdmin(decodedToken.isAdmin);

        apiClient.interceptors.request.use((config) => {
          config.headers.Authorization = storedToken;
          return config;
        });
      } else {
        sessionStorage.removeItem("token");
      }
    }
  }, [storedToken]);

  async function login(username, password) {
    try {
      const response = await executeJwtAuthenticationService(
        username,
        password
      );

      if (response.status === 200) {
        const jwtToken = "Bearer " + response.data.token;
        setIsAuthenticated(true);
        setUsername(username);
        setToken(jwtToken);
        setIsAdmin(response.data.token.isAdmin);

        sessionStorage.setItem("token", jwtToken);

        // all of the urls are intercepted and request a auth token
        apiClient.interceptors.request.use((config) => {
          config.headers.Authorization = jwtToken;
          return config;
        });

        return true;
      } else {
        logout();
        return false;
      }
    } catch (err) {
      logout();
      return false;
    }
  }

  function logout() {
    setIsAuthenticated(false);
    setToken(null);
    setUsername(null);
    sessionStorage.removeItem("token");
  }

  const valueToBeShared = {
    isAuthenticated,
    setIsAuthenticated,
    login,
    logout,
    username,
    setUsername,
    token,
    setToken,
    storedToken,
    isAdmin,
    setIsAdmin  
  };

  return (
    <AuthContext.Provider value={valueToBeShared}>
      {children}
    </AuthContext.Provider>
  );
}
