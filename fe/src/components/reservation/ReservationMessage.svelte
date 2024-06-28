<script>
    export let reservations;
    export let hasNonDeletedReservations;
  </script>
  
  <div class="flex flex-col lg:flex-row items-center w-full bg-white rounded-lg shadow-lg overflow-hidden mb-8">
    <!-- 왼쪽 섹션 -->
    <div class="flex flex-col items-start p-8 lg:w-1/3" id="reservation-message">
      {#if $reservations.length > 0 && hasNonDeletedReservations()}
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
  