<script>
  import { onMount } from 'svelte';

  export let currentLocation = { lat: 37.5665, lng: 126.9780 }; // 기본 위치를 서울로 설정

  let mapContainer;
  let map;

  onMount(() => {
    if (typeof kakao !== 'undefined') {
      const options = {
        center: new kakao.maps.LatLng(currentLocation.lat, currentLocation.lng),
        level: 3
      };
      map = new kakao.maps.Map(mapContainer, options);
    }
  });
</script>

<div class="h-full" bind:this={mapContainer}></div>
