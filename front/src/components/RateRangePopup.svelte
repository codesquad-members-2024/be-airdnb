<script>
    import { createEventDispatcher, onDestroy, onMount } from "svelte";
    import RangeSlider from "svelte-range-slider-pips"
    export let rateRange

    const currency = new Intl.NumberFormat("ko", {
        style: 'currency',
        currency: 'KRW',
    });
    const formatter = (value) => currency.format(value);

    //TODO: 필터 스토어로 연결 필요
    $: sliderRange = [10000, 1000000]
    $: if (sliderRange[0] > sliderRange[1]) {
        sliderRange[1] = sliderRange[0]
    }

    function updateSliderRange(index, e) {
        console.log(e.target)
        console.log(e.target.value)

        const parsedValue = parseInt(e.target.value);
        console.log('parsedValue', parsedValue)

        if (!isNaN(parsedValue)) {
            sliderRange[index] = parsedValue
            console.log('range', sliderRange)
        }
    }

    let popupRef;

    onMount(() => {
        document.addEventListener('click', handleClickOutside, true);
    });

    onDestroy(() => {
        document.removeEventListener('click', handleClickOutside, true);
    });

    function handleClickOutside(event) {
        if (popupRef && !popupRef.contains(event.target)) {
            console.log(popupRef)
            dispatch('onRatePopupClick');
        }
    }
</script>

<div class="absolute z-[98] top-[94px] right-[5px] w-[400px] h-[355px] px-[2.8rem] py-[1.5rem] bg-white drop-shadow-md rounded-3xl animate-slidein whitespace-nowrap"
     role="menu" on:click|stopPropagation>
    <div class="flex flex-col space-y-2">
        <div class="font-bold py-6">
            <span>가격 범위</span>
        </div>
        <div class="flex flex-col gap-3">
            <div class="flex gap-4 justify-between items-center">
                <div class="filter-container w-[160px] h-[55px] px-2 py-2 rounded-xl border border-gray-400">
                    <label class="relative" for="price_filter_min">
                        <div class="text-gray-600/80 text-[13px]">
                            최저
                        </div>
                        <div class="absolute w-full">
                            <div class="inline-flex pr-1">
                                <span aria-hidden="true">₩</span>
                            </div>
                            <input id="price_filter_min" aria-label="₩" type="text" autocomplete="off" bind:value={sliderRange[0]}
                                   class="w-[90px] focus:outline-none" on:input={(e) => updateSliderRange(0, e)} />
                        </div>
                    </label>
                </div>

                <div>
                    <span>
                        <i class="bi bi-dash-lg"></i>
                    </span>
                </div>

                <div class="filter-container w-[160px] h-[55px] px-2 py-2 rounded-xl border border-gray-400">
                    <label class="relative" for="price_filter_max">
                        <div class="text-gray-600/80 text-[13px]">
                            최고
                        </div>
                        <div class="absolute">
                            <div class="inline-flex pr-1">
                                <span aria-hidden="true">₩</span>
                            </div>
                            <input id="price_filter_max" aria-label="₩" type="text" autocomplete="off" bind:value={sliderRange[1]}
                                   class="w-[90px] focus:outline-none" on:input={(e) => updateSliderRange(1, e)}
                            />
                        </div>
                    </label>
                </div>
            </div>
            <div class="text-[15px] text-gray-400 ml-0.5">
                <span>
                    평균 1박 요금은 ₩165,556 입니다.
                </span>
            </div>
        </div>
        <div class="pt-6">
            <RangeSlider {formatter} id="sliderInRange" range suffix="원" min={0} max={1000000} pushy pips first="label" last="label" bind:values={sliderRange} />
        </div>
    </div>
</div>

<style>
    :root {
        --range-slider:            hsl(180, 3.9%, 84.9%);
        --range-handle-inactive: hsl(180, 4%, 41%);
        --range-handle:            hsl(180, 4%, 14%);
        --range-handle-focus:      hsl(180, 4%, 14%);
        --range-handle-border:     hsl(352, 77%, 50%);
        --range-range-inactive:    hsl(180, 4.6%, 61.8%);
        --range-range:             hsl(180, 4%, 14%);
        --range-float-inactive:     hsl(180, 4.6%, 61.8%);
        --range-float:              hsl(244.1, 63.2%, 54.1%);
        --range-float-text:         hsl(0, 0%, 100%);

        --range-pip:               hsl(210, 14.3%, 53.3%);
        --range-pip-text:          hsl(210, 14.3%, 53.3%);
        --range-pip-active:        hsl(180, 4%, 14%);
        --range-pip-active-text:   hsl(180, 4%, 14%);
        --range-pip-hover:         hsl(180, 4%, 14%);
        --range-pip-hover-text:    hsl(180, 4%, 14%);
        --range-pip-in-range:      hsl(180, 4%, 14%);
        --range-pip-in-range-text: hsl(180, 4%, 14%);
    }
</style>