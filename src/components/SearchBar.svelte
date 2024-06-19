<script>
  import { format } from 'date-fns';
  import DatePickerComponent from './DatePickerComponent.svelte';

  let dateFormat = 'M월 d일';
  let onDatePickerPopup = false;
  let checkIn = '';
  let checkOut = '';
  let dowLabels = ["일", "월", "화", "수", "목", "금", "토"];
  let monthLabels = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];

  const onClickClearCheckIn = () => {
    checkIn = '';
  };

  const onClickClearCheckOut = () => {
    checkOut = '';
  };

  const toggleDatePicker = () => {
    onDatePickerPopup = !onDatePickerPopup;
  };

  const formatDate = (dateString) => (dateString && format(new Date(dateString), dateFormat)) || '';

  let formattedCheckIn = '';
  let formattedCheckOut = '';

  $: formattedCheckIn = formatDate(checkIn);
  $: formattedCheckOut = formatDate(checkOut);

  const handleDateSelected = (e) => {
    const { startDate, endDate } = e.detail;
    checkIn = startDate;
    checkOut = endDate;
    toggleDatePicker();
  };

  const handleKeyDown = (event) => {
    if (event.key === 'Enter' || event.key === ' ') {
      toggleDatePicker();
    }
  };
</script>

<div class="fixed top-24 left-1/2 transform -translate-x-1/2 w-full max-w-4xl">
  <div class="bg-white shadow-md rounded-full flex items-center w-full">
    <button type="button" class="flex-grow px-6 py-4 border-r text-left" on:click={toggleDatePicker} on:keydown={handleKeyDown}>
      <div class="text-xs font-semibold text-gray-600">체크인</div>
      <div class="text-sm text-gray-800">{formattedCheckIn || '날짜 입력'}</div>
    </button>
    <button type="button" class="flex-grow px-6 py-4 border-r text-left" on:click={toggleDatePicker} on:keydown={handleKeyDown}>
      <div class="text-xs font-semibold text-gray-600">체크아웃</div>
      <div class="text-sm text-gray-800">{formattedCheckOut || '날짜 입력'}</div>
    </button>
    <div class="flex-grow px-6 py-4 border-r">
      <div class="text-xs font-semibold text-gray-600">요금</div>
      <div class="text-sm text-gray-800">금액대 설정</div>
    </div>
    <div class="flex-grow px-6 py-4 border-r">
      <div class="text-xs font-semibold text-gray-600">인원</div>
      <div class="text-sm text-gray-800">게스트 추가</div>
    </div>
    <div class="px-4 py-4">
      <button class="bg-red-500 text-white rounded-full p-2 focus:outline-none">
        <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
          <path fill-rule="evenodd" d="M12.9 14.32a8 8 0 111.414-1.414l4.294 4.294-1.414 1.414-4.294-4.294zM8 14a6 6 0 100-12 6 6 0 000 12z" clip-rule="evenodd"></path>
        </svg>
      </button>
    </div>
  </div>
</div>

<DatePickerComponent
  bind:checkIn={checkIn}
  bind:checkOut={checkOut}
  {dowLabels}
  {monthLabels}
  {onDatePickerPopup}
  on:dateSelected={handleDateSelected}
  on:toggle={toggleDatePicker}
/>

<style>
  .border-r {
    border-right: 1px solid #e5e7eb; /* Tailwind's gray-200 color */
  }
</style>
