<script>
  import Header from "../components/Header.svelte";
  import MiniSearchBar from "../components/MiniSearchBar.svelte";
  import { onMount } from 'svelte';
  import MapComponent from "../components/accommodation/MapComponent.svelte";
  import ListComponent from "../components/accommodation/ListComponent.svelte"; // ListComponent 임포트

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

  // 리스트 항목 예제 데이터
  let items = [
    { id: 1, title: 'Spacious and Comfortable cozy house #4', price: '₩82,953 / 박', location: '서울특별시 영등포구 양평동', img: 'https://airdnb-storage.s3.ap-northeast-2.amazonaws.com/accommodations/thumbnail.png', rating: 4.8, reviews: 127 },
    { id: 2, title: '여의도 한강 공원이 보이는 멋진 숙소', price: '₩96,095 / 박', location: '서울특별시 영등포구 여의도동', img: 'https://airdnb-storage.s3.ap-northeast-2.amazonaws.com/accommodations/thumbnail.png', rating: 4.9, reviews: 201 },
    { id: 3, title: '여의도 한강 공원이 보이는 멋진 숙소', price: '₩96,095 / 박', location: '서울특별시 영등포구 여의도동', img: 'https://airdnb-storage.s3.ap-northeast-2.amazonaws.com/accommodations/thumbnail.png', rating: 4.9, reviews: 201 },
    // 더 많은 항목을 추가할 수 있습니다.
  ];
</script>

<Header 
  state={true} 
  bind:checkIn={checkIn} 
  bind:checkOut={checkOut} 
  bind:selectedMinPrice={selectedMinPrice} 
  bind:selectedMaxPrice={selectedMaxPrice} 
  bind:totalGuests={totalGuests} 
/>

<div class="flex h-screen pt-24"> <!-- 상단 패딩 추가 -->
  <div class="w-1/2 overflow-y-auto p-4">
    <ListComponent {items} />
  </div>
  <div class="w-1/2">
    <MapComponent />
  </div>
</div>
