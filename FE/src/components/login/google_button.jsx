const GoogleButton = ({ onClick }) => {
  return (
    <button
      onClick={onClick}
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
        boxShadow: "0px 1px 2px rgba(0, 0, 0, 0.2)",
        justifyContent: "center",
        width: "100%" /* 버튼 너비 조정 */,
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
