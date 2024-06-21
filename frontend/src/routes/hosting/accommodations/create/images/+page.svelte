<script>
    import QuestionSection from "../QuestionSection.svelte";
    import RoutingBtn from "../../../../../components/RoutingBtn.svelte";
    import FilePond, { registerPlugin, supported } from 'svelte-filepond';
    import FilePondPluginImageExifOrientation from 'filepond-plugin-image-exif-orientation';
    import FilePondPluginImagePreview from 'filepond-plugin-image-preview';
    import {urlPrefix} from "../Constants.js";

    registerPlugin(FilePondPluginImageExifOrientation, FilePondPluginImagePreview);
    let pond;
    let name = 'filepond';
</script>

<QuestionSection title="Q9. 사진 추가하기"
                 description="마지막으로 숙소의 사진을 추가해주세요 (최소 5장)">
    <div class="w-[1000px] aspect-video mt-7">
        <FilePond
                bind:this={pond}
                {name}
                allowMultiple={true}
        />
    </div>
</QuestionSection>

<div class="flex-grow"></div>

<div class="flex mt-5 w-[75%] justify-between">
    <RoutingBtn targetUrl="{urlPrefix}/amenities" btnText="이전"/>
    <RoutingBtn targetUrl="/hosting" btnText="완료하기"/>
</div>