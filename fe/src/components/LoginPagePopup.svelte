<script>
  import { createEventDispatcher, onMount } from 'svelte';
  export let show = false;
  export let onClose;

  let email = '';
  let password = '';
  let name = '';
  let isLoginMode = true;
  const dispatch = createEventDispatcher();

  let API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
  if (API_BASE_URL.endsWith('/')) {
    API_BASE_URL = API_BASE_URL.slice(0, -1);
  }

  const handleLoginOrSignup = async () => {
    const endpoint = isLoginMode ? `${API_BASE_URL}/login` : `${API_BASE_URL}/register`;

    const body = JSON.stringify({
      email,
      password,
      ...(isLoginMode ? {} : { name })
    });

    try {
      const response = await fetch(`${endpoint}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body
      });

      if (response.ok) {
        const data = await response.json();
        const accessToken = data.accessToken;
        const refreshToken = data.refreshToken;
        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken); 

        const token = localStorage.getItem('accessToken');

        try {
          const base64Url = token.split('.')[1];
          const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
          const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
            return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
          }).join(''));

          const decodedToken = JSON.parse(jsonPayload);

          const user = {
            userEmail: decodedToken.memberId,
            username: decodedToken.memberName,
            profileImage: decodedToken.memberProfile
          };

          alert(isLoginMode ? "로그인 되었습니다." : "회원가입 되었습니다.");
          dispatch('login', user);
          handleClose();
        } catch (error) {
          console.error('Error decoding token:', error);
          alert('토큰 디코딩 중 오류가 발생했습니다.');
        }
      } else {
        const errorData = await response.json();
        alert(errorData.errorMessage);
      }
    } catch (error) {
      console.error('Error:', error);
      alert('서버가 응답하지 않습니다. 잠시 후, 다시 시도해주세요');
    } 
  };

  const handleSocialLogin = (provider) => {
    const endpoint = `${API_BASE_URL}/oauth2/authorization/${provider === '깃허브' ? 'github' : 'kakao'}`;
    window.location.href = endpoint;
  };

  const toggleMode = () => {
    isLoginMode = !isLoginMode;
  };

  const handleClose = () => {
    email = '';
    password = '';
    name = '';
    onClose();
  };

  // GitHub 인증 후 리디렉션된 URL에서 쿼리 파라미터 읽어오기
  onMount(() => {
    const query = new URLSearchParams(window.location.search);
    const accessToken = query.get('accessToken');
    const refreshToken = query.get('refreshToken');

    if (accessToken && refreshToken) {
      localStorage.setItem('accessToken', accessToken);
      localStorage.setItem('refreshToken', refreshToken);

      const base64Url = accessToken.split('.')[1];
      const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
      const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
      }).join(''));

      const decodedToken = JSON.parse(jsonPayload);

      const user = {
        userEmail: decodedToken.memberId,
        username: decodedToken.memberName,
        profileImage: decodedToken.memberProfile
      };

      dispatch('login', user);
      handleClose();

      window.location.href = '/';
    }
  });
</script>

{#if show}
  <div class="fixed inset-0 bg-gray-500 bg-opacity-75 flex items-center justify-center z-50 text-gray-800">
    <div class="bg-white p-8 rounded-lg shadow-lg w-full max-w-md relative">
      <button class="absolute top-2 right-2 text-gray-600 hover:text-gray-800" on:click={handleClose}>
        <svg class="h-6 w-6" fill="none" stroke="currentColor" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
        </svg>
      </button>
      <h2 class="text-2xl font-bold mb-6 text-gray-800 text-center">{isLoginMode ? '로그인' : '회원가입'}</h2>
      
      <div class="mb-4">
        <label for="email" class="block text-gray-700">이메일</label>
        <input id="email" type="email" bind:value={email} class="w-full p-2 border border-gray-300 rounded mt-1 text-gray-900" placeholder="이메일을 입력하세요" />
      </div>

      {#if !isLoginMode}
        <div class="mb-6">
          <label for="name" class="block text-gray-700">이름</label>
          <input id="name" type="text" bind:value={name} class="w-full p-2 border border-gray-300 rounded mt-1 text-gray-900" placeholder="이름을 입력하세요" />
        </div>
      {/if}
  
      <div class="mb-4">
        <label for="password" class="block text-gray-700">비밀번호</label>
        <input id="password" type="password" bind:value={password} class="w-full p-2 border border-gray-300 rounded mt-1 text-gray-900" placeholder="비밀번호를 입력하세요" />
      </div>
  
      <button on:click={handleLoginOrSignup} class="w-full bg-blue-500 text-white py-2 rounded hover:bg-blue-600 transition">{isLoginMode ? '로그인' : '회원가입'}</button>
      
      <div class="mt-4 text-center text-gray-600">{isLoginMode ? '아직 회원이 아니신가요?' : '이미 회원이신가요?'} <button on:click={toggleMode} class="text-blue-500 hover:underline">{isLoginMode ? '회원가입' : '로그인'}</button></div>
  
      <div class="mt-6 text-center text-gray-600">또는</div>
      
      <div class="mt-4 flex justify-between space-x-2">
        <button on:click={() => handleSocialLogin('카카오')} class="w-full bg-yellow-400 text-black py-2 rounded hover:bg-yellow-500 transition">{isLoginMode ? '카카오 로그인' : '카카오로 회원가입'}</button>
        <button on:click={() => handleSocialLogin('깃허브')} class="w-full bg-gray-800 text-white py-2 rounded hover:bg-gray-900 transition">{isLoginMode ? '깃허브 로그인' : '깃허브로 회원가입'}</button>
      </div>
    </div>
  </div>
{/if}

<style>
  /* Tailwind CSS 기본 설정을 사용하므로 별도 스타일 정의는 최소화 */
</style>
