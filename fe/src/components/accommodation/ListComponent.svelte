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
    <div
      class="flex border border-gray-200 rounded-lg overflow-hidden bg-white shadow-lg transform transition-transform duration-200 hover:-translate-y-1"
      role="button"
      tabindex="0"
      on:click={() => openPopup(item)}
      on:keydown={(event) => handleKeyDown(event, item)}
    >
      <img src={item.img} alt={item.title} class="w-1/3 object-cover" />
      <div class="p-4 flex flex-col justify-between w-2/3">
        <div class="flex justify-between items-center mb-2">
          <h3 class="text-xl font-semibold">{item.title}</h3>
          <svg class="w-6 h-6 text-red-500" fill="currentColor" viewBox="0 0 24 24">
            <path d="M12 21.35l-1.45-1.32C5.4 15.36 2 12.28 2 8.5 2 5.42 4.42 3 7.5 3c1.74 0 3.41.81 4.5 2.09C13.09 3.81 14.76 3 16.5 3 19.58 3 22 5.42 22 8.5c0 3.78-3.4 6.86-8.55 11.54L12 21.35z"/>
          </svg>
        </div>
        <p class="text-gray-600">{item.location}</p>
        <div class="flex justify-between items-center mt-4">
          <div class="flex items-center">
            <svg class="w-5 h-5 text-yellow-400" fill="currentColor" viewBox="0 0 20 20">
              <path d="M9.049 2.927C9.327 2.164 10.673 2.164 10.951 2.927L12.262 6.105L15.679 6.441C16.51 6.526 16.837 7.536 16.23 8.105L13.781 10.272L14.412 13.631C14.564 14.454 13.654 15.066 12.901 14.7L9.999 13.185L7.098 14.7C6.345 15.066 5.435 14.454 5.587 13.631L6.218 10.272L3.769 8.105C3.162 7.536 3.489 6.526 4.32 6.441L7.737 6.105L9.049 2.927Z"/>
            </svg>
            <span class="ml-1 text-gray-800">{item.rating} ({item.reviews})</span>
          </div>
          <p class="text-lg font-semibold">{item.price}</p>
        </div>
      </div>
    </div>
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
