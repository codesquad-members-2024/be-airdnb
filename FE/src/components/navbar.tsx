import Link from "next/link";

export default function Navbar() {
  return (
    <div className="navbar">
      <Link href="/">Home</Link>
      <Link href="/login">Login</Link>
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
