<script>
    import { onMount } from 'svelte';
    import axios from 'axios';

    let username = '';
    let password = '';
    let error = '';

    export let isAccoCardClicked;
    $: isAccoCardClicked;
    const handleAccoCardClicked = () => {
        isAccoCardClicked = !isAccoCardClicked;
    }

    const handleLogin = async (event) => {
        event.preventDefault();
    };
</script>

<div class="fixed top-0 left-0 w-full h-full bg-black bg-opacity-50 flex justify-center items-center z-30">
    <div class="bg-white p-10 rounded-lg w-96 text-center relative shadow-lg">
        <span class="absolute top-3 right-3 text-2xl cursor-pointer" on:click={handleAccoCardClicked}>&times;</span>
        <h2 class="text-2xl mb-4">Login</h2>
        {#if error}
            <p class="text-red-500 mb-4">{error}</p>
        {/if}
        <form on:submit|preventDefault={handleLogin} class="flex flex-col items-center">
            <label class="flex flex-col items-start mb-4 w-full">
                Username:
                <input type="text" bind:value={username} class="w-full p-2 mt-2 border border-gray-300 rounded" />
            </label>
            <label class="flex flex-col items-start mb-4 w-full">
                Password:
                <input type="password" bind:value={password} class="w-full p-2 mt-2 border border-gray-300 rounded" />
            </label>
            <button type="submit" class="w-full p-3 mt-2 bg-blue-500 text-white rounded">Login</button>
        </form>
        <button class="w-full p-3 mt-4 bg-[#FEE500] text-center text-gray-700 rounded flex justify-center items-center" on:click={handleGithubLogin}>
            <span class="kakao-icon"></span>
            카카오 로그인
        </button>
    </div>
</div>