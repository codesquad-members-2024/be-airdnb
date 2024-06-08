import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { logout } from "../../services/auth";
import "../../styles/App.css";

const Navbar = () => {
  const [userName, setUserName] = useState("");

  const token = localStorage.getItem("token");
  useEffect(() => {
    if (token) {
      const name = localStorage.getItem("userName");
      if (name) {
        setUserName(name);
      }
    }
  }, [token]);

  return (
    <div className="navbar">
      <Link to="/">Home</Link>
      {userName ? (
        <span>
          {userName}{" "}
          <Link to="/" onClick={logout}>
            Logout
          </Link>
        </span>
      ) : (
        <Link to="/login">Login</Link>
      )}
      <Link to="/map">Map</Link>
      <Link to="https://github.com/CodeSquad-Airbnb-Team07/be-airbnb">
        Github
      </Link>
      <Link to="https://www.figma.com/proto/T2ASU9JPHsYS0ocQjDrL1g/BE_%EC%88%99%EC%86%8C%EC%98%88%EC%95%BD%EC%84%9C%EB%B9%84%EC%8A%A4?node-id=80-358&t=x7GURSVndUvXuErC-0&scaling=contain&page-id=80%3A317">
        Figma
      </Link>
      <Link to="/api/swagger-ui/index.html#/">API</Link>
    </div>
  );
};

export default Navbar;
