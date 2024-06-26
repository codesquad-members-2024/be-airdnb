<script>
    export let closeModal;
    export let cancelReservation;
  </script>
  
  <div class="fixed inset-0 flex items-center justify-center bg-gray-900 bg-opacity-50">
    <div class="bg-white rounded-lg shadow-lg p-8 max-w-sm w-full">
      <h2 class="text-xl font-bold mb-4">예약 취소</h2>
      <p class="text-sm text-gray-600 mb-4">예약을 취소하면 되돌릴 수 없습니다.</p>
      <p class="text-sm text-gray-600 mb-4">취소하시겠습니까?</p>
      <div class="flex justify-end space-x-2">
        <button class="bg-gray-300 hover:bg-gray-400 text-black px-4 py-2 rounded-md shadow-md" on:click={closeModal}>아니오</button>
        <button class="bg-red-500 hover:bg-red-600 text-white px-4 py-2 rounded-md shadow-md" on:click={cancelReservation}>예</button>
      </div>
    </div>
  </div>
  