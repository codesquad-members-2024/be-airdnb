import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { handleOAuth } from "./auth";

const RedirectPage = (provider) => {
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const code = searchParams.get("code");
    const state = searchParams.get("state");

    if (code) {
      handleOAuth(code, state, provider, navigate);
    }
  }, [location, navigate, provider]);

  return (
    <div className="flex items-center justify-center h-screen">
      <div className="text-lg font-semibold">Processing</div>
    </div>
  );
};

export default RedirectPage;
