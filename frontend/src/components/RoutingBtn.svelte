<script>
    import {goto} from "$app/navigation";

    export let targetUrl;
    export let btnText;

    function handleNext() {
        goto(targetUrl);
    }
</script>

<button on:click={handleNext}
        class="text-gray-900 bg-white border border-gray-300 focus:outline-none hover:bg-gray-100
        focus:ring-4 focus:ring-gray-100 font-medium rounded-lg text-sm px-5 py-2.5 me-2 mb-2">
    {btnText}
</button>