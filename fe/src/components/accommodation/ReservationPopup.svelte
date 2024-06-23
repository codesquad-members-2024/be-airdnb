<script>
  // 필요한 속성들을 import
  export let isPopupOpen = false;
  export let selectedItem = {};
  export let checkIn = '';
  export let checkOut = '';
  export let totalGuests = 0;
  export let length = 0;

  import { createEventDispatcher } from 'svelte';
  const dispatch = createEventDispatcher();

  // 사용자 JWT 토큰 가져오기 (예시로 로컬 스토리지에서 가져오는 함수)
  function getAuthToken() {
    return localStorage.getItem('jwt'); // 로컬 스토리지에서 JWT 토큰을 가져옴
  }

  // 팝업을 닫는 함수
  function closePopup() {
    dispatch('close');
  }

  // 오버레이 클릭 시 팝업을 닫는 함수
  function handleOverlayClick(event) {
    if (event.target === event.currentTarget) {
      closePopup();
    }
  }

  // 예약하기 버튼 클릭 시 처리 함수
  async function handleReservation() {
    const accommodationId = selectedItem.accommodationId;
    const capacity = totalGuests;
    const checkInDate = checkIn; // checkIn을 LocalDate로 가정
    const checkOutDate = checkOut; // checkOut을 LocalDate로 가정

    // JWT 토큰 가져오기
    const authToken = getAuthToken();

    // HTTP 요청 설정
    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${authToken}` // JWT 토큰을 Authorization 헤더에 추가
      },
      body: JSON.stringify({
        accommodationId,
        checkInDate,
        checkOutDate,
        capacity
      })
    };

    try {
      const response = await fetch('http://localhost:8080/reservation', requestOptions);
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
      const data = await response.json();
      console.log('Reservation Success:', data);
      // 예약 성공 시 추가적인 처리 가능
    } catch (error) {
      console.error('Error making reservation:', error);
      // 예약 실패 시 처리
    }
  }

  // 키다운 이벤트 처리 함수
  function handleKeyDown(event) {
    if (event.key === 'Escape') {
      closePopup();
    }
  }
</script>

{#if isPopupOpen}
  <div
    class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50"
    role="dialog"
    aria-modal="true"
    tabindex="-1"
    on:click={handleOverlayClick}
    on:keydown={handleKeyDown}
  >
    <div class="bg-white p-6 rounded-lg shadow-lg max-w-lg w-full" role="document" on:click|stopPropagation>
      <div class="flex justify-between items-center mb-4">
        <div>
          <p class="text-2xl font-bold">{selectedItem.accommodationName}</p>
          <p class="py-2 text-sm">{selectedItem.address.city} {selectedItem.address.district} {selectedItem.address.neighborhood} {selectedItem.address.streetName} {selectedItem.address.detailedAddress}</p>
          <p class="text-xl font-semibold">{selectedItem.fee ? `₩${selectedItem.fee.dayRate.toLocaleString()}` : "₩0"} / 박</p>
        </div>
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
      <button class="w-full bg-black text-white px-4 py-2 rounded hover:bg-gray-800 mb-4" on:click={handleReservation}>예약하기</button>
      <div class="text-gray-600">
        <!-- 비용 정보 표시 -->
        <p class="mb-2">₩{selectedItem.fee ? selectedItem.fee.dayRate.toLocaleString() : "0"} x {length}박 = ₩{selectedItem.fee ? (selectedItem.fee.dayRate * length).toLocaleString() : "0"}</p>
        <p class="mb-2">청소비: ₩{selectedItem.fee ? selectedItem.fee.cleaningFee.toLocaleString() : "0"}</p>
        <p class="mb-2">서비스 수수료: ₩{selectedItem.fee ? ((selectedItem.fee.dayRate * totalGuests + selectedItem.fee.cleaningFee) * 0.03).toLocaleString() : "0"}</p>
      </div>
      <div class="mt-4 text-lg font-semibold">
        <!-- 총 합계 계산 -->
        <p>총 합계: ₩{selectedItem.fee ? ((selectedItem.fee.dayRate * length) + selectedItem.fee.cleaningFee + ((selectedItem.fee.dayRate * length + selectedItem.fee.cleaningFee) * 0.03)).toLocaleString() : "0"}</p>
      </div>
    </div>
  </div>
{/if}
