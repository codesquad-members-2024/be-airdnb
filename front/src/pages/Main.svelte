<script>
    import Footer from "../components/Footer.svelte";
    import MainContents from "../components/MainContents.svelte";
    import BackgroundImage from "../components/BackgroundImage.svelte";
    import Header from "../components/Header.svelte";
    import { writable } from "svelte/store";
    import { onMount } from "svelte";

    // Create a writable store for the JWT token
    export const jwtToken = writable("");

    let login = false;

    // Function to store JWT token from URL parameters
    const getTokens = () => {
        const tokens = {
            "jwt-token": "",
            "refresh-token": ""
        }

        const cookies = document.cookie.split(';');

        for (let i = 0; i < cookies.length; i++) {
            let cookie = cookies[i].trim();
            if (cookie.indexOf('jwt-token=') === 0) {
                console.log("jwt cookie!", cookie.substring(10, cookie.length));
                tokens.jwt = cookie.substring(10, cookie.length);
            }
            if (cookie.indexOf('refresh-token=') === 0) {
                console.log("ref cookie!", cookie.substring(14, cookie.length));
                tokens.refresh = cookie.substring(14, cookie.length);
            }
        }
        return tokens;
    }

    const storeJwtToken = (name, token) => {
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

        if (payload) {
            jwtToken.set(payload);
            localStorage.setItem(name, JSON.stringify(payload));
            console.log(`${name} token!`, payload)
        }
    };

    // Store the JWT token on mount
    onMount(() => {
        const tokens = getTokens();

        if (tokens.jwt !== undefined) {
            storeJwtToken('jwt', tokens.jwt);
            storeJwtToken('refresh', tokens.refresh);
        }

        if (localStorage.getItem('jwt') !== null) {
            login = true;
        }
    });
</script>

<Header scrollMode={true} login={login}/>

<div class="mb-16">
    <BackgroundImage />
</div>

<MainContents />

<Footer />