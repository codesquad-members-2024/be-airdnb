<script>
    import { onMount, onDestroy } from 'svelte';
    import {filter} from "../../store/Filter.js";

    export let total = 0;
    export let onClose;

    let adults = 1;
    let children = 0;
    filter.subscribe(value => {
        total = value.guestCount;
    })

    let infants = 0;

    $: total;

    const updateTotal = () => {
        total = adults + children + infants;
    };

    const increment = (type) => {
        if (type === 'adults') adults++;
        if (type === 'children') children++;
        if (type === 'infants') infants++;
        updateTotal();
    };

    const decrement = (type) => {
        if (type === 'adults' && adults > 1) adults--; // Ensure adults do not go below 1
        if (type === 'children' && children > 0) children--;
        if (type === 'infants' && infants > 0) infants--;
        updateTotal();
    };

    const handleClickOutside = (event) => {
        if (!event.target.closest('.popup')) {
            updateTotal();
            onClose(total);
        }
    };

    onMount(() => {
        document.addEventListener('mousedown', handleClickOutside);
    });

    onDestroy(() => {
        document.removeEventListener('mousedown', handleClickOutside);
    });
</script>

<div class="absolute right-0 z-[50] top-[4rem] w-[350px] bg-white shadow-2xl rounded-xl" role="menu">
    <div class="popup bg-white p-6 rounded-lg shadow-lg w-full">
        <div class="space-y-3">
            <div class="flex justify-between items-center">
                <div>
                    <div class="font-bold text-gray-800">성인</div>
                    <div class="text-gray-500 text-sm">만 13세 이상</div>
                </div>
                <div class="flex items-center space-x-2">
                    <button class="text-gray-600 bg-[#FF5A5F] bg-opacity-50 hover:bg-opacity-80 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => decrement('adults')}>-</button>
                    <div>{adults}</div>
                    <button class="text-gray-600 bg-[#FF5A5F] bg-opacity-50 hover:bg-opacity-80 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => increment('adults')}>+</button>
                </div>
            </div>

            <div class="border-b border-b-gray-200" />

            <div class="flex justify-between items-center">
                <div>
                    <div class="font-bold text-gray-800">어린이</div>
                    <div class="text-gray-500 text-sm">만 2-12세</div>
                </div>
                <div class="flex items-center space-x-2">
                    <button class="text-gray-600 bg-[#FF5A5F] bg-opacity-50 hover:bg-opacity-80 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => decrement('children')}>-</button>
                    <div>{children}</div>
                    <button class="text-gray-600 bg-[#FF5A5F] bg-opacity-50 hover:bg-opacity-80 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => increment('children')}>+</button>
                </div>
            </div>

            <div class="border-b border-b-gray-200" />

            <div class="flex justify-between items-center">
                <div>
                    <div class="font-bold text-gray-800">유아</div>
                    <div class="text-gray-500 text-sm">만 2세 미만</div>
                </div>
                <div class="flex items-center space-x-2">
                    <button class="text-gray-600 bg-[#FF5A5F] bg-opacity-50 hover:bg-opacity-80 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => decrement('infants')}>-</button>
                    <div>{infants}</div>
                    <button class="text-gray-600 bg-[#FF5A5F] bg-opacity-50 hover:bg-opacity-80 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => increment('infants')}>+</button>
                </div>
            </div>
        </div>
    </div>
</div>

<style>
    .popup {
        z-index: 50; /* Ensure the popup is above other elements */
    }
    button {
        cursor: pointer;
    }
</style>