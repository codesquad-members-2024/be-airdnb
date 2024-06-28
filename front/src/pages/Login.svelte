<script>
    import LoginForm from "../components/LoginForm.svelte";
    import RegisterForm from "../components/RegisterForm.svelte";

    let registerToggle = false
    $: registerToggle
</script>

<div class="centered-container flex justify-center items-center min-h-screen">
    <div class="flex-col">
        <div class="flex flex-col my-10 justify-center items-center">
            <img class="animate-slidein300" src="/assets/ylogo.png" alt="logo" />

            <hr class="text-divider animate-slidein300">

            <a href="https://airdnb-team04.shop/oauth2/authorization/github" role="button" type="button" class="login-btn-oauth animate-slidein300" >
                <span class="mr-1">
                    <i class="bi bi-github"></i>
                </span>
                GitHub 계정으로 로그인하기
            </a>

            <a href="https://airdnb-team04.shop/oauth2/authorization/google" role="button" type="button" class="login-btn-oauth animate-slidein300">
                <span class="mr-1">
                    <i class="bi bi-google"></i>
                </span>
                Google 계정으로 로그인하기
            </a>

            <a href="https://airdnb-team04.shop/oauth2/authorization/kakao" role="button" type="button" class="login-btn-oauth animate-slidein300">
                <span class="mr-1">
                    <img class="size-[15px] -translate-y-0.5 -translate-x-0.5" src="https://upload.wikimedia.org/wikipedia/commons/thumb/d/dd/Kakao_Corp._symbol_-_2012.svg/800px-Kakao_Corp._symbol_-_2012.svg.png?20151013114543" alt="kakao" />
                </span>
                Kakao 계정으로 로그인하기
            </a>

            <hr class="text-divider animate-slidein300">

            <div class="auth-form animate-slidein300">
                {#if !registerToggle}
                    <LoginForm bind:registerToggle={registerToggle} />
                {:else}
                    <RegisterForm bind:registerToggle={registerToggle} />
                {/if}
            </div>
        </div>
    </div>
</div>