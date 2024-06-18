<script>
    import { router } from "tinro";
    import { createEventDispatcher } from "svelte";

    export let onProfilePopup

    const dispatch = createEventDispatcher();

    let user = {
        "username": null,
        "profileImage": null
    }

    user = JSON.parse(localStorage.getItem('jwt'));

    console.log('popup: ', user)

    const onLogin = () => {
        router.goto("/login");

        onProfilePopup = !onProfilePopup;
    }

    const onLogout = () => {
        localStorage.removeItem('jwt');

        dispatch('logout', 'bye');

        onProfilePopup = !onProfilePopup;
    }

    const onWithdraw = () => {
        onProfilePopup = !onProfilePopup;
    }
</script>

<div class="profile-popup-container" role="menu">
    {#if user === null}
        <button class="profile-popup-btn" role="menuitem" tabindex="0" on:click={(e) => {e.stopPropagation(); onLogin();}}>
            <span class="profile-popup-btn-label">로그인</span>
        </button>
    {:else if user !== null}
        <button class="profile-popup-btn" role="menuitem" tabindex="0" on:click={(e) => {e.stopPropagation(); onLogout();}}>
            <span class="profile-popup-btn-label">예약 취소</span>
        </button>
        <button class="profile-popup-btn" role="menuitem" tabindex="0" on:click={(e) => {e.stopPropagation(); onLogout();}}>
            <span class="profile-popup-btn-label">위시리스트</span>
        </button>
        <button class="profile-popup-btn" role="menuitem" tabindex="0" on:click={(e) => {e.stopPropagation(); onLogout();}}>
            <span class="profile-popup-btn-label">로그아웃</span>
        </button>
        <button class="profile-popup-btn error" role="menuitem" tabindex="0" on:click={(e) => {e.stopPropagation(); onWithdraw();}}>
            <span class="profile-popup-btn-label">회원탈퇴</span>
        </button>
    {/if}
</div>