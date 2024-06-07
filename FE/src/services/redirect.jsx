import axios from "axios";
import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const RedirectPage = (provider) => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleOAuth = async (code) => {
    try {
      const response = await axios.get(
        `https://squadbnb.site/api/oauth/login/${provider}?code=${code}`
      );
      const data = response.data; // 응답 데이터
      console.log("로그인 성공: " + data);
      localStorage.setItem("token", data.token);
      localStorage.setItem("userId", data.userId);
      localStorage.setItem("username", data.username);

      console.log(data);
      navigate("/success");
    } catch (error) {
      navigate("/fail");
    }
  };

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const code = searchParams.get("code");
    if (code) {
      alert("CODE = " + code);
      handleOAuth(code);
    }
  }, [location]);

  return (
    <div>
      <div>Processing...</div>
    </div>
  );
};

export default RedirectPage;
