<script>
    import { DatePicker } from '@svelte-plugins/datepicker';
    import {createEventDispatcher, onDestroy, onMount} from 'svelte';
    import {filter} from "../../store/Filter.js";

    export let checkIn;
    export let checkOut;
    export let dowLabels;
    export let monthLabels;
    export let onClose;

    filter.subscribe(value => {
        checkIn = value.checkInDate;
        checkOut = value.checkOutDate;
    })

    $: checkIn;
    $: checkOut;

    const updateSchedule = () => {
        filter.updateCheckInCheckOut(checkIn, checkOut);
        console.log($filter.checkInDate, $filter.checkOutDate);
    };

    const handleClickOutside = (event) => {
        if (!event.target.closest('.popup')) {
            updateSchedule();
            onClose();
        }
    };

    onMount(() => {
        document.addEventListener('mousedown', handleClickOutside);
    });

    onDestroy(() => {
        document.removeEventListener('mousedown', handleClickOutside);
    });
</script>

<div class="absolute bottom-[-5px] left-[2rem] popup">
    <DatePicker
            theme="custom-datepicker"
            isOpen={true}
            bind:startDate={checkIn}
            bind:endDate={checkOut}
            {dowLabels}
            {monthLabels}
            isRange
            isMultipane
            enableFutureDates={true}
            enablePastDates={false}
            showYearControls={false}
            on:dateSelected={updateSchedule}
    />
</div>

<style>
    :global(.datepicker[data-picker-theme="custom-datepicker"]) {
        --datepicker-container-background: #fff;
        --datepicker-container-border: none;

        --datepicker-calendar-header-text-color: #000;
        --datepicker-calendar-dow-color: #000;
        --datepicker-calendar-day-color: #000;
        --datepicker-calendar-day-color-disabled: #ccc;
        --datepicker-calendar-range-selected-background: #e0e0e0;

        --datepicker-calendar-header-month-nav-background-hover: #e0e0e0;
        --datepicker-calendar-header-month-nav-icon-next-filter: none;
        --datepicker-calendar-header-month-nav-icon-prev-filter: none;
        --datepicker-calendar-header-year-nav-icon-next-filter: none;
        --datepicker-calendar-header-year-nav-icon-prev-filter: none;

        --datepicker-calendar-split-border: none;

        --datepicker-presets-border: none;
        --datepicker-presets-button-background-active: #000;
        --datepicker-presets-button-color: #000;
        --datepicker-presets-button-color-active: #fff;
        --datepicker-presets-button-color-hover: #333;
        --datepicker-presets-button-color-focus: #333;
    }
</style>
