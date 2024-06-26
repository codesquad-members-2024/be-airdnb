import {get, writable} from "svelte/store";
import {postApi} from "../service/api.js";
import {goto} from "$app/navigation";

function setAuth() {
    const initValue = {
        accountName: '',
        loginType: '',
        nickname: '',
        accessToken: ''
    };

    const { subscribe, set, update } = writable({...initValue});

    const handleAuthResponse = (authResponse) => {
        const token = authResponse.accessToken;
        const base64Payload = token.split('.')[1];
        const base64 = base64Payload.replace(/-/g, '+').replace(/_/g, '/');
        const payload = JSON.parse(
            decodeURIComponent(
                window.atob(base64).split('')
                    .map(function (c) {
                        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
                    })
                    .join('')
            )
        );
        const updateData = {
            accountName: payload.sub,
            loginType: payload.loginType,
            nickname: authResponse.nickname,
            accessToken: authResponse.accessToken
        };
        update(data => ({
            ...data,
            ...updateData
        }));
        isRefreshed.set(true);
    };

    // 토큰 갱신 함수
    const refresh = async () => {
        try {
            const authResponse = await postApi({ path: '/auth/refresh' });
            handleAuthResponse(authResponse);
        } catch (err) {
            resetUserInfo();
            isRefreshed.set(false);
        }
    };

    const resetUserInfo = () => set({...initValue});

    const register = async (data) => {
        const options = {
            path: "/auth/register",
            data: {
                accountName: data.accountName,
                loginPassword: data.loginPassword,
                nickname: data.nickname,
            }
        }

        const authResponse = await postApi(options);
        handleAuthResponse(authResponse);
        isLoggedIn.set(true);
        alert("가입이 완료되었습니다");
    }

    const login = async (data) => {
        const options = {
            path: "/auth/login",
            data: {
                accountName: data.accountName,
                loginPassword: data.loginPassword,
                nickname: data.nickname
            }
        }

        const authResponse = await postApi(options);
        handleAuthResponse(authResponse);
        isLoggedIn.set(true);
    }

    const oauthLogin = async (code) => {
        const options = {
            path: `/auth/login/oauth/authenticate?code=${code}`,
        }

        const authResponse = await postApi(options);
        handleAuthResponse(authResponse);
        isLoggedIn.set(true);
        const {nickname} = get(auth);
        alert(`환영합니다 ${nickname}님`);
        await goto("/accommodations");
    }

    const logout = async (data) => {
        await postApi({ path:'/auth/logout' });
        isRefreshed.set(false);
        isLoggedIn.set(false);
        auth.accessToken = '';
        await goto("/accommodations");
    }

    return {
        subscribe,
        register,
        login,
        logout,
        oauthLogin,
    }
}

export const isRefreshed = writable(false);
export const isLoggedIn = writable(false);
export const auth = setAuth();
