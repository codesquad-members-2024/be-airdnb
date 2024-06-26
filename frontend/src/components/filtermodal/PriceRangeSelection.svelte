<script>
    import { onMount, onDestroy } from 'svelte';

    export let minPrice = 100000;
    export let maxPrice = 1000000;
    export let selectedMinPrice;
    export let selectedMaxPrice;
    export let onClose;

    const handleClickOutside = (event) => {
        if (!event.target.closest('#PriceRangeSlider')) {
            onClose(selectedMinPrice, selectedMaxPrice);
        }
    };

    const roundToNearestThousand = (value) => {
        return Math.round(value / 1000) * 1000;
    };

    const handleMinChange = (event) => {
        selectedMinPrice = roundToNearestThousand(Math.min(event.target.value, selectedMaxPrice - 1000));
    };

    const handleMaxChange = (event) => {
        selectedMaxPrice = roundToNearestThousand(Math.max(event.target.value, selectedMinPrice + 1000));
    };

    const handleApply = () => {
        onClose(selectedMinPrice, selectedMaxPrice);
    };

    onMount(() => {
        document.addEventListener('mousedown', handleClickOutside);
    });

    onDestroy(() => {
        document.removeEventListener('mousedown', handleClickOutside);
    });
</script>

<div id="PriceRangeSlider" class="absolute flex justify-center items-center text-black top-[4rem] shadow-2xl min-w-[400px] z-40">
    <div class="bg-white p-7 rounded-lg w-full">
        <div class="range-slider">
            <div class="text-lg font-bold mb-4">가격 범위</div>
            <div class="mb-6">평균 1박 요금은 ₩165,556원 입니다.</div>
            <label for="min-price">최소 요금: ₩{selectedMinPrice}</label>
            <label for="max-price">최대 요금: ₩{selectedMaxPrice}</label>
            <div class="slider">
                <input id="min-price"
                       type="range"
                       min={minPrice}
                       max={maxPrice}
                       bind:value={selectedMinPrice}
                       on:input={handleMinChange}
                       step="1000"/>
                <input id="max-price"
                       type="range"
                       min={minPrice}
                       max={maxPrice}
                       bind:value={selectedMaxPrice}
                       on:input={handleMaxChange}
                       step="1000"/>
            </div>
        </div>
        <button class="bg-[#FF5A5F] bg-opacity-80 text-white px-[20px] py-[10px] rounded-2xl font-semibold text-[16px]
        cursor-pointer border-none w-full hover:bg-opacity-90" on:click={handleApply}>적용하기</button>
    </div>
</div>

<style>
    label {
        color: black;
        display: block;
        margin-bottom: 8px;
    }
    .range-slider {
        margin-bottom: 16px;
    }
    .slider {
        position: relative;
        height: 40px;
    }
    input[type="range"] {
        -webkit-appearance: none;
        width: 100%;
        position: absolute;
        pointer-events: none;
        background: transparent;
    }
    input[type="range"]::-webkit-slider-runnable-track {
        width: 100%;
        height: 4px; /* 막대기 굵기 */
        cursor: pointer;
        background: #ddd; /* 막대기 색상 */
        border-radius: 2px;
    }
    input[type="range"]::-moz-range-track {
        width: 100%;
        height: 4px; /* 막대기 굵기 */
        cursor: pointer;
        background: #ddd; /* 막대기 색상 */
        border-radius: 2px;
    }
    input[type="range"]::-webkit-slider-thumb {
        -webkit-appearance: none;
        pointer-events: all;
        width: 30px;
        height: 30px;
        border-radius: 50%;
        background: url("$lib/assets/image/toggleBtn.svg") no-repeat;
        background-size: contain;
        cursor: pointer;
        position: relative;
        z-index: 2;
    }
    input[type="range"]#max-price::-webkit-slider-thumb {
        background: url("$lib/assets/image/toggleBtn.svg") no-repeat;
        background-size: contain;
    }
    input[type="range"]::-moz-range-thumb {
        pointer-events: all;
        width: 20px;
        height: 20px;
        border-radius: 50%;
        background: black;
        cursor: pointer;
        position: relative;
        z-index: 2;
    }
    input[type="range"]#max-price::-moz-range-thumb {
        background: black;
    }
</style>
