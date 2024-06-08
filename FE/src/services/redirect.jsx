import axios from "axios";
import { useEffect } from "react";
import { useLocation, useNavigate } from "react-router-dom";

const RedirectPage = (provider) => {
  const location = useLocation();
  const navigate = useNavigate();

  const handleOAuth = async (code, state) => {
    try {
      const response = await axios.get(
        `https://squadbnb.site/api/login/oauth2/code/${String(
          provider.provider
        )}?code=${code}&state=${state}`
      );
      const data = response.data; // 응답 데이터
      console.log("로그인 성공: " + data);
      localStorage.setItem("token", data.token);
      localStorage.setItem("userId", data.userId);
      localStorage.setItem("userName", data.userName);

      alert("로그인 성공: " + data.userName + "님 환영합니다!");
    } catch (error) {
      alert("로그인 실패: " + error.response.data.message);
    }
    navigate("/");
  };

  useEffect(() => {
    const searchParams = new URLSearchParams(location.search);
    const code = searchParams.get("code");
    const state = searchParams.get("state");

    if (code) {
      handleOAuth(code, state);
    }
  }, [location]);

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
      }}
    >
      <div
        style={{
          textAlign: "center",
          fontSize: "2vw",
        }}
      >
        Processing...
      </div>
    </div>
  );
};

export default RedirectPage;
