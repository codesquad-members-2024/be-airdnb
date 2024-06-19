<script>
    import {onMount} from "svelte";
    import axios from "axios";

    onMount(() => {
        const searchParams = new URLSearchParams(window.location.search);
        const code = searchParams.get('code');
        if (code) {
            alert("CODE = " + code);
            handleOAuthKakao(code);
        }
    });

    const handleOAuthKakao = async (code) => {
        try {
            const response = await axios.post(`http://localhost:8080/api/login/oauth/authenticate?code=${code}`);
            localStorage.setItem("token", code);
            alert("로그인 성공: " + response.data);
        } catch (err) {
            alert("실패...");
        }
    }
</script>

<div>
    <div class="font-extrabold text-2xl">
        Loading...
    </div>
</div>