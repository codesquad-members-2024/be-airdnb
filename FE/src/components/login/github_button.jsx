const GithubButton = ({ onClick }) => {
  return (
    <button
      onClick={onClick}
      style={{
        backgroundColor: "#1B1F23",

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
      <span style={{ color: "#fff" }}>Login with GitHub</span>
    </button>
  );
};

export default GithubButton;
