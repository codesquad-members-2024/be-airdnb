<script>
    export let label;
    export let id;
    export let type = "text";
    export let value = "";
    export let readonly = false;
    export let placeholder;
</script>

<div class="mb-4">
    <label for={id} class="block text-sm font-medium text-gray-700 mb-1">{label}</label>
    <input type={type} id="{id}" name="{id}" readonly="{readonly}" value="{value}" placeholder="{placeholder}"
        class="mt-1, block w-full py-2 px-3 border border-gray-400 bg-white rounded-md sm:text-sm">
</div>