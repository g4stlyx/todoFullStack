import "./styles/App.css";
import "./styles/todoApp.css";
import Login from "./components/Login";
import Welcome from "./components/Welcome";
import Error from "./components/Error";
import TodoList from "./components/TodoList";
import Footer from "./components/Footer";
import Header from "./components/Header";
import Logout from "./components/Logout";
import Todo from "./components/Todo";
import { Routes, Route, useNavigate } from "react-router-dom";
import { useAuth } from "./components/security/AuthContext";
import { jwtDecode } from "jwt-decode";
import { useEffect, useState } from "react";
import { Spinner } from 'react-bootstrap';

function AuthenticatedRoute({ children }) {
  const authContext = useAuth();
  const storedToken = authContext.storedToken;
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    if (storedToken) {
      const decodedToken = jwtDecode(storedToken.replace("Bearer ", ""));
      const currentTime = Date.now() / 1000;

      if (decodedToken.exp > currentTime) {
        authContext.setToken(storedToken);
        authContext.setIsAuthenticated(true);
        authContext.setUsername(decodedToken.sub);
        setIsLoading(false);
      } else {
        sessionStorage.removeItem("token");
        navigate("/login");
      }
    } else {
      navigate("/login");
    }
  }, [storedToken, authContext, navigate]);

  if (isLoading) {
    return (
      <div className="d-flex justify-content-center align-items-center" style={{ height: "100vh" }}>
      <Spinner animation="border" role="status">
        <span className="visually-hidden">Loading...</span>
      </Spinner>
    </div>
    )
  }

  return children;
}

function App() {
  return (
    <div className="App TodoApp">
      <Header />
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/login" element={<Login />} />
        <Route
          path="/welcome/:username"
          element={
            <AuthenticatedRoute>
              <Welcome />
            </AuthenticatedRoute>
          }
        />
        <Route
          path="/todos"
          element={
            <AuthenticatedRoute>
              <TodoList />
            </AuthenticatedRoute>
          }
        />
        <Route
          path="/todos/:id"
          element={
            <AuthenticatedRoute>
              <Todo />
            </AuthenticatedRoute>
          }
        />
        <Route
          path="/logout"
          element={
            <AuthenticatedRoute>
              <Logout />
            </AuthenticatedRoute>
          }
        />
        <Route path="*" element={<Error />} />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
