import { useEffect } from 'react';

const KakaoButton = () => {
  const handleLogin = () => {
    window.location.href = 'https://squadbnb.site/oauth2/authorization';
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
        backgroundColor: '#ff0',
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
        src="/images/kakao-logo.svg"
        alt="Kakao 로고"
        style={{ marginRight: '10px', width: '24px', height: '24px' }}
      />
      <span>Login with Kakao</span>
      
    </button>
  );
};

export default KakaoButton;