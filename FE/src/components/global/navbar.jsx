import { Link } from "react-router-dom";
import { useEffect, useState } from "react";
import { logout } from "../../services/auth";
import "../../styles/App.css";

const Navbar = () => {
  const [userName, setUserName] = useState("");

  useEffect(() => {
    const token = localStorage.getItem("token");
    if (token) {
      const name = localStorage.getItem("userName");
      if (name) {
        setUserName(name);
      }
    }
  }, []);

  return (
    <div className="navbar">
      <Link to="/">Home</Link>
      {userName ? (
        <span>
          {userName} <button onClick={logout}>Logout</button>
        </span>
      ) : (
        <Link to="/login">Login</Link>
      )}
      <Link to="/map">Map</Link>
      <a
        href="https://github.com/CodeSquad-Airbnb-Team07/be-airbnb"
        target="_blank"
        rel="noopener noreferrer"
      >
        Github
      </a>
      <a
        href="https://www.figma.com/proto/T2ASU9JPHsYS0ocQjDrL1g/BE_%EC%88%99%EC%86%8C%EC%98%88%EC%95%BD%EC%84%9C%EB%B9%84%EC%8A%A4?node-id=80-358&t=x7GURSVndUvXuErC-0&scaling=contain&page-id=80%3A317"
        target="_blank"
        rel="noopener noreferrer"
      >
        Figma
      </a>
    </div>
  );
};

export default Navbar;
