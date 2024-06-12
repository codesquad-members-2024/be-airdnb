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
        boxShadow: "0px 1px 2px rgba(0, 0, 0, 0.2)",
        justifyContent: "center",
        width: "100%" /* 버튼 너비 조정 */,
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
