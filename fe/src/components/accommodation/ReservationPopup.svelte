<script>
  export let isPopupOpen = false;
  export let selectedItem = {};
  export let checkIn = '';
  export let checkOut = '';
  export let totalGuests = 0;
  export let length = 0;

  let API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
  if (API_BASE_URL.endsWith('/')) {
    API_BASE_URL = API_BASE_URL.slice(0, -1);
  }

  import { createEventDispatcher } from 'svelte';
  const dispatch = createEventDispatcher();

  function getAuthToken() {
    return localStorage.getItem('accessToken');
  }

  function closePopup() {
    dispatch('close');
  }

  function handleOverlayClick(event) {
    if (event.target === event.currentTarget) {
      closePopup();
    }
  }

  async function handleReservation() {
    const accommodationId = selectedItem.accommodationId;
    const capacity = totalGuests;
    const checkInDate = checkIn;
    const checkOutDate = checkOut;

    const authToken = getAuthToken();

    const requestOptions = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${authToken}`
      },
      body: JSON.stringify({
        accommodationId,
        checkInDate,
        checkOutDate,
        capacity
      })
    };

    try {
      const response = await fetch(`${API_BASE_URL}/reservation`, requestOptions);
      if (response.ok) {
        const data = await response.json();
        alert("예약이 완료되었습니다." + " 예약자: " + data.memberInformation.memberName + " 숙소 이름: " + data.accommodationInformation.accommodationName);
        closePopup(); // 예약이 완료되면 팝업 창을 닫음
        window.location.href ="/trips";
      }
      else {
        const errorData = await response.json();
        alert(errorData.errorMessage);
      }
    } catch (error) {
      console.error('Error making reservation:', error);
    }
  }
</script>

{#if isPopupOpen}
  <!-- svelte-ignore a11y-click-events-have-key-events -->
  <!-- svelte-ignore a11y-no-noninteractive-element-interactions -->
  <div
    class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50"
    role="dialog"
    aria-modal="true"
    tabindex="-1"
    on:click={handleOverlayClick}
  >
    <!-- svelte-ignore a11y-click-events-have-key-events -->
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
        <p class="mb-2">₩{selectedItem.fee ? selectedItem.fee.dayRate.toLocaleString() : "0"} x {length}박 = ₩{selectedItem.fee ? (selectedItem.fee.dayRate * length).toLocaleString() : "0"}</p>
        <p class="mb-2">청소비: ₩{selectedItem.fee ? selectedItem.fee.cleaningFee.toLocaleString() : "0"}</p>
        <p class="mb-2">서비스 수수료: ₩{selectedItem.fee ? ((selectedItem.fee.dayRate * length + selectedItem.fee.cleaningFee) * 0.03).toLocaleString() : "0"}</p>
      </div>
      <div class="mt-4 text-lg font-semibold">
        <p>총 합계: ₩{selectedItem.fee ? ((selectedItem.fee.dayRate * length) + selectedItem.fee.cleaningFee + ((selectedItem.fee.dayRate * length + selectedItem.fee.cleaningFee) * 0.03)).toLocaleString() : "0"}</p>
      </div>
      <button class="absolute top-2 right-2 p-2" on:click={closePopup} aria-label="Close popup">
        &times;
      </button>
    </div>
  </div>
{/if}
