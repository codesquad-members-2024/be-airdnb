<script>
    import {filter} from "../../store/Filter.js";
    import {differenceInDays, parseISO} from "date-fns";
    import {createEventDispatcher} from "svelte";

    export let accoId;
    export let placeCategory;
    export let title;
    export let maxGuestCount;
    export let bedroomCount;
    export let bathroomCount;
    export let totalPrice;
    export let amenities;
    export let imageUrls;

    let stayLength = differenceInDays(parseISO($filter.checkOutDate), parseISO($filter.checkInDate));
    let pricePerNight = Math.floor(totalPrice / stayLength);

    const dispatch = createEventDispatcher();
    function handleClick() {
        dispatch('accoSelected', {accoId});
    }
</script>

<div class="w-[685px] h-[260px] flex border-b-airbnb-text-normal border-b border-b-gray-300 m-0.5 hover:cursor-pointer pb-3"
    on:click={handleClick}>
    <img src="https://images.contentstack.io/v3/assets/bltec2ed8e3c4b1e16d/bltfbcc7f32e0cd6ff5/getting-started-on-airbnb-optimized.jpg"
         class="object-cover w-[320px] h-full p-3 rounded-[1.5rem]"
         alt="이미지~">
    <div class="flex flex-col m-4 w-full">
        <div class="flex-grow">
            <div>
                <p class="text-gray-500 text-sm mb-3">{placeCategory}</p>
            </div>
            <div>
                <p class="mb-2.5 text-xl font-semibold text-gray-900">{title}</p>
            </div>
            <div class="text-gray-500 text-sm mb-2.5 flex flex-col gap-2">
                <div class="flex gap-2">
                    <p>최대 인원 {maxGuestCount}명  /</p>
                    <p> 방 {bedroomCount}개  /</p>
                    <p> 욕실 {bathroomCount}개</p>
                </div>
                <div class="flex gap-2">
                    {#each amenities as amenity}
                        <p>{amenity}</p>
                    {/each}
                </div>
            </div>
        </div>
        <div class="flex flex-col">
            <div class="flex gap-1 justify-end">
                <p>₩{pricePerNight}</p><p>/ 박</p>
            </div>
            <div class="flex justify-end text-sm text-gray-500 underline">
                <p>총액 ₩{totalPrice}</p>
            </div>
        </div>
    </div>
</div>