<script>
    export let reservations;
    export let confirmReservation;
    export let openModal;
  </script>
  
  <div class="mb-8">
    <h2 class="text-2xl font-bold mb-4">예정 여행지</h2>
    <div class="flex flex-wrap -mx-2">
      {#each $reservations as reservation (reservation.reservationId)}
        {#if reservation.deleted === false && reservation.isConfirmed === false}
          <div class="w-full lg:w-1/2 px-2 mb-4">
            <div class="flex items-center bg-white rounded-lg shadow-lg p-4 relative">
              <img src="{reservation.accommodationImages}" alt="{reservation.accommodationName}" class="w-16 h-16 rounded-lg mr-4">
              <div class="flex-1">
                <h3 class="text-xl font-bold">{reservation.accommodationName}, {reservation.city}</h3>
                <p class="text-gray-600">호스트: {reservation.memberId}</p>
                <p class="text-gray-600">{reservation.checkInDate} – {reservation.checkOutDate}</p>
              </div>
              <div class="flex space-x-2 absolute bottom-4 right-4">
                <button class="bg-blue-500 text-white text-sm px-2 py-2 rounded-md shadow-md" on:click={() => confirmReservation(reservation.reservationId)}>확정</button>
                <button class="bg-red-500 text-white text-sm px-2 py-2 rounded-md shadow-md" on:click={() => openModal(reservation.reservationId)}>취소</button>
              </div>
            </div>
          </div>
        {/if}
      {/each}
    </div>
    {#if $reservations.filter(r => r.deleted === false && r.isConfirmed === false).length === 0}
      <p class="text-gray-600">예정된 여행이 없습니다.</p>
    {/if}
  </div>
  