<script>
    import { createEventDispatcher } from "svelte";

    const dispatch = createEventDispatcher();

    $: adultCount = 0
    $: childCount = 0
    $: babyCount = 0

    const onUpdateGuestCount = () => {
        dispatch('update', {
            guestCount: adultCount + childCount,
            babyCount: babyCount
        })
    }

    const minusAdultCount = () => {
        adultCount -= 1
        onUpdateGuestCount()
    }

    const plusAdultCount = () => {
        adultCount += 1
        onUpdateGuestCount()
    }

    const minusChildCount = () => {
        childCount -= 1
        onUpdateGuestCount()
    }

    const plusChildCount = () => {
        childCount += 1
        onUpdateGuestCount()
    }

    const minusBabyCount = () => {
        babyCount -= 1
        onUpdateGuestCount()
    }

    const plusBabyCount = () => {
        babyCount += 1
        onUpdateGuestCount()
    }
</script>

<div class="absolute z-[98] top-[94px] right-[5px] w-[400px] h-[355px] px-[2.8rem] py-[1.5rem] bg-white drop-shadow-md rounded-3xl animate-slidein whitespace-nowrap"
     role="menu" on:click|stopPropagation>
    <div class="flex flex-col h-full space-y-4">
        <div class="flex justify-between items-center py-6 border-b">
            <div class="flex flex-col">
                <h1 class="text-gray-800">
                    성인
                </h1>
                <div class="text-[13px] text-gray-500/75">
                    13세 이상
                </div>
            </div>
            <div class="w-[98px] flex gap-4 justify-between items-center">
                <button type="button" class="rangeBtn" disabled={adultCount === 0} on:click={e => {e.stopPropagation(); minusAdultCount();}}>
                    <span>
                        <i class="bi bi-dash-circle"></i>
                    </span>
                </button>

                <span>{adultCount}</span>

                <button type="button" class="rangeBtn" disabled={adultCount === 15} on:click={e => {e.stopPropagation(); plusAdultCount();}}>
                    <span>
                        <i class="bi bi-plus-circle"></i>
                    </span>
                </button>
            </div>
        </div>

        <div class="flex justify-between items-center py-6 border-b">
            <div class="flex flex-col">
                <h1 class="text-gray-800">
                    어린이
                </h1>
                <div class="text-[13px] text-gray-500/75">
                    2~12세
                </div>
            </div>
            <div class="w-[98px] flex gap-4 justify-between items-center">
                <button type="button" class="rangeBtn" disabled={childCount === 0} on:click={e => {e.stopPropagation(); minusChildCount();}}>
                    <span>
                        <i class="bi bi-dash-circle"></i>
                    </span>
                </button>

                <span>{childCount}</span>

                <button type="button" class="rangeBtn" disabled={childCount === 15} on:click={e => {e.stopPropagation(); plusChildCount();}}>
                    <span>
                        <i class="bi bi-plus-circle"></i>
                    </span>
                </button>
            </div>
        </div>

        <div class="flex justify-between items-center py-6">
            <div class="flex flex-col">
                <h1 class="text-gray-800">
                    유아
                </h1>
                <div class="text-[13px] text-gray-500/75">
                    2세 미만
                </div>
            </div>
            <div class="w-[98px] flex gap-4 justify-between items-center">
                <button type="button" class="rangeBtn" disabled={babyCount === 0} on:click={e => {e.stopPropagation(); minusBabyCount();}}>
                    <span>
                        <i class="bi bi-dash-circle"></i>
                    </span>
                </button>

                <span>{babyCount}</span>

                <button type="button" class="rangeBtn" disabled={babyCount === 15} on:click={e => {e.stopPropagation(); plusBabyCount();}}>
                    <span>
                        <i class="bi bi-plus-circle"></i>
                    </span>
                </button>
            </div>
        </div>
    </div>
</div>