<script>
    import QuestionSection from "../QuestionSection.svelte";
    import RoutingBtn from "../../../../../components/RoutingBtn.svelte";
    import LocationForm from "../../../../../components/LocationForm.svelte";
    import {urlPrefix} from "../Constants.js";
</script>

<QuestionSection title="Q6. 숙소 상세 주소를 확인해주세요"
                 description="주소는 게스트의 예약이 확정된 이후에 공개됩니다.">
    <LocationForm />
</QuestionSection>

<div class="flex-grow"></div>

<div class="flex mt-5 w-[75%] justify-between">
    <RoutingBtn targetUrl="{urlPrefix}/coordinate" btnText="이전"/>
    <RoutingBtn targetUrl="{urlPrefix}/floorPlan" btnText="다음"/>
</div>