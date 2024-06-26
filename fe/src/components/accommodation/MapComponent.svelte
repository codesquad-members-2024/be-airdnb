<script>
  import { onMount, afterUpdate, createEventDispatcher } from 'svelte';

  export let currentLocation = { lat: 37.5665, lng: 126.9780 };
  export let items = [];

  let mapContainer;
  let map;
  let markers = [];
  let selectedMarkerId = null;
  let currentZoomLevel = 6; // 초기 줌 레벨

  const dispatch = createEventDispatcher();

  const createMarker = (position, text, id) => {
    const markerContent = document.createElement('div');
    const isSelected = selectedMarkerId === id;
    markerContent.innerHTML = `<div class="${isSelected ? 'bg-red-400 text-white' : 'bg-white'} font-bold px-2 rounded-md select-none shadow-lg">₩${text}</div>`;

    markerContent.addEventListener('click', () => {
      dispatch('markerClick', { id });
      selectedMarkerId = id;
      addMarkers(); 
    });

    const marker = new kakao.maps.CustomOverlay({
      position: position,
      content: markerContent,
      yAnchor: 1.5,
      zIndex: isSelected ? 10 : 0
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

  const moveToCurrentLocation = () => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;
        const locPosition = new kakao.maps.LatLng(lat, lng);
        map.panTo(locPosition);

        // 현재 위치로 이동한 후 API 호출
        dispatch('mapDragEnd', { lat, lng, zoom: currentZoomLevel });
      });
    } else {
      alert('Geolocation is not supported by this browser.');
    }
  };

  onMount(() => {
    if (typeof kakao !== 'undefined') {

      const options = {
        center: new kakao.maps.LatLng(currentLocation.lat, currentLocation.lng),
        level: currentZoomLevel
      };
      map = new kakao.maps.Map(mapContainer, options);

      kakao.maps.event.addListener(map, 'dragend', () => {
        const center = map.getCenter();
        dispatch('mapDragEnd', { lat: center.getLat(), lng: center.getLng(), zoom: currentZoomLevel });
      });

      kakao.maps.event.addListener(map, 'zoom_changed', () => {
        currentZoomLevel = map.getLevel();
        const center = map.getCenter();
        dispatch('mapDragEnd', { lat: center.getLat(), lng: center.getLng(), zoom: currentZoomLevel });
      });

      addMarkers();
    }
  });

  afterUpdate(() => {
    if (map) {
      addMarkers();
    }
  });

  export function focusMarker(itemId) {
    selectedMarkerId = itemId;
    const item = items.find(i => i.accommodationId === itemId);
    if (item) {
      const position = new kakao.maps.LatLng(item.coordinate.latitude, item.coordinate.longitude);
      map.panTo(position);
      addMarkers(); 
    }
  }
</script>

<div class="relative h-full" bind:this={mapContainer}></div>

<button 
  class="absolute top-2 right-2 transform -translate-x-1/2 bg-white hover:text-gray-600 text-black px-2 py-2 rounded shadow-lg z-50"
  on:click={moveToCurrentLocation}
>
  <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke-width="1.5" stroke="currentColor" class="w-6 h-6">
    <path stroke-linecap="round" stroke-linejoin="round" d="M15 10.5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
    <path stroke-linecap="round" stroke-linejoin="round" d="M19.5 10.5c0 7.142-7.5 11.25-7.5 11.25S4.5 17.642 4.5 10.5a7.5 7.5 0 1 1 15 0Z" />
  </svg>
</button>
