<script>
    import { format } from 'date-fns';
    import { DatePicker } from "@svelte-plugins/datepicker";
    import GuestsSelectPopup from "./GuestsSelectPopup.svelte";
    import RateRangePopup from "./RateRangePopup.svelte";

    // unused
    $: searchLocation = ''
    const onClickClearLocation = () => {
        searchLocation = ''
    }

    /* DatePicker 관련 */
    let dateFormat = 'M월 d일';
    let onDatePickerPopup = false;
    $: checkIn = ''
    $: checkOut = ''
    $: rateRange = [0, 0]
    const dowLabels = ["일", "월", "화", "수", "목", "금", "토"];
    const monthLabels = ["1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월"]

    const onClickClearCheckIn = () => {
        checkIn = ''
    }

    const onClickClearCheckOut = () => {
        checkOut = ''
    }

    const toggleDatePicker = () => {
        onDatePickerPopup = !onDatePickerPopup;
        console.log('toggle', onDatePickerPopup);
    }

    const formatDate = (dateString) => dateString && format(new Date(dateString), dateFormat) || '';

    let formattedCheckIn = '';
    let formattedCheckOut = '';

    $: formattedCheckIn = formatDate(checkIn);
    $: formattedCheckOut = formatDate(checkOut);

    /* 요금 범위 관련 */
    let onRatePopup = false;

    const onRatePopupClick = () => {
        onRatePopup = !onRatePopup
    }

    const onClickClearRate = () => {
        rateRange = [0, 0]
    }

    /* 여행자 수 선택 관련 */
    let onGuestsPopup = false;

    $: totalGuests = 0
    $: totalBabies = 0

    const onGuestsPopupClick = () => {
        onGuestsPopup = !onGuestsPopup
    }

    const onClickClearGuests = () => {
        totalGuests = 0
        totalBabies = 0
    }

    const handleUpdate = (e) => {
        totalGuests = e.detail.guestCount;
        totalBabies = e.detail.babyCount;
    }

    // 팝업창 닫기
    const closeRatePopup = () => {
        onRatePopup = false;
        console.log('onRatePopup', onRatePopup)
    }

    const closeGuestsPopup = () => {
        onGuestsPopup = false;
        console.log('onGuestsPopup', onGuestsPopup)
    }
