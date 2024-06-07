import GithubButton from "../components/login/github_button";
import GoogleButton from "../components/login/google_button";
import "../styles/App.css";

const LoginPage = () => {
  const handleLogin = (provider) => {
    window.location.href = `https://squadbnb.site/api/oauth2/authorization/${provider}`;
  };

  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        height: "100vh",
      }}
    >
      <div>
        <GithubButton onClick={() => handleLogin("github")} />
        <GoogleButton onClick={() => handleLogin("google")} />
      </div>
    </div>
  );
};

export default LoginPage;
