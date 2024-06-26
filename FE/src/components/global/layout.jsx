import { Outlet } from "react-router-dom";
import Nav from "./nav";

const Layout = () => {
  return (
    <div>
      <Nav />
      <Outlet />
    </div>
  );
};

export default Layout;
