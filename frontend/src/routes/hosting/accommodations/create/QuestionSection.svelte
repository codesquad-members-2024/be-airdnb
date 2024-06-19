<script>
    import {urlPrefix} from "./Constants.js";
    import RoutingBtn from "../../../../components/RoutingBtn.svelte";

    export let title;
    export let description;
</script>

<div class="section-container min-w-[50%]">
    <div class="mb-[10px] font-bold text-2xl">{title}</div>
    <span class="text-sm text-gray-500 mb-4">{description}</span>
    <slot></slot>
</div>