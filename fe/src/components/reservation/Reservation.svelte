<script>
    import { onMount } from "svelte";
  
    let API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
    if (API_BASE_URL.endsWith('/')) {
      API_BASE_URL = API_BASE_URL.slice(0, -1);
    }
  
    let reservations = [];
  
    onMount(async () => {
      const accessToken = localStorage.getItem("accessToken");
      const response = await fetch(`${API_BASE_URL}/reservation/member`, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
          "Authorization": `Bearer ${accessToken}`
        }
      });
      reservations = await response.json();
    });
  
    function confirmReservation(reservationId) {
      // 예약 확정 API 호출 함수
    }
  
    function cancelReservation(reservationId) {
      // 예약 취소 API 호출 함수
    }
  </script>
  
  <div class="pt-28 container mx-auto">
    <h1 class="text-3xl font-bold my-8">여행</h1>
  
    <div class="flex flex-col lg:flex-row items-center w-full bg-white rounded-lg shadow-lg overflow-hidden mb-8">
      <!-- 왼쪽 섹션 -->
      <div class="flex flex-col items-start p-8 lg:w-1/3" id="reservation-message">
        {#if reservations.length > 0}
          <h1 class="text-2xl font-bold mb-2 text-green-500">다음 여행이 예약되었습니다!</h1>
          <p class="text-gray-600 mb-4">여행 계획을 세우고 기대해보세요.</p>
        {:else}
          <h1 class="text-2xl font-bold mb-2">아직 예약된 여행이 없습니다!</h1>
          <p class="text-gray-600 mb-4">여행 가방에 쌓인 먼지를 털어내고 다음 여행 계획을 세워보세요.</p>
        {/if}
      </div>
  
      <!-- 오른쪽 섹션 -->
      <div class="lg:w-2/3 h-72">
        <img src="/assets/family.webp" alt="여행 이미지" class="w-full h-full object-cover">
      </div>
    </div>


    <!-- 확정된 여행 섹션 -->
    <div class="mb-8">
        <h2 class="text-2xl font-bold mb-4">확정된 여행</h2>
        <div class="flex flex-wrap -mx-2">
          {#each reservations as reservation (reservation.reservationId)}
            {#if reservation.deleted === false && reservation.isConfirmed === true}
              <div class="w-full lg:w-1/2 px-2 mb-4">
                <div class="flex items-center bg-white rounded-lg shadow-lg p-4 relative">
                  <img src="{reservation.accommodationImages}" alt="{reservation.accommodationName}" class="w-16 h-16 rounded-lg mr-4">
                  <div class="flex-1">
                    <h3 class="text-xl font-bold">{reservation.accommodationName}, {reservation.city}</h3>
                    <p class="text-gray-600">호스트: {reservation.accommodationDescription}</p>
                    <p class="text-gray-600">{reservation.checkInDate} – {reservation.checkOutDate}</p>
                  </div>
                  <div class="absolute bottom-4 right-4">
                    <button class="bg-green-500 text-white text-sm px-2 py-2 rounded-md shadow-md">확정됨</button>
                  </div>
                </div>
              </div>
            {/if}
          {/each}
        </div>
        {#if reservations.filter(r => r.deleted === false && r.isConfirmed === true).length === 0}
          <p class="text-gray-600">확정된 여행이 없습니다.</p>
        {/if}
      </div>
  
    <!-- 예정 여행지 섹션 -->
    <div class="mb-8">
      <h2 class="text-2xl font-bold mb-4">예정 여행지</h2>
      <div class="flex flex-wrap -mx-2">
        {#each reservations as reservation (reservation.reservationId)}
          {#if reservation.deleted === false && reservation.isConfirmed === false}
            <div class="w-full lg:w-1/2 px-2 mb-4">
              <div class="flex items-center bg-white rounded-lg shadow-lg p-4 relative">
                <img src="{reservation.accommodationImages}" alt="{reservation.accommodationName}" class="w-16 h-16 rounded-lg mr-4">
                <div class="flex-1">
                  <h3 class="text-xl font-bold">{reservation.accommodationName}, {reservation.city}</h3>
                  <p class="text-gray-600">호스트: {reservation.accommodationDescription}</p>
                  <p class="text-gray-600">{reservation.checkInDate} – {reservation.checkOutDate}</p>
                </div>
                <div class="flex space-x-2 absolute bottom-4 right-4">
                  <button class="bg-blue-500 text-white text-sm px-2 py-2 rounded-md shadow-md" on:click={() => confirmReservation(reservation.reservationId)}>확정</button>
                  <button class="bg-red-500 text-white text-sm px-2 py-2 rounded-md shadow-md" on:click={() => cancelReservation(reservation.reservationId)}>취소</button>
                </div>
              </div>
            </div>
          {/if}
        {/each}
      </div>
      {#if reservations.filter(r => r.deleted === false && r.isConfirmed === false).length === 0}
        <p class="text-gray-600">예정된 여행이 없습니다.</p>
      {/if}
    </div>
  
  
    <!-- 취소된 여행 섹션 -->
    <div>
      <h2 class="text-2xl font-bold mb-4">취소된 여행</h2>
      <div class="flex flex-wrap -mx-2">
        {#each reservations as reservation (reservation.reservationId)}
          {#if reservation.deleted === true && reservation.isConfirmed === true}
            <div class="w-full lg:w-1/2 px-2 mb-4">
              <div class="flex items-center bg-white rounded-lg shadow-lg p-4 relative">
                <img src="{reservation.accommodationImages}" alt="{reservation.accommodationName}" class="w-16 h-16 rounded-lg mr-4">
                <div class="flex-1">
                  <h3 class="text-xl font-bold">{reservation.accommodationName}, {reservation.city}</h3>
                  <p class="text-gray-600">호스트: {reservation.accommodationDescription}</p>
                  <p class="text-gray-600">{reservation.checkInDate} – {reservation.checkOutDate}</p>
                  <button class="bg-red-500 text-white text-sm px-2 py-2 rounded-md shadow-md absolute bottom-4 right-4">취소됨</button>
                </div>
              </div>
            </div>
          {/if}
        {/each}
      </div>
      {#if reservations.filter(r => r.deleted === true && r.isConfirmed === true).length === 0}
        <p class="text-gray-600 pb-12">취소된 여행이 없습니다.</p>
      {/if}
    </div>
  </div>
  
  <style>
    /* Tailwind CSS 기본 설정을 사용하므로 별도 스타일 정의는 최소화 */
    .relative .flex.space-x-2 {
      display: flex;
      gap: 0.5rem;
    }
  </style>
  