</script>
<div class="flex gap-1 my-3 w-[860px] h-[82px] border border-gray-200 bg-white rounded-full mx-auto items-center animate-fade-down animate-duration-300" style="z-index: 98;" data-panel-bounds="true">

    <!--            <div class="flex gap-6 justify-between items-center py-5 pl-10 pr-4 rounded-full border hover:bg-gray-200">-->
    <!--                <label for="bigsearch-query-location-input" class="flex flex-col justify-center items-start">-->
    <!--                    <div class="text-[12px]">여행지</div>-->
    <!--                    <input id="bigsearch-query-location-input" type="text" name="query" bind:value={searchLocation} class="outline-none text-[14px]"-->
    <!--                           placeholder="여행지 검색" spellcheck="false" aria-autocomplete="none" autocomplete="off" autocorrect="false" />-->
    <!--                </label>-->
    <!--                <button type="button" aria-label="입력 내용 지우기" on:click={onClickClearLocation} class="clearBtn"-->
    <!--                        class:visible={searchLocation.trim() !== ''} class:invisible={searchLocation.trim() === ''}>-->
    <!--                    <span>-->
    <!--                        <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">-->
    <!--                            <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" fill="none"/>-->
    <!--                            <path d="M15 9L9 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>-->
    <!--                            <path d="M9 9L15 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>-->
    <!--                        </svg>-->
    <!--                    </span>-->
    <!--                </button>-->
    <!--            </div>-->
    <div class="flex gap-1 items-center relative">
        {#if onDatePickerPopup}
            <div class="absolute -bottom-[12px] animate-slidein">
                <DatePicker theme="custom-datepicker" isOpen={true} bind:startDate={checkIn} bind:endDate={checkOut} dowLabels={dowLabels} monthLabels={monthLabels} isRange isMultipane enableFutureDates enablePastDates={false} showYearControls={false}></DatePicker>
            </div>
        {/if}
        <div class="relative w-[150px] h-[81px] flex gap-6 justify-between items-center rounded-full py-5 pl-8 pr-5 whitespace-nowrap hover:bg-gray-200/60" role="button" tabindex="0" aria-expanded="true"
             on:click={toggleDatePicker}>
            <div type="button" class="flex flex-col">
                <div class="text-[12px]">
                    체크인
                </div>
                {#if checkIn}
                    <div class="text-[14px]">{formattedCheckIn}</div>
                {:else}
                    <div class="text-[14px] text-gray-400">날짜 추가</div>
                {/if}
            </div>
            <button type="button" aria-label="입력 내용 지우기" on:click={onClickClearCheckIn} class="clearBtn absolute right-3"
                    class:visible={checkIn} class:invisible={!checkIn}>
                        <span>
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" fill="none"/>
                                <path d="M15 9L9 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M9 9L15 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </span>
            </button>
        </div>
        <div class="relative w-[150px] h-[81px] flex gap-6 justify-between items-center rounded-full py-5 pl-8 pr-5 whitespace-nowrap hover:bg-gray-200/60" role="button" tabindex="0" aria-expanded="false"
             on:click={toggleDatePicker}>
            <div type="button" class="flex flex-col">
                <div class="text-[12px]">
                    체크아웃
                </div>
                {#if checkOut}
                    <div class="text-[14px]">{formattedCheckOut}</div>
                {:else}
                    <div class="text-[14px] text-gray-400">날짜 추가</div>
                {/if}
            </div>
            <button type="button" aria-label="입력 내용 지우기" on:click={onClickClearCheckOut} class="clearBtn absolute right-3"
                    class:visible={checkOut} class:invisible={!checkOut}>
                        <span>
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" fill="none"/>
                                <path d="M15 9L9 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M9 9L15 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </span>
            </button>
        </div>
    </div>

    <div class="h-16 border-l border-gray-200"></div>

    <div class="relative w-[220px] h-[81px] flex gap-6 justify-between items-center rounded-full py-5 pl-8 pr-5 whitespace-nowrap hover:bg-gray-200/60" role="button"
         on:click={onRatePopupClick}>
        <div class="flex flex-col">
            <div class="text-[12px]">
                요금
            </div>
            {#if rateRange[0] !== 0 || rateRange[0] !== 0}
                <div class="text-[14px]">{rateRange[0]} ~ {rateRange[1]}</div>
            {:else}
                <div class="text-[14px] text-gray-400">금액대 설정</div>
            {/if}
        </div>
        <button type="button" aria-label="입력 내용 지우기" on:click={onClickClearRate} class="clearBtn absolute right-3"
                class:visible={rateRange[0] !== 0 || rateRange[1] !== 0} class:invisible={rateRange[0] === 0 && rateRange[1] === 0}>
                        <span>
                            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                                <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" fill="none"/>
                                <path d="M15 9L9 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                                <path d="M9 9L15 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                            </svg>
                        </span>
        </button>
        {#if onRatePopup}
            <div class="overlay" on:click={(e) => {closeRatePopup(); e.stopPropagation();}}></div>
            <RateRangePopup bind:rateRange={rateRange} />
        {/if}
    </div>

    <div class="h-16 border-l border-gray-200"></div>

    <div class="relative min-w-[326px] max-w-[326px] h-[81px] flex gap-6 justify-between items-center rounded-full py-5 pl-8 pr-5 whitespace-nowrap hover:bg-gray-200/60" role="button"
         on:click={onGuestsPopupClick}>
        <div class="flex flex-col">
            <div class="text-[12px]">
                여행자
            </div>
            {#if totalBabies === 0 && totalGuests !== 0}
                <div class="text-[14px]">게스트 {totalGuests}명</div>
            {:else if totalBabies !== 0}
                <div class="text-[14px]">게스트 {totalGuests}명, 유아 {totalBabies}명</div>
            {:else}
                <div class="text-[14px] text-gray-400">게스트 추가</div>
            {/if}
        </div>
        <button type="button" aria-label="입력 내용 지우기" on:click={onClickClearGuests} class="clearBtn absolute right-[140px] z-[30]"
                class:visible={totalGuests !== 0} class:invisible={totalGuests === 0}>
            <span>
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M12 22C17.5228 22 22 17.5228 22 12C22 6.47715 17.5228 2 12 2C6.47715 2 2 6.47715 2 12C2 17.5228 6.47715 22 12 22Z" fill="none"/>
                    <path d="M15 9L9 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <path d="M9 9L15 15" stroke="#333333" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </span>
        </button>
        {#if onGuestsPopup}
            <div class="overlay" on:click={(e) => {closeGuestsPopup(); e.stopPropagation();}}></div>
            <GuestsSelectPopup on:update={handleUpdate} />
        {/if}
        <button type="button" class="absolute right-[10px] z-[20] flex gap-2 pl-3 pr-5 py-3 justify-between items-center rounded-full bg-primary hover:bg-pink-700/85 active:transition-all active:scale-[0.98] active:duration-75"
                on:click|stopPropagation>
            <span>
                <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path d="M11 19C15.4183 19 19 15.4183 19 11C19 6.58172 15.4183 3 11 3C6.58172 3 3 6.58172 3 11C3 15.4183 6.58172 19 11 19Z" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    <path d="M21 21L16.65 16.65" stroke="#ffffff" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                </svg>
            </span>
            <span class="text-white">
                검색
            </span>
        </button>
    </div>
</div>

<style>
    :global(.datepicker[data-picker-theme="custom-datepicker"]) {
        --datepicker-state-active: #333333;
        --datepicker-state-hover: #F5F5F7;

        /**
         * Calendar Range
         */
        --datepicker-calendar-range-background: var(--datepicker-state-hover);
        --datepicker-calendar-range-background-disabled: var(--datepicker-state-hover);
        --datepicker-calendar-range-border: 0;
        --datepicker-calendar-range-border-radius: 0;
        --datepicker-calendar-range-color: var(--datepicker-color);
        --datepicker-calendar-range-color-disabled: #ffc0b7;
        --datepicker-calendar-range-cursor: default;
        --datepicker-calendar-range-font-weight: var(--datepicker-font-weight-base);

        /**
         * Calendar Range Start & End
         */
        --datepicker-calendar-range-start-box-shadow: inset -20px 0 0 var(--datepicker-state-hover);
        --datepicker-calendar-range-end-box-shadow: inset 20px 0 0 var(--datepicker-state-hover);
        --datepicker-calendar-range-start-box-shadow-selected: inset -20px 0 0 #F5F5F7;
        --datepicker-calendar-range-end-box-shadow-selected: inset 20px 0 0 #F5F5F7;

        --datepicker-calendar-range-start-end-background: #333333;
        --datepicker-calendar-range-start-end-color: #F5F5F7;

        /**
         * Calendar Range Selected
         */
        --datepicker-calendar-range-selected-background: var(--datepicker-state-active);
        --datepicker-calendar-range-selected-border-radius: 20px;
        --datepicker-calendar-range-selected-color: #ffffff;
        --datepicker-calendar-range-selected-font-weight: var(--datepicker-font-weight-medium);

        --datepicker-calendar-range-selected-start-border-radius: 20px;

        /**
         * Calendar Day
         */
        --datepicker-calendar-day-align-items: center;
        --datepicker-calendar-day-background-hover: #f5f5f5;
        --datepicker-calendar-day-border: 1px solid transparent;
        --datepicker-calendar-day-border-radius: 100%;
        --datepicker-calendar-day-color: #232a32;
        --datepicker-calendar-day-color-disabled: #b9bdc1;
        --datepicker-calendar-day-color-hover: #232a32;
        --datepicker-calendar-day-cursor: pointer;
        --datepicker-calendar-day-cursor-disabled: default;
        --datepicker-calendar-day-display: flex;
        --datepicker-calendar-day-height: 40px;
        --datepicker-calendar-day-justify-content: center;
        --datepicker-calendar-day-font-family: var(--datepicker-font-family);
        --datepicker-calendar-day-font-size: var(--datepicker-font-size-base);
        --datepicker-calendar-day-margin-bottom: 1px;
        --datepicker-calendar-day-padding: var(--datepicker-padding-base);
        --datepicker-calendar-day-text-align: center;
        --datepicker-calendar-day-width: 40px;
        --datepicker-calendar-day-zindex-focus: 12;
    }
</style>