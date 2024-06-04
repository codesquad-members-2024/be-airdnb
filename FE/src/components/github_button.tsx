import { useEffect } from 'react';

const GithubButton = () => {
  const handleLogin = () => {
    window.location.href = 'https://squadbnb.site/api/oauth2/authorization/github';
  };


  return (
    <button
      onClick={handleLogin}
      style={{
        backgroundColor: '#333',
        color: '#fff',
        padding: '10px 20px',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        display: 'flex',
        alignItems: 'center',
        margin: '10px auto' 
      }}
    >
      <img
        src="/images/github-logo.svg"
        alt="GitHub 로고"
        style={{ marginRight: '10px', width: '24px', height: '24px' }}
      />
      <span>Login with GitHub</span>
    </button>
  );
};

export default GithubButton;