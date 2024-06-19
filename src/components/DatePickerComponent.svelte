<!-- DatePickerComponent.svelte -->
<script>
    import { DatePicker } from '@svelte-plugins/datepicker';
    import { createEventDispatcher } from 'svelte';
  
    export let checkIn;
    export let checkOut;
    export let dowLabels;
    export let monthLabels;
    export let isRange = true;
    export let isMultipane = true;
    export let enableFutureDates = true;
    export let enablePastDates = false;
    export let showYearControls = false;
    export let onDatePickerPopup = false;
  
    const dispatch = createEventDispatcher();
  
    const handleDateSelected = (e) => {
      const { startDate, endDate } = e.detail;
      dispatch('dateSelected', { startDate, endDate });
    };
  </script>
  
  {#if onDatePickerPopup}
        <DatePicker
          theme="custom-datepicker"
          isOpen={true}
          bind:startDate={checkIn}
          bind:endDate={checkOut}
          {dowLabels}
          {monthLabels}
          {isRange}
          {isMultipane}
          {enableFutureDates}
          {enablePastDates}
          {showYearControls}
          on:dateSelected={handleDateSelected}
        />
  {/if}
  