<script>
  import { onMount, onDestroy } from 'svelte';

  export let total = 0;
  export let onClose;

  let adults = 2;
  let children = 0;
  let infants = 0;

  const updateTotal = () => {
    total = adults + children + infants;
  };

  const increment = (type) => {
    if (type === 'adults') adults++;
    if (type === 'children') children++;
    if (type === 'infants') infants++;
    updateTotal();
  };

  const decrement = (type) => {
    if (type === 'adults' && adults > 1) adults--; // Ensure adults do not go below 1
    if (type === 'children' && children > 0) children--;
    if (type === 'infants' && infants > 0) infants--;
    updateTotal();
  };

  const handleClickOutside = (event) => {
    if (!event.target.closest('.popup')) {
      updateTotal();
      onClose(total);
    }
  };

  onMount(() => {
    document.addEventListener('mousedown', handleClickOutside);
  });

  onDestroy(() => {
    document.removeEventListener('mousedown', handleClickOutside);
  });
</script>

<div class="fixed inset-0 bg-gray-500 bg-opacity-75 flex justify-center items-center text-black">
  <div class="popup bg-white p-6 rounded-lg shadow-lg w-80">
    <div class="space-y-6">
      <div class="flex justify-between items-center">
        <div>
          <div class="font-bold text-gray-800">성인</div>
          <div class="text-gray-500 text-sm">만 13세 이상</div>
        </div>
        <div class="flex items-center space-x-2">
          <button class="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => decrement('adults')}>-</button>
          <div>{adults}</div>
          <button class="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => increment('adults')}>+</button>
        </div>
      </div>

      <div class="flex justify-between items-center">
        <div>
          <div class="font-bold text-gray-800">어린이</div>
          <div class="text-gray-500 text-sm">만 2-12세</div>
        </div>
        <div class="flex items-center space-x-2">
          <button class="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => decrement('children')}>-</button>
          <div>{children}</div>
          <button class="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => increment('children')}>+</button>
        </div>
      </div>

      <div class="flex justify-between items-center">
        <div>
          <div class="font-bold text-gray-800">유아</div>
          <div class="text-gray-500 text-sm">만 2세 미만</div>
        </div>
        <div class="flex items-center space-x-2">
          <button class="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => decrement('infants')}>-</button>
          <div>{infants}</div>
          <button class="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center" on:click={() => increment('infants')}>+</button>
        </div>
      </div>
    </div>
    
  </div>
</div>

<style>
  .popup {
    z-index: 50; /* Ensure the popup is above other elements */
  }
  button {
    cursor: pointer;
  }
</style>
