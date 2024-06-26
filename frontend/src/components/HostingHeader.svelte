<script>
    import profile_icon from "$lib/assets/image/profile_icon.svg";
    import {isLoggedIn} from "../store/Auth.js";
    import ProfilePopup from "./ProfilePopup.svelte";
    import LoginModal from "../routes/accommodations/LoginModal.svelte";

    $: isLoginBtnClicked = false;
    const handleLoginBtnClick = () => {
        isLoginBtnClicked = !isLoginBtnClicked;
    }

    $: isProfileBtnClicked = false;
    const handleProfileBtnClick = () => {
        isProfileBtnClicked = !isProfileBtnClicked;
    }

</script>

<header class="flex flex-col mb-[3rem] relative z-20 border-b border-b-gray-300">
    <div id="logo" class="max-w-[1440px] mx-auto w-full flex items-center justify-between p-4 py-6">
        <a class="text-2xl font-semibold" href="/hosting">Airdnb</a>
        <nav class="hidden md:flex items-center gap-[30px] lg:gap-6">
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting">투데이</a>
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting">달력</a>
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting">숙소</a>
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting">메시지</a>
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting/accommodations/create">숙소 등록</a>
        </nav>
        <button class="hidden md:flex m-0.5" on:click={$isLoggedIn ? handleProfileBtnClick : handleLoginBtnClick}>
            <img src="{profile_icon}" alt="User Profile Icon">
        </button>
        {#if isProfileBtnClicked && $isLoggedIn}
            <ProfilePopup bind:isProfileBtnClicked="{isProfileBtnClicked}"/>
        {/if}
        <button class="md:hidden grid place-items-center">
            <i class="fa-solid fa-bars"></i>
        </button>
    </div>
    {#if isLoginBtnClicked && !$isLoggedIn}
        <LoginModal bind:isLoginBtnClicked="{isLoginBtnClicked}"/>
    {/if}
</header>