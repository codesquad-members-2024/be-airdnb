<script>
    export let isPopupOpen = false;
    export let selectedItem = {};
    export let checkIn = '';
    export let checkOut = '';
    export let totalGuests = 0;
  
    import { createEventDispatcher } from 'svelte';
    const dispatch = createEventDispatcher();
  
    function closePopup() {
      dispatch('close');
    }
  </script>
  
  {#if isPopupOpen}
    <div class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
      <div class="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full">
        <div class="flex justify-between items-center mb-4">
          <div>
            <p class="text-xl font-semibold">₩{selectedItem.price || "₩0"} / 박</p>
            <p class="text-sm text-gray-500">{selectedItem.reviews || "0"} 후기</p>
          </div>
          <button on:click={closePopup} class="text-gray-600 hover:text-gray-900" aria-label="Close popup">
            <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 24 24">
              <path d="M6.225 4.811a1.001 1.001 0 011.413 0L12 9.172l4.362-4.361a1.001 1.001 0 011.414 1.414L13.414 10.586l4.362 4.361a1.001 1.001 0 01-1.414 1.414L12 12l-4.362 4.361a1.001 1.001 0 01-1.414-1.414L10.586 12 6.225 7.639a1.001 1.001 0 010-1.414z" />
            </svg>
          </button>
        </div>
        <div class="mb-4 border rounded-lg">
          <div class="grid grid-cols-2 gap-0 border-b p-4">
            <div class="border-r pr-4">
              <label for="checkin" class="block text-gray-700 font-bold">체크인</label>
              <p id="checkin" class="text-lg">{checkIn || "N/A"}</p>
            </div>
            <div class="pl-4">
              <label for="checkout" class="block text-gray-700 font-bold">체크아웃</label>
              <p id="checkout" class="text-lg">{checkOut || "N/A"}</p>
            </div>
          </div>
          <div class="p-4">
            <label for="guests" class="block text-gray-700 font-bold">인원</label>
            <p id="guests" class="text-lg">{totalGuests || "N/A"}명</p>
          </div>
        </div>
        <button class="w-full bg-black text-white px-4 py-2 rounded hover:bg-gray-800 mb-4">예약하기</button>
        <div class="text-gray-600">
          <p class="mb-2">₩71,466 x 18박 = ₩1,322,396</p>
          <p class="mb-2">청소비: ₩25,996</p>
          <p class="mb-2">서비스 수수료: ₩182,468</p>
          <p class="mb-2">속박세와 수수료: ₩18,247</p>
        </div>
        <div class="mt-4 text-lg font-semibold">
          <p>총 합계: ₩1,488,195</p>
        </div>
      </div>
    </div>
  {/if}
  