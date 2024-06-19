<script>
    // 필요한 경우 여기에 스크립트를 추가할 수 있습니다.
    import LoginPopup from "./LoginPopup.svelte";
</script>

<header class="fixed top-0 left-0 w-full bg-transparent text-white shadow-md z-50">
    <div class="container mx-auto flex justify-between items-center py-4 px-6">
      
      <!-- 왼쪽 로고 -->
      <div class="text-lg font-semibold text-black">
        <a href="/">
          <img src="/assets/logo.png" alt="Logo" class="h-16">
        </a>
      </div>
      
      <!-- 중앙 버튼들 -->
      <div class="flex justify-center space-x-6">
        <button class="text-lg text-black bg-transparent hover:font-bold hover:underline transition duration-300">숙소</button>
        <button class="text-lg text-black bg-transparent hover:font-bold hover:underline transition duration-300">체험</button>
        <button class="text-lg text-black bg-transparent hover:font-bold hover:underline transition duration-300">온라인 체험</button>
      </div>
  
      <!-- 오른쪽 로그인 컴포넌트 -->
      <div class="flex justify-end">
        <LoginPopup />
        <button>로그인</button> <!-- 예시로 로그인 버튼 추가 -->
      </div>
  
    </div>
</header>
