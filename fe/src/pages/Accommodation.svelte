<script>
  import Header from "../components/Header.svelte";
  import { onMount } from 'svelte';
  import MapComponent from "../components/accommodation/MapComponent.svelte";
  import ListComponent from "../components/accommodation/ListComponent.svelte";

  let checkIn = '';
  let checkOut = '';
  let selectedMinPrice = 100000;
  let selectedMaxPrice = 1000000;
  let totalGuests = 0;
  let length = 0;
  let currentLocation = null;
  let items = [];
  let page = 0;
  let isLoading = false;
  let hasMore = true;
  let currentZoomLevel = 8; // 초기 줌 레벨
  

  let listComponentRef;
  let mapComponentRef;

  let API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
  if (API_BASE_URL.endsWith('/')) {
    API_BASE_URL = API_BASE_URL.slice(0, -1);
  }

  const calculateRadius = (zoomLevel) => {
    // 줌 레벨에 따라 반경을 계산하는 로직을 추가하세요
    // 예시: 줌 레벨 8일 때 10km, 줌 레벨 9일 때 5km 등
    const radiusByZoom = {
      5: 1,
      6: 3,
      7: 5,
      8: 10,
      9: 20,
      10: 40,
      11: 80,
      // 추가 줌 레벨 및 반경 값을 여기에 추가
    };
    return radiusByZoom[zoomLevel] || 10; // 기본값은 10km
  };

  const loadMoreItems = async () => {
    if (isLoading || !hasMore) return;
    isLoading = true;

    const checkInDate = checkIn || new Date().toISOString().split('T')[0];
    const checkOutDate = checkOut || new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0];
    const radius = calculateRadius(currentZoomLevel);

    if (currentLocation) {
      const { lat, lng } = currentLocation;
      const response = await fetch(`${API_BASE_URL}/accommodation/search?capacity=${totalGuests}&min_dayrate=${selectedMinPrice}&max_dayrate=${selectedMaxPrice}&checkin_date=${checkInDate}&checkout_date=${checkOutDate}&page=${page}&lat=${lat}&lng=${lng}&radius=${radius}`);
      const data = await response.json();

      if (data.content.length === 0) {
        hasMore = false;
      } else {
        items = [...items, ...data.content];
        page += 1;
      }
    }

    isLoading = false;
  };

  onMount(async () => {
    const urlParams = new URLSearchParams(window.location.search);
    checkIn = urlParams.get('checkin') || '';
    checkOut = urlParams.get('checkout') || '';
    selectedMinPrice = Number(urlParams.get('price_min')) || 100000;
    selectedMaxPrice = Number(urlParams.get('price_max')) || 1000000;
    totalGuests = Number(urlParams.get('capacity')) || 0;
    length = Number(urlParams.get('length')) || 0;

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        currentLocation = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
        loadMoreItems(); // 위치 정보를 가져온 후에 데이터를 로드
      }, (error) => {
        console.error('Error occurred while retrieving location: ', error);
        currentLocation = { lat: 37.5665, lng: 126.9780 };
        loadMoreItems(); // 위치 정보를 가져올 수 없을 때 데이터를 로드
      });
    } else {
      currentLocation = { lat: 37.5665, lng: 126.9780 };
      await loadMoreItems(); // 위치 정보가 사용 불가능할 때 데이터를 로드
    }
  });

  const handleScroll = async (event) => {
    const { scrollTop, scrollHeight, clientHeight } = event.target;
    if (scrollTop + clientHeight >= scrollHeight - 100) {
      await loadMoreItems();
    }
  };

  const handleMarkerClick = (event) => {
    const itemId = event.detail.id;
    if (listComponentRef && listComponentRef.focusItem) {
      listComponentRef.focusItem(itemId);
    }
  };

  const handleViewOnMap = (event) => {
    const itemId = event.detail.id;
    if (mapComponentRef && mapComponentRef.focusMarker) {
      mapComponentRef.focusMarker(itemId);
    }
  };

  const handleMapDragEnd = (event) => {
    currentLocation = {
      lat: event.detail.lat,
      lng: event.detail.lng
    };
    currentZoomLevel = event.detail.zoom;
    page = 0;
    items = [];
    hasMore = true;
    loadMoreItems();
  };

</script>

<Header 
  state={true} 
  bind:checkIn={checkIn} 
  bind:checkOut={checkOut} 
  bind:selectedMinPrice={selectedMinPrice} 
  bind:selectedMaxPrice={selectedMaxPrice} 
  bind:totalGuests={totalGuests} 
  bind:length = {length}
/>

<div class="flex h-screen pt-28">
  <div class="w-1/2 overflow-y-auto p-4" on:scroll={handleScroll} on:viewOnMap={handleViewOnMap}>
    <ListComponent {items} {checkIn} {checkOut} {totalGuests} {selectedMinPrice} {selectedMaxPrice} {length} bind:this={listComponentRef} />
    {#if isLoading}
      <p>Loading...</p>
    {/if}
  </div>
  <div class="w-1/2 relative">
    {#if currentLocation}
      <MapComponent {currentLocation} {items} on:markerClick={handleMarkerClick} on:mapDragEnd={handleMapDragEnd} bind:this={mapComponentRef} />
    {/if}
  </div>
</div>
