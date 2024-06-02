import Link from "next/link";

export default function Navbar() {
  return (
      <div className="navbar">
          <Link href="/">home</Link>
          <Link href="/login">login</Link>
      </div>
  )
} 