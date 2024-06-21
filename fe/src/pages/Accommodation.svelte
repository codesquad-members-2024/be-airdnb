<script>
    import Header from "../components/Header.svelte";
    import MiniSearchBar from "../components/MiniSearchBar.svelte";
    import { onMount } from 'svelte';
  
    let checkIn = '';
    let checkOut = '';
    let selectedMinPrice = 100000;
    let selectedMaxPrice = 1000000;
    let totalGuests = 0;
  
    onMount(() => {
      const urlParams = new URLSearchParams(window.location.search);
      checkIn = urlParams.get('checkin') || '';
      checkOut = urlParams.get('checkout') || '';
      selectedMinPrice = Number(urlParams.get('price_min')) || 100000;
      selectedMaxPrice = Number(urlParams.get('price_max')) || 1000000;
      totalGuests = Number(urlParams.get('capacity')) || 0;
    });
  </script>
  
  <Header 
    state={true} 
    bind:checkIn={checkIn} 
    bind:checkOut={checkOut} 
    bind:selectedMinPrice={selectedMinPrice} 
    bind:selectedMaxPrice={selectedMaxPrice} 
    bind:totalGuests={totalGuests} 
  />
  