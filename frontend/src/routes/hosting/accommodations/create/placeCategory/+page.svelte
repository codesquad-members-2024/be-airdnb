<script>
import QuestionSection from "../QuestionSection.svelte";
import Card from "../../../../../components/Card.svelte";
import RoutingBtn from "../../../../../components/RoutingBtn.svelte";
import {urlPrefix} from "../Constants.js";

let selectedPlaceCategoryCard;

const placeCategories = [
    { icon: '🏡', label: '주택' },
    { icon: '🏢', label: '아파트' },
    { icon: '🏚️', label: '헛간' },
    { icon: '🏨', label: '모텔' },
    { icon: '🚤', label: '보트' },
    { icon: '🏡', label: '통나무집' },
];

function placeCategoryClick(index) {
    selectedPlaceCategoryCard = index;
}
</script>

<QuestionSection title="Q4. 당신의 숙소를 가장 잘 설명할 수 있는 카테고리는 무엇인가요?" description="">
    <div class="grid grid-cols-3 gap-4 justify-center w-[1000px]">
        {#each placeCategories as placeCategory, index}
            <Card
                    icon={placeCategory.icon}
                    label={placeCategory.label}
                    isActive={selectedPlaceCategoryCard === index}
                    onClick={() => placeCategoryClick(index)}
            />
        {/each}
    </div>
</QuestionSection>

<div class="flex-grow"></div>

<div class="flex mt-5 w-[75%] justify-between">
    <RoutingBtn targetUrl="{urlPrefix}" btnText="이전"/>
    <RoutingBtn targetUrl="{urlPrefix}/coordinate" btnText="다음"/>
</div>
