const GithubButton = () => {
  const handleLogin = async () => {
    try {
      const response = await fetch(
        new Request("https://squadbnb.site/api/oauth2/authorization/github"),
        {
          method: "GET",
        }
      );

      const authorizationUrl = response.headers.get("Location");
      console.log(authorizationUrl);
      if (authorizationUrl) {
        // 두 번째 fetch 요청
        const authResponse = await fetch(authorizationUrl, {
          method: "GET",
        });

        const codeUrl = authResponse.headers.get("Location");
        console.log(codeUrl);
        if (codeUrl) {
          // 응답 JSON에서 token, userId, userName 추출
          const data = await authResponse.json();
          console.log(data);

          // 로컬 스토리지에 저장
          localStorage.setItem("token", data.token);
          localStorage.setItem("userId", data.userId.toString());
          localStorage.setItem("userName", data.userName);
        }

        // 홈 페이지로 리다이렉트
        window.location.href = "/";
      }
    } catch (error) {
      console.error("Error during GitHub OAuth2 authorization", error);
    }
  };

  return (
    <button
      onClick={handleLogin}
      style={{
        backgroundColor: "#333",
        color: "#fff",
        padding: "10px 20px",
        border: "none",
        borderRadius: "5px",
        cursor: "pointer",
        display: "flex",
        alignItems: "center",
        margin: "10px auto",
      }}
    >
      <img
        src="/images/github-logo.svg"
        alt="GitHub 로고"
        style={{ marginRight: "10px", width: "24px", height: "24px" }}
      />
      <span>Login with GitHub</span>
    </button>
  );
};

export default GithubButton;
