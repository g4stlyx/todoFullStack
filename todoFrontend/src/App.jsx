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
import { Routes, Route } from "react-router-dom";
import Profile from "./components/Profile";
import UserList from "./components/UserList";
import User from "./components/User";
import AdminRoute from "./components/security/AdminRoute";
import AuthenticatedRoute from "./components/security/AuthenticatedRoute";

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
        <Route
          path="/profile"
          element={
            <AuthenticatedRoute>
              <Profile />
            </AuthenticatedRoute>
          }
        />
        <Route
          path="/administrator/users/:username"
          element={
            <AdminRoute>
              <User />
            </AdminRoute>
          }
        />
        <Route
          path="/administrator/users"
          element={
            <AdminRoute>
              <UserList />
            </AdminRoute>
          }
        />
        <Route path="*" element={<Error />} />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
