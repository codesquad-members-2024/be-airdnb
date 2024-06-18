<script>
    import { onDestroy } from "svelte";
    import { NaverMap, createMapScript, NaverMarker, NaverMarkerCluster } from "svelte-naver-maps";

    createMapScript({ clientId: "g8ulnqaykq" });

    let map;

    const mapOptions = {
        zoom: 17,
        latitude: 37.490776,
        longitude: 127.033480,
        width: "100%",
        height: "100%"
    };

    onDestroy(() => {
        if (map && map.$destroy) {
            map.$destroy();
        }
    });
</script>

<style>

</style>

<NaverMap mapOptions={mapOptions} bind:this={map}>
    <NaverMarkerCluster>
        <NaverMarker latitude={37.490776} longitude={127.033480} />
    </NaverMarkerCluster>
</NaverMap>