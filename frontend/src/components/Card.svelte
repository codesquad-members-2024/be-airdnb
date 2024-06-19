<script>
    export let icon;
    export let label;
    export let isActive = false;
    export let onClick;
</script>

<div class={`card border rounded-lg p-4 flex flex-col items-center justify-center cursor-pointer
${isActive ? 'bg-gray-200' : ''}`}
     on:click|stopPropagation={onClick}>
    <span class="text-2xl">{icon}</span>
    <span class="mt-2">{label}</span>
</div>