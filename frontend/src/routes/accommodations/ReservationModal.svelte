<script>
    import {differenceInDays, parseISO} from "date-fns";
    import {reservation} from "../../store/AccoProducts.js";
    import { createEventDispatcher } from 'svelte';
    import {filter} from "../../store/Filter.js";

    export let isReservationModalOpened;
    export let accoId = '';
    export let title = '';
    export let checkIn = '';
    export let checkOut = '';
    export let totalGuests = 0;
    export let totalPrice;

    const dispatch = createEventDispatcher();

    function closePopup() {
        isReservationModalOpened = false;
        dispatch('close');
    }

    function handleOverlayClick(event) {
        if (event.target === event.currentTarget) {
            closePopup();
        }
    }

    let stayLength = differenceInDays(parseISO($filter.checkOutDate), parseISO($filter.checkInDate));
    let pricePerNight = Math.floor(totalPrice / stayLength);
</script>

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
                <p class="text-2xl font-bold">{title}</p>
                <p class="text-xl font-semibold">{pricePerNight} / 박</p>
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
        <button class="w-full bg-black text-white px-4 py-2 rounded hover:bg-gray-800 mb-4" on:click={reservation.handleReservation}>예약하기</button>
        <div class="mt-4 text-lg font-semibold">
            <p>총 합계: ₩{totalPrice}</p>
        </div>
        <button class="absolute top-2 right-2 p-2" on:click={closePopup} aria-label="Close popup">
            &times;
        </button>
    </div>
</div>
