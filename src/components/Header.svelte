<script>
  import { onMount } from 'svelte';
  import LoginPopup from "./LoginPopup.svelte";
  import MiniSearchBar from "./MiniSearchBar.svelte";
  import SearchBar from "./SearchBar.svelte";

  let showMiniSearchBar = false;
  let header;
  let checkIn = '';
  let checkOut = '';
  let selectedMinPrice = 100000;
  let selectedMaxPrice = 1000000;
  let totalGuests = 0;

  onMount(() => {
    const handleScroll = () => {
      const scrollPosition = window.scrollY;
      showMiniSearchBar = scrollPosition > 200; // 200px 스크롤 시 MiniSearchBar 보이기
    };

    window.addEventListener('scroll', handleScroll);

    return () => {
      window.removeEventListener('scroll', handleScroll);
    };
  });
</script>

<header bind:this={header} class="fixed top-0 left-0 w-full bg-transparent text-white z-50 whitespace-nowrap">
  <div class="container mx-auto flex justify-between items-center py-4 px-6">
    
    <!-- 왼쪽 로고 -->
    <div>
      <a href="/">
        <img src="/assets/logo.png" alt="Logo" class="h-16">
      </a>
    </div>
    
    <!-- 중앙 버튼들 -->
    <div class="flex justify-center space-x-6">
      {#if !showMiniSearchBar}
        <button class="text-xl text-black bg-transparent hover:font-bold hover:underline transition duration-300">숙소</button>
        <button class="text-xl text-black bg-transparent hover:font-bold hover:underline transition duration-300">체험</button>
        <button class="text-xl text-black bg-transparent hover:font-bold hover:underline transition duration-300">온라인 체험</button>
      {/if}
      {#if showMiniSearchBar}
        <MiniSearchBar bind:checkIn={checkIn} bind:checkOut={checkOut} bind:selectedMinPrice={selectedMinPrice} bind:selectedMaxPrice={selectedMaxPrice} bind:totalGuests={totalGuests} />
      {/if}
    </div>

    <!-- 오른쪽 로그인 컴포넌트 및 미니 검색 바 -->
    <div class="flex items-center space-x-4">
      <LoginPopup />
    </div>
  </div>

  <!-- Search Bar -->
  <div class="absolute w-full flex justify-center">
    {#if !showMiniSearchBar}
      <div class="w-full max-w-4xl">
        <SearchBar bind:checkIn={checkIn} bind:checkOut={checkOut} bind:selectedMinPrice={selectedMinPrice} bind:selectedMaxPrice={selectedMaxPrice} bind:totalGuests={totalGuests} />
      </div>
    {/if}
  </div>
</header>
