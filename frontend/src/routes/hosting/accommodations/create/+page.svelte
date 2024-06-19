<script>
    import QuestionSection from "./QuestionSection.svelte";
    import RoutingBtn from "../../../../components/RoutingBtn.svelte";
    import PriceInputField from "../../../../components/PriceInputField.svelte";
    import {urlPrefix} from "./Constants.js";
</script>

<div class="space-y-12 ">
    <QuestionSection title="Q1. 주택에 이름을 지어주세요"
                     description="숙소 이름은 짧을수록 효과적입니다. 나중에 언제든지 변경할 수 있으니, 너무 걱정하지 마세요.(35자 제한)">
        <div>
            <textarea
                    id="title"
                    rows="4"
                    class="block p-2.5 w-[1000px] text-sm text-gray-900 rounded-lg
                    border border-gray-300 focus:ring-blue-500 focus:border-blue-500"
                    placeholder="숙소 이름을 적어주세요"></textarea>
        </div>
    </QuestionSection>

    <QuestionSection title="Q2. 숙소에 대한 설명을 해주세요"
                     description="숙소 설명 작성하기 (500자 제한)">
        <textarea
                id="description"
                rows="4"
                class="block p-2.5 w-[1000px] text-sm text-gray-900 rounded-lg
                    border border-gray-300 focus:ring-blue-500 focus:border-blue-500"
                placeholder="숙소에 대한 설명을 적어주세요">편안함을 자랑하는 이곳에서 즐거운 시간을 보내실 수 있을 것입니다</textarea>
    </QuestionSection>

    <QuestionSection title="Q3. 이제 요금을 설정하세요"
                     description="언제든지 변경하실 수 있습니다. (₩13,659에서 ₩13,658,393)">
        <PriceInputField />
    </QuestionSection>
</div>

<div class="flex-grow"></div>

<div class="flex mt-5 w-[75%] justify-between">
    <RoutingBtn targetUrl="/hosting" btnText="등록취소"/>
    <RoutingBtn targetUrl="{urlPrefix}/placeCategory" btnText="다음"/>
</div>
