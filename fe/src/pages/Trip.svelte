<script>
    import Footer from "../components/Footer.svelte";
    import Header from "../components/Header.svelte";
    import Reservation from "../components/reservation/Reservation.svelte";
    import { onMount } from "svelte";

    onMount(() => {
        const accessToken = localStorage.getItem("accessToken");
        if (!accessToken) {
            window.location.href = '/';
            return;
        }
    });
</script>

<Header state={true} />

<Reservation />

<Footer />
