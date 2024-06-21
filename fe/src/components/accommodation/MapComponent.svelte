<script>
  import { onMount } from 'svelte';

  let mapContainer;
  let map;

  onMount(() => {
    if (typeof kakao !== 'undefined') {
      const options = {
        center: new kakao.maps.LatLng(33.450701, 126.570667),
        level: 3
      };
      map = new kakao.maps.Map(mapContainer, options);
    }
  });
</script>

<div class="h-full" bind:this={mapContainer}></div>
