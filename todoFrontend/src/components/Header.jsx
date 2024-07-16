import { Link } from "react-router-dom";
import { useAuth } from "./security/AuthContext";

export default function Header() {
  const authContext = useAuth();
  const isAuthenticated = authContext.isAuthenticated;

  return (
    <div className="Header border-bottom border-light border-5 mb-5 p-2">
      <div className="container">
        <div className="row">
          <nav className="navbar navbar-expand-lg">
            <a
              className="navbar-brand ms-2 fs-2 fw-bold text-black"
              href="https://g4stlyx.github.io"
            >
              g4stly
            </a>
            <div className="collapse navbar-collapse">
              <ul className="navbar-nav">
                <li className="nav-item fs-5">
                  {isAuthenticated && 
                  <Link className="nav-link" to="/welcome/g4stly">
                    Home
                  </Link>}
                </li>
                <li className="nav-item fs-5">
                  {isAuthenticated && 
                  <Link className="nav-link" to="/todos">
                    Todos
                  </Link>}
                </li>
              </ul>
            </div>
            <ul className="navbar-nav">
              <li className="nav-item fs-5">
                {!isAuthenticated && <Link className="nav-link" to="/login">
                  Login
                </Link>}
              </li>
              <li className="nav-item fs-5">
                {isAuthenticated && <Link className="nav-link" to="/logout" onClick={authContext.logout}>
                  Logout
                </Link>}
              </li>
            </ul>
          </nav>
        </div>
      </div>
    </div>
  );
}
