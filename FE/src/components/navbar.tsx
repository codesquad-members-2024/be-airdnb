"use client";
import Link from "next/link";
import { useEffect, useState } from "react";
import { logout } from "./login/auth";

export default function Navbar() {
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
      <Link href="/">Home</Link>
      {userName ? (
        <span>
          {userName} <button onClick={logout}>Logout</button>
        </span>
      ) : (
        <Link href="/login">Login</Link>
      )}
      <Link href="/map">Map</Link>
      <Link href="https://github.com/CodeSquad-Airbnb-Team07/be-airbnb">
        Github
      </Link>
      <Link href="https://www.figma.com/proto/T2ASU9JPHsYS0ocQjDrL1g/BE_%EC%88%99%EC%86%8C%EC%98%88%EC%95%BD%EC%84%9C%EB%B9%84%EC%8A%A4?node-id=80-358&t=x7GURSVndUvXuErC-0&scaling=contain&page-id=80%3A317">
        Figma
      </Link>
    </div>
  );
}
