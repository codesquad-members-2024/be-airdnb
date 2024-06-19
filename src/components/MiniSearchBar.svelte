<script>
  import { format } from 'date-fns';
  import DatePickerComponent from './DatePickerComponent.svelte';
  import RatePopup from './RatePopup.svelte';
  import GuestPopup from './GuestPopup.svelte';

  export let checkIn;
  export let checkOut;
  export let selectedMinPrice = 100000;
  export let selectedMaxPrice = 1000000;
  export let totalGuests = 0;

  let dateFormat = 'M월 d일';
  let onDatePickerPopup = false;
  let onRatePopup = false;
  let onGuestPopup = false;
  const dowLabels = ["일", "월", "화", "수", "목", "금", "토"];
  const monthLabels = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];

  const toggleDatePicker = () => {
    onDatePickerPopup = !onDatePickerPopup;
  };

  const toggleRatePopup = () => {
    onRatePopup = !onRatePopup;
  };

  const toggleGuestPopup = () => {
    onGuestPopup = !onGuestPopup;
  };

  const formatDate = (dateString) => (dateString && format(new Date(dateString), dateFormat)) || '';

  $: formattedCheckIn = formatDate(checkIn);
  $: formattedCheckOut = formatDate(checkOut);
  $: formattedDates = formattedCheckIn && formattedCheckOut ? `${formattedCheckIn} - ${formattedCheckOut}` : '일정 입력';

  const handleDateSelected = (e) => {
    const { startDate, endDate } = e.detail;
    checkIn = startDate;
    checkOut = endDate;
    toggleDatePicker();
  };

  const handleRateSelected = (min, max) => {
    selectedMinPrice = min;
    selectedMaxPrice = max;
    toggleRatePopup();
  };

  const handleGuestsSelected = (total) => {
    totalGuests = total;
    toggleGuestPopup();
  };
</script>

<div class="relative">
  <div class="bg-white shadow-md rounded-full flex items-center w-full max-w-lg p-2 border border-gray-300">
    <button type="button" class="flex-grow px-4 py-2 border-r text-left" on:click={toggleDatePicker}>
      <div class="text-sm text-gray-600">{formattedDates}</div>
    </button>
    <button type="button" class="flex-grow px-4 py-2 border-r text-left" on:click={toggleRatePopup}>
      <div class="text-sm text-gray-600">₩{selectedMinPrice.toLocaleString()} - ₩{selectedMaxPrice.toLocaleString()}</div>
    </button>
    <button type="button" class="flex-grow px-4 py-2 border-r text-left" on:click={toggleGuestPopup}>
      <div class="text-sm text-gray-600">게스트 {totalGuests}명</div>
    </button>
    <div class="px-4 py-2">
      <button class="bg-red-500 text-white rounded-full p-2 focus:outline-none">
        <svg class="w-5 h-5" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd" d="M12.9 14.32a8 8 0 111.414-1.414l4.294 4.294-1.414 1.414-4.294-4.294zM8 14a6 6 0 100-12 6 6 0 000 12z" clip-rule="evenodd"></path>
        </svg>
      </button>
    </div>
  </div>

  {#if onDatePickerPopup}
    <DatePickerComponent
      bind:checkIn={checkIn}
      bind:checkOut={checkOut}
      {dowLabels}
      {monthLabels}
      {onDatePickerPopup}
      on:dateSelected={handleDateSelected}
      on:toggle={toggleDatePicker}
    />
  {/if}

  {#if onRatePopup}
    <RatePopup bind:selectedMinPrice={selectedMinPrice} bind:selectedMaxPrice={selectedMaxPrice} onClose={handleRateSelected} />
  {/if}

  {#if onGuestPopup}
    <GuestPopup bind:total={totalGuests} onClose={handleGuestsSelected} />
  {/if}
</div>

<style>
  .border-r {
    border-right: 1px solid #e5e7eb; /* Tailwind's gray-300 color */
  }
</style>
