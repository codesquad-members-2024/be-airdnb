<script>
  import { onMount } from 'svelte';
  import LoginPagePopup from './LoginPagePopup.svelte';

  let isOpen = false;
  let showLoginModal = false;
  
  let user = {
    "userEmail": null,
    "username": null, // 초기에는 null로 설정
    "profileImage": "/assets/profile.png" // 기본 프로필 이미지 경로
  };

  function togglePopup() {
    isOpen = !isOpen;
  }

  function closePopup() {
    isOpen = false;
  }

  function login(userEmail, username, profileImage) {
    user.userEmail = userEmail;
    user.username = username;
    user.profileImage = profileImage;
    showLoginModal = false;
  }

  function logout() {
    user.userEmail = null;
    user.username = null;
    user.profileImage = "/assets/profile.png"; // 기본 프로필 이미지로 되돌리기
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
  }

  const toggleLoginModal = () => {
    showLoginModal = !showLoginModal;
  };

  function handleLogin(event) {
    const userData = event.detail;
    login(userData.userEmail, userData.username, userData.profileImage);
    toggleLoginModal();
  }

  onMount(() => {
    const token = localStorage.getItem('accessToken');
    if (token) {
      try {
        // Extract payload part of JWT
        const base64Url = token.split('.')[1];
        const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
        const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
          return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
        }).join(''));

        const decodedToken = JSON.parse(jsonPayload);

        login(decodedToken.memberId, decodedToken.memberName, decodedToken.memberProfile);
      } catch (error) {
        console.error('Error decoding token on mount:', error);
      }
    }
  });
</script>

<div class="relative inline-block text-left">
  <div>
    <button on:click={togglePopup} class="inline-flex justify-center space-x-2 items-center w-full rounded-full border border-gray-300 shadow-sm px-4 py-2 bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-indigo-500">
      <!-- Menu Icon -->
      <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="h-6 w-6">
        <path stroke-linecap="round" stroke-linejoin="round" d="M3.75 6.75h16.5M3.75 12h16.5m-16.5 5.25h16.5" />
      </svg>
      <!-- Profile Image -->
      <img src={user.profileImage} alt="User Profile" class="h-6 w-6 ml-2 rounded-full">
    </button>
  </div>
  
  {#if isOpen}
    <div class="origin-top-right absolute right-0 mt-2 w-56 rounded-md shadow-lg bg-white ring-1 ring-black ring-opacity-5 z-20">
      <div class="py-1" role="menu" aria-orientation="vertical" aria-labelledby="options-menu">
        {#if user.username === null}
          <!-- User is not logged in -->
          <button on:click={() => { closePopup(); toggleLoginModal(); }} class="w-full text-left block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900">로그인</button>
        {:else}
          <!-- User is logged in -->
          <div class="px-4 py-2 text-sm text-black font-bold">안녕하세요, {user.username}님</div>
          <button on:click={() => { closePopup(); window.location.href = '/trips'; }} class="w-full text-left block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900">여행</button>
          <button on:click={closePopup} class="w-full text-left block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900">채팅</button>
          <button on:click={() => { closePopup(); logout(); }} class="w-full text-left block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100 hover:text-gray-900">로그아웃</button>
        {/if}
      </div>
    </div>
  {/if}
</div>

<!-- LoginPagePopup Modal -->
<LoginPagePopup show={showLoginModal} onClose={toggleLoginModal} on:login={handleLogin} />

<style>
  /* Tailwind CSS 기본 설정을 사용하므로 별도 스타일 정의는 최소화 */
</style>
