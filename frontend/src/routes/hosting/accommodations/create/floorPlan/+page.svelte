<script>
    import QuestionSection from "../QuestionSection.svelte";
    import RoutingBtn from "../../../../../components/RoutingBtn.svelte";
    import {urlPrefix} from "../Constants.js";
    import FloorPlanInputField from "../../../../../components/FloorPlanInputField.svelte";

    $: floorPlans = {
        "게스트": 4,
        "침실": 1,
        "침대": 1,
        "욕실": 1,
    }
</script>

<QuestionSection title="Q7. 숙소 기본 정보를 알려주세요"
                 description="침대 유형과 같은 세부 사항은 나중에 추가하실 수 있습니다">
    <div class="border-none max-w-md mt-10 p-2">
        {#each Object.entries(floorPlans) as [title, numOfCategory]}
            <FloorPlanInputField {title} bind:numOfCategory={numOfCategory}/>
        {/each}
    </div>
</QuestionSection>

<div class="flex-grow"></div>

<div class="flex mt-5 w-[75%] justify-between">
    <RoutingBtn targetUrl="{urlPrefix}/location" btnText="이전"/>
    <RoutingBtn targetUrl="{urlPrefix}/amenities" btnText="다음"/>
</div>