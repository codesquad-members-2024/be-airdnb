<script>
    import {auth} from "../../store/Auth.js";
    import {goto} from "$app/navigation";

    $: userData = {
        accountName: '',
        password: '',
    }
    let error = '';

    export let isLoginBtnClicked;
    $: isLoginBtnClicked;
    const handleLoginBtnClicked = () => {
        isLoginBtnClicked = !isLoginBtnClicked;
    }

    const handleLogin = async (userData) => {
        if(userData.accountName.length > 50
            || userData.accountName.length < 4
            || userData.password.length > 30
            || userData.password.length < 4){
            alert("잘못된 아이디 비밀번호 형식입니다");
            return;
        }
        await auth.login(userData);
        alert(`환영합니다 ${$auth.nickname}님`);
        isLoginBtnClicked = false;
    };

    const handleKakaoLogin = () => {
        window.location.href = 'http://3.35.215.131/api/auth/login/oauth/kakao';
    };
</script>

<div class="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center z-30">
    <div class="bg-white p-10 rounded-lg w-96 text-center relative shadow-lg">
        <span class="absolute top-3 right-3 text-2xl cursor-pointer" on:click={handleLoginBtnClicked}>&times;</span>
        <h2 class="text-2xl mb-4">로그인</h2>
        {#if error}
            <p class="text-red-500 mb-4">{error}</p>
        {/if}
        <form on:submit|preventDefault class="flex flex-col items-center">
            <label class="flex flex-col items-start mb-4 w-full">
                아이디
                <input type="text" bind:value={userData.accountName} class="w-full p-2 mt-2 border border-gray-300 rounded" />
            </label>
            <label class="flex flex-col items-start mb-4 w-full">
                비밀번호
                <input type="password" bind:value={userData.password} class="w-full p-2 mt-2 border border-gray-300 rounded" />
            </label>
            <button type="button"
                    class="w-full p-3 mt-2 bg-blue-500 text-white rounded"
                    on:click={handleLogin(userData)}>로그인</button>
        </form>
        <button class="w-full p-3 mt-4 bg-[#FEE500] text-center text-gray-700 rounded flex justify-center items-center" on:click={handleKakaoLogin}>
            <span class="kakao-icon"></span>
            카카오 로그인
        </button>
        <hr class="border-b-airbnb-text-bold my-4"/>
        <button class="w-full p-3 mt-4 border border-gray-500 text-center text-gray-700 rounded flex justify-center items-center" on:click={() => {goto('/oauth/register')}}>
            회원가입
        </button>
    </div>
</div>