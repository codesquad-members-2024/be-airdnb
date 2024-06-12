import styles from "/src/styles/nav.module.css";
import GithubButton from "./github_button.jsx";
import GoogleButton from "./google_button.jsx";

const LoginModal = ({ show, onClose }) => {
  if (!show) {
    return null;
  }

  const handleLogin = (provider) => {
    window.location.href = `https://squadbnb.site/api/oauth2/authorization/${provider}`;
  };

  return (
    <div className={styles.modal} onClick={onClose}>
      <div className={styles.modalContent} onClick={(e) => e.stopPropagation()}>
        <h2>Login</h2>
        <GithubButton onClick={() => handleLogin("github")} />
        <GoogleButton onClick={() => handleLogin("google")} />
      </div>
    </div>
  );
};

export default LoginModal;
