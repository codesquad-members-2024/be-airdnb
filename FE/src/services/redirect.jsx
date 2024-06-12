import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import { handleOAuth } from "./auth";

const RedirectPage = (provider) => {
  const location = useLocation();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const code = searchParams.get("code");
    const state = searchParams.get("state");

    if (code) {
      handleOAuth(code, state, provider);
    }
  }, [location, provider]);

  return null;
};

export default RedirectPage;
