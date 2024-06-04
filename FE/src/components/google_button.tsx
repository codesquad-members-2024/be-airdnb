import { useEffect } from 'react';

const GoogleButton = () => {
  const handleLogin = () => {
    window.location.href = 'https://github.com/login/oauth/authorize?client_id=YOUR_CLIENT_ID&redirect_uri=YOUR_REDIRECT_URI&scope=user';
  };

  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get('code');
    if (code) {
      fetch('/api/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ code }),
      })
        .then((res) => res.json())
        .then((data) => {
          localStorage.setItem('token', data.token);
        })
        .catch((error) => {
          console.error('Error:', error);
        });
    }
  }, []);

  return (
    <button
      onClick={handleLogin}
      style={{
        backgroundColor: '#fff',
        color: '#000',
        padding: '10px 20px',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        display: 'flex',
        alignItems: 'center',
        margin: '10px auto' 
      }}>
      <img
        src="/images/google-logo.svg"
        alt="Google 로고"
        style={{ marginRight: '10px', width: '24px', height: '24px' }}
      />
      <span>Login with Google</span>
      
    </button>
  );
};

export default GoogleButton;