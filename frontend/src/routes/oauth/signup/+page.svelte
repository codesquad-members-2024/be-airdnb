<script>
    import {auth} from "../../../store/auth.js";
    import {goto} from "$app/navigation";

    let userData = {
        loginId: '',
        nickname: '',
        loginPassword: '',
    }

    let confirmPassword = '';
    let loginIdValid = true;
    let nicknameValid = true;
    let loginPasswordValid = true;
    let confirmPasswordValid = true;

    function validateInput() {
    loginIdValid = userData.loginId.length >= 4 && userData.loginId.length <= 50;
    nicknameValid = userData.nickname.length >= 4 && userData.nickname.length <= 50;
    loginPasswordValid = userData.loginPassword.length >= 4 && userData.loginPassword.length <= 30;
    confirmPasswordValid = confirmPassword === userData.loginPassword;
    }

    function handleSubmit(event) {
        event.preventDefault();
        validateInput();

        if (loginIdValid && nicknameValid && loginPasswordValid && confirmPasswordValid) {
            auth.signUp(userData);
            goto("/accommodations");
        }
    }
</script>

<style>
    .error-border {
        border-color: red;
    }
</style>

<div class="flex flex-col items-center justify-center min-h-screen bg-gray-100">
    <div class="w-full flex-col gap-4 max-w-md p-8 min-h-[400px] space-y-6 bg-white shadow-md rounded-md">
        <h2 class="text-2xl font-bold text-center">Airdnb에 가입하세요!</h2>
        <div class="space-y-6">
            <div class="space-y-2 h-[50px]">
                <input type="text"
                       placeholder="ID"
                       class="border border-gray-300 rounded px-3 py-2 h-2/3 w-full {loginIdValid ? '' : 'error-border'}"
                       bind:value={userData.loginId}/>
                <p class="text-xs text-gray-600 mt-2">
                    최소 4자에서 최대 50자까지 입력 가능합니다
                </p>
            </div>
            <div class="space-y-2 h-[50px]">
                <input type="text"
                       placeholder="Nickname"
                       class="border border-gray-300 rounded px-3 py-2 h-2/3 w-full {nicknameValid ? '' : 'error-border'}"
                       bind:value={userData.nickname}/>
                <p class="text-xs text-gray-600 mt-1">
                    최소 4자에서 최대 50자까지 입력 가능합니다
                </p>
            </div>
            <div class="space-y-2 h-[50px]">
                <input type="password"
                       placeholder="Password"
                       class="border border-gray-300 rounded px-3 py-2 h-2/3 w-full {loginPasswordValid ? '' : 'error-border'}"
                       bind:value={userData.loginPassword}/>
                <p class="text-xs text-gray-600 mt-1">
                    최소 4자에서 최대 30자까지 입력 가능합니다
                </p>
            </div>
            <div class="space-y-2 h-[50px]">
                <input type="password"
                       placeholder="Confirm"
                       class="border border-gray-300 rounded px-3 py-2 h-2/3 w-full {confirmPasswordValid ? '' : 'error-border'}"
                       bind:value={confirmPassword}/>
            </div>
            <div class="flex items-center justify-between">
                <a href="/accommodations" class="text-sm text-blue-500">로그인하러 돌아가기</a>
                <button class="px-4 py-2 text-white bg-blue-600 rounded hover:bg-blue-700"
                on:click={handleSubmit}>
                    가입하기
                </button>
            </div>
        </div>
    </div>
</div>