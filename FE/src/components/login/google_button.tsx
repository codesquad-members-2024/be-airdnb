const GoogleButton = () => {
  const handleLogin = () => {
    fetch("https://squadbnb.site/api/oauth2/authorization/google", {
      method: "GET",
    })
      .then((response) => response.json())
      .then((data: LoginResponse) => {
        localStorage.setItem("token", data.token);
        localStorage.setItem("userId", data.userId.toString());
        localStorage.setItem("userName", data.userName);
        window.location.href = "/"; // Redirect to the home page
      });
  };

  return (
    <button
      onClick={handleLogin}
      style={{
        backgroundColor: "#fff",
        color: "#000",
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
        src="/images/google-logo.svg"
        alt="Google 로고"
        style={{ marginRight: "10px", width: "24px", height: "24px" }}
      />
      <span>Login with Google</span>
    </button>
  );
};

export default GoogleButton;
