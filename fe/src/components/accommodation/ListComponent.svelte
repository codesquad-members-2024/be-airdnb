<script>
  import ReservationPopup from './ReservationPopup.svelte';
  export let items = [];
  export let checkIn = '';
  export let checkOut = '';
  export let totalGuests = 0;
  let selectedItem = null;
  let isPopupOpen = false;

  function openPopup(item) {
    selectedItem = item;
    isPopupOpen = true;
  }

  function closePopup() {
    isPopupOpen = false;
  }

  const handleKeyDown = (event, item) => {
    if (event.key === 'Enter' || event.key === ' ') {
      openPopup(item);
    }
  };
</script>

<div class="flex flex-col gap-4">
  {#each items as item}
    <button
      class="flex border border-gray-200 rounded-lg overflow-hidden bg-white shadow-lg transform transition-transform duration-200 hover:-translate-y-1 text-left"
      on:click={() => openPopup(item)}
      on:keydown={(event) => handleKeyDown(event, item)}
    >
      <div class="w-1/3 h-48 overflow-hidden">
        <img src={item.accommodationImage} alt={item.accommodationName} class="object-cover w-full h-full" />
      </div>
      <div class="p-4 flex flex-col justify-between w-2/3">
        <div class="mb-2">
          <p class="text-sm text-gray-500">{item.address.city} {item.address.district} {item.address.neighborhood} {item.address.streetName} {item.address.detailedAddress}</p>
          <h1 class="text-xl font-bold my-2">{item.accommodationName}</h1>
          <p class="text-sm text-gray-600">최대 인원 {item.maxCapacity}명 • 침실 {item.roomInfo.bedroomCount}개 • 침대 {item.roomInfo.bedCount}개 • 욕실 {item.roomInfo.bathroomCount}개</p>
        </div>
        <div class="flex justify-end items-center mt-4">
          <p class="text-lg font-semibold">₩{item.fee.dayRate.toLocaleString()} / 박</p>
        </div>
      </div>
    </button>
  {/each}
</div>

<ReservationPopup
  {isPopupOpen}
  {selectedItem}
  {checkIn}
  {checkOut}
  {totalGuests}
  on:close={closePopup}
/>

<style>
  img {
    display: block;
  }
</style>
