<script>
    import { format, addDays, differenceInDays } from 'date-fns'; // addDays 추가
    import GuestCountSelection from "./GuestCountSelection.svelte";
    import PriceRangeSelection from "./PriceRangeSelection.svelte";
    import ScheduleSelection from "./ScheduleSelection.svelte";
    import filterBtn from "$lib/assets/image/filterBtnSmall.svg"
    import { onMount } from 'svelte';
    import {filter} from "../../store/Filter.js";
    import {accoProducts} from "../../store/AccoProducts.js";

    let checkIn;
    let checkOut;
    let selectedMinPrice = 100000;
    let selectedMaxPrice = 1000000;
    let totalGuests;
    let latitude = 0;
    let longitude = 0;
    let state = false;

    filter.subscribe(value => {
        checkIn = value.checkInDate;
        checkOut = value.checkOutDate;
        totalGuests = value.guestCount;
        latitude = value.latitude;
        longitude = value.longitude;
    })

    $: totalGuests;

    let dateFormat = 'M월 d일';
    let onScheduleSelection = false;
    let onPriceRangeSelection = false;
    let onGuestCountSelection = false;
    const dowLabels = ["일", "월", "화", "수", "목", "금", "토"];
    const monthLabels = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"];

    const toggleScheduleSelection = () => {
        onScheduleSelection = !onScheduleSelection;
    };

    const togglePriceRangeSelection = () => {
        onPriceRangeSelection = !onPriceRangeSelection;
    };

    const toggleGuestCountSelection = () => {
        onGuestCountSelection = !onGuestCountSelection;
    };

    const formatDate = (dateString) => (dateString && format(new Date(dateString), dateFormat)) || '';

    $: formattedCheckIn = formatDate(checkIn);
    $: formattedCheckOut = formatDate(checkOut);
    $: formattedDates = state ? '언제든지' : (formattedCheckIn && formattedCheckOut ? `${formattedCheckIn} - ${formattedCheckOut}` : '일정 입력');

    const handleDateSelected = () => {
        toggleScheduleSelection();
    };

    const handleRateSelected = (min, max) => {
        selectedMinPrice = min;
        selectedMaxPrice = max;
        togglePriceRangeSelection();
    };

    const handleGuestsSelected = () => {
        toggleGuestCountSelection();
    };

    const handleSearch = () => {
        filter.updateCheckInCheckOut(checkIn, checkOut);
        filter.updatePeopleCount(totalGuests);
        const queryString = filter.generateFilterQueryString();
        accoProducts.fetchAccoProducts(queryString);
    };
</script>

<div class="relative flex justify-center min-w-[700px]">
    <div class="bg-white shadow-md rounded-full flex items-center w-full max-w-lg p-2 border border-gray-300">
        <button type="button" class="flex-grow px-4 py-2 border-r text-left" on:click={toggleScheduleSelection}>
            <div class="text-sm text-gray-600">{formattedDates}</div>
        </button>
        <button type="button" class="flex-grow px-4 py-2 border-r text-left" on:click={togglePriceRangeSelection}>
            <div class="text-sm text-gray-600">₩{selectedMinPrice.toLocaleString()} - ₩{selectedMaxPrice.toLocaleString()}</div>
        </button>
        <div class="flex">
            <button type="button" class="flex-grow px-4 py-2 text-left" on:click={toggleGuestCountSelection}>
                <div class="text-sm text-gray-600">게스트 {totalGuests}명</div>
            </button>
            <button class="p-2 focus:outline-none" on:click={handleSearch}>
                <img src="{filterBtn}" alt="filter button" class="w-6 h-6">
            </button>
        </div>
    </div>

    {#if onScheduleSelection}
        <ScheduleSelection
                bind:checkIn={checkIn}
                bind:checkOut={checkOut}
                {dowLabels}
                {monthLabels}
                on:dateSelected={handleDateSelected}
                onClose={toggleScheduleSelection}
        />
    {/if}

    {#if onPriceRangeSelection}
        <PriceRangeSelection bind:selectedMinPrice={selectedMinPrice}
                             bind:selectedMaxPrice={selectedMaxPrice}
                             onClose={handleRateSelected} />
    {/if}

    {#if onGuestCountSelection}
        <GuestCountSelection bind:total={totalGuests}
                             onClose={handleGuestsSelected} />
    {/if}
</div>

<style>
    .border-r {
        border-right: 1px solid #e5e7eb; /* Tailwind's gray-300 color */
    }
</style>