<script>
  import { onMount, afterUpdate, createEventDispatcher } from 'svelte';

  export let currentLocation = { lat: 37.5665, lng: 126.9780 };
  export let items = [];

  let mapContainer;
  let map;
  let markers = [];
  let selectedMarkerId = null;

  const dispatch = createEventDispatcher();

  const createMarker = (position, text, id) => {
    const markerContent = document.createElement('div');
    const isSelected = selectedMarkerId === id;
    markerContent.innerHTML = `<div class="${isSelected ? 'bg-red-400 text-white' : 'bg-white'} font-bold px-2 rounded-md select-none shadow-lg">₩${text}</div>`;

    markerContent.addEventListener('click', () => {
      dispatch('markerClick', { id });
      selectedMarkerId = id;
      addMarkers(); // Re-render markers to update the clicked state
    });

    const marker = new kakao.maps.CustomOverlay({
      position: position,
      content: markerContent,
      yAnchor: 1.5
    });

    markers.push(marker);
    marker.setMap(map);
  };

  const clearMarkers = () => {
    markers.forEach(marker => marker.setMap(null));
    markers = [];
  };

  const addMarkers = () => {
    clearMarkers();
    items.forEach(item => {
      const position = new kakao.maps.LatLng(item.coordinate.latitude, item.coordinate.longitude);
      const text = item.fee.dayRate.toLocaleString();
      createMarker(position, text, item.accommodationId);
    });
  };

  onMount(() => {
    if (typeof kakao !== 'undefined') {
      const options = {
        center: new kakao.maps.LatLng(currentLocation.lat, currentLocation.lng),
        level: 10
      };
      map = new kakao.maps.Map(mapContainer, options);
      addMarkers();
    }
  });

  afterUpdate(() => {
    if (map) {
      addMarkers();
    }
  });
</script>

<div class="h-full" bind:this={mapContainer}></div>
