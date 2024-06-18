<script>
    import { onDestroy, onMount } from "svelte";
    import ProfilePopup from "./ProfilePopup.svelte";
    import SearchBar from "./SearchBar.svelte";
    import SearchBarMini from "./SearchBarMini.svelte";

    export let scrollMode;
    export let login;

    let isSearchWidgetShow = true;

    $: login;

    function handleScroll() {
        const header = document.getElementById('header');

        if (window.scrollY > 0) {
            header.style.backgroundColor = '#ffffff';
            header.classList.add('shadow-md')
            header.style.zIndex = '99'
            isSearchWidgetShow = false;
        } else {
            header.style.backgroundColor = 'transparent';
            header.classList.remove('shadow-md')
            isSearchWidgetShow = true;
        }
    }

    // 스크롤 이벤트 리스너 추가
    onMount(() => {
        if (scrollMode) {
            window.addEventListener('scroll', handleScroll);
        }

        console.log('scrollmode', scrollMode)
        console.log('isSearchWidgetShow', isSearchWidgetShow)
    })

    // 스크롤 이벤트 리스너 제거
    onDestroy(() => {
        if (scrollMode) {
            window.removeEventListener('scroll', handleScroll);
        }
    });

    // 프로필 팝업창 토글
    let onProfilePopup = false;

    const onProfilePopupClick = () => {
        onProfilePopup = !onProfilePopup;
    }

    const closeProfilePopup = () => {
        onProfilePopup = false;
    }

    function handleLogout(event) {
        console.log('logout', event.detail)
        login = null;
    }
</script>

<header id="header" class="fixed top-0 left-0 w-screen z-[10]" class:shadow-md={!scrollMode} class:bg-white={!scrollMode}>
    <div class="flex h-[92px] px-[4rem] py-[1.5rem] justify-between items-center">
        <div id="logo w-[88px] h-[46px]">
            <a class="no-hover-color-effect text-4xl italic" href="/">
                <svg width="85" height="26" viewBox="0 0 85 26" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M0.752 25H15.76V20.2H6.48V1.16H0.752V25ZM27.046 25.448C33.542 25.448 37.958 20.776 37.958 12.968C37.958 5.192 33.542 0.743999 27.046 0.743999C20.55 0.743999 16.134 5.16 16.134 12.968C16.134 20.776 20.55 25.448 27.046 25.448ZM27.046 20.52C23.91 20.52 21.99 17.576 21.99 12.968C21.99 8.36 23.91 5.64 27.046 5.64C30.182 5.64 32.134 8.36 32.134 12.968C32.134 17.576 30.182 20.52 27.046 20.52ZM51.8182 25.448C55.1462 25.448 58.0903 24.136 59.7542 22.536V11.4H50.8902V16.072H54.6663V19.912C54.1542 20.296 53.2582 20.52 52.4262 20.52C47.9782 20.52 45.8662 17.768 45.8662 13.032C45.8662 8.392 48.3942 5.64 51.9142 5.64C53.8982 5.64 55.1462 6.44 56.3302 7.496L59.3382 3.848C57.7062 2.216 55.2102 0.743999 51.7222 0.743999C45.3542 0.743999 40.0102 5.288 40.0102 13.224C40.0102 21.288 45.2262 25.448 51.8182 25.448ZM73.111 25.448C79.607 25.448 84.023 20.776 84.023 12.968C84.023 5.192 79.607 0.743999 73.111 0.743999C66.615 0.743999 62.199 5.16 62.199 12.968C62.199 20.776 66.615 25.448 73.111 25.448ZM73.111 20.52C69.975 20.52 68.055 17.576 68.055 12.968C68.055 8.36 69.975 5.64 73.111 5.64C76.247 5.64 78.199 8.36 78.199 12.968C78.199 17.576 76.247 20.52 73.111 20.52Z" fill="#333333"/>
                </svg>
            </a>
        </div>
        <div class="flex gap-8 justify-between items-center whitespace-nowrap">
            {#if scrollMode === false}
                <SearchBarMini />
            {:else if scrollMode === true && isSearchWidgetShow === false}
                <SearchBarMini />
            {:else}
                <button type="button" class="underlineBtn">
                    숙소
                </button>
                <button type="button" class="underlineBtn">
                    체험
                </button>
                <button type="button" class="underlineBtn">
                    온라인 체험
                </button>
            {/if}
        </div>
        <button type="button" class="account border border-gray-400/70 flex justify-between items-center gap-2 p-4 w-[78px] h-[40px] text-sm rounded-full bg-white hover:drop-shadow-lg active:transition-all active:duration-100 active:scale-[0.98]" on:click={onProfilePopupClick}>
            <span class="icon">
                <svg width="16" height="16" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M3 12H21" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <path d="M3 6H21" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <path d="M3 18H21" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </span>
            {#if login === true}
                <img class="w-[30px] h-[30px] rounded-full" src={JSON.parse(localStorage.getItem('jwt')).profileImage} alt="프로필 이미지"/>
            {:else}
                <span class="user flex justify-center items-center size-[32px] font-bold p-[7px] text-[8px] rounded-full text-white bg-gray-500">
                <svg width="30" height="30" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M20 21V19C20 17.9391 19.5786 16.9217 18.8284 16.1716C18.0783 15.4214 17.0609 15 16 15H8C6.93913 15 5.92172 15.4214 5.17157 16.1716C4.42143 16.9217 4 17.9391 4 19V21" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <path d="M12 11C14.2091 11 16 9.20914 16 7C16 4.79086 14.2091 3 12 3C9.79086 3 8 4.79086 8 7C8 9.20914 9.79086 11 12 11Z" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </span>
            {/if}
        </button>
        {#if onProfilePopup}
            <div class="overlay" on:click={closeProfilePopup}></div>
            <ProfilePopup bind:onProfilePopup={onProfilePopup} on:logout={handleLogout} />
        {/if}
    </div>
    {#if scrollMode === true && isSearchWidgetShow === true}
        <SearchBar />
    {/if}
</header>
