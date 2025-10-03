import { Link, NavLink } from "react-router-dom";

function Header() {
  return (
    <header className="border-bottom bg-white">
      <nav className="navbar navbar-expand-sm container">
        <Link to="/" className="navbar-brand fw-bold" style={{ letterSpacing: 0.3 }}>
          Sistema OS
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#appNavbar"
          aria-controls="appNavbar"
          aria-expanded="false"
          aria-label="Alternar navegação"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div id="appNavbar" className="collapse navbar-collapse">
          <ul className="navbar-nav me-auto">
            <li className="nav-item">
              <NavLink to="/ordens" className="nav-link">
                Ordens
              </NavLink>
            </li>
          </ul>
          <ul className="navbar-nav">
            <li className="nav-item">
              <NavLink to="/login" className="nav-link">
                Login
              </NavLink>
            </li>
          </ul>
        </div>
      </nav>
    </header>
  );
}

export default Header;