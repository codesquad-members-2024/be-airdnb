<script>
    import HostingHeader from "../../components/HostingHeader.svelte";
    import {Footer} from "flowbite-svelte";
    import HostingFooter from "../../components/HostingFooter.svelte";
</script>

<HostingHeader />
<div class="flex-grow">
    <slot/>
</div>
<HostingFooter />