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
