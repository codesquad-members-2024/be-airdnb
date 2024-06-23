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

  let listComponentRef;
  let mapComponentRef;

  const loadMoreItems = async () => {
    if (isLoading || !hasMore) return;
    isLoading = true;

    const checkInDate = checkIn || new Date().toISOString().split('T')[0];
    const checkOutDate = checkOut || new Date(Date.now() + 7 * 24 * 60 * 60 * 1000).toISOString().split('T')[0];

    if (currentLocation) {
      const { lat, lng } = currentLocation;
      const response = await fetch(`http://localhost:8080/accommodation/search?capacity=${totalGuests}&min_dayrate=${selectedMinPrice}&max_dayrate=${selectedMaxPrice}&checkin_date=${checkInDate}&checkout_date=${checkOutDate}&page=${page}&lat=${lat}&lng=${lng}&radius=10`);
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
    console.log(`Marker clicked for item: ${itemId}`);
    if (listComponentRef && listComponentRef.focusItem) {
      listComponentRef.focusItem(itemId);
    }
  };

  const handleViewOnMap = (event) => {
    const itemId = event.detail.id;
    console.log(`View on map for item: ${itemId}`);
    if (mapComponentRef && mapComponentRef.focusMarker) {
      mapComponentRef.focusMarker(itemId);
    }
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
  <div class="w-1/2">
    {#if currentLocation}
      <MapComponent {currentLocation} {items} on:markerClick={handleMarkerClick} bind:this={mapComponentRef} />
    {/if}
  </div>
</div>
