<script>
    export let title;
    export let numOfCategory;

    const onIncreaseNum = () => {
        numOfCategory++;
    }

    const onDecreaseNum = () => {
        if((numOfCategory === 0) || (title === "침대" && numOfCategory === 1) || (title === "게스트" && numOfCategory === 1)) {
            return;
        }
        numOfCategory--;
    }

    $: isDisabled = (title === "침대" || title === "게스트" ) ? numOfCategory === 1 : numOfCategory === 0;
</script>

<div class="flex justify-between border-b border-b-gray-200 p-5">
    <span class="text-gray-700">{title}</span>
    <div class="flex gap-4">
        <button
                class="border border-airbnb-text-bold rounded-full px-3"
                class:disabled={isDisabled}
                on:click={onDecreaseNum}
                disabled={isDisabled}
        > -</button>
        {numOfCategory}
        <button class="border border-airbnb-text-bold rounded-full px-3" on:click={onIncreaseNum}>+
        </button>
    </div>
</div>

<style>
    .disabled {
        cursor: not-allowed;
        opacity: 0.2;
    }
</style>