<script>
    import { onMount, onDestroy } from 'svelte';
    
    export let minPrice = 100000;
    export let maxPrice = 1000000;
    export let onClose;
  
    let minSelected = minPrice;
    let maxSelected = maxPrice;
  
    const handleClickOutside = (event) => {
      if (!event.target.closest('.popup')) {
        onClose(minSelected, maxSelected);
      }
    };
  
    const roundToNearestThousand = (value) => {
      return Math.round(value / 1000) * 1000;
    };
  
    const handleMinChange = (event) => {
      minSelected = roundToNearestThousand(Math.min(event.target.value, maxSelected - 1000));
    };
  
    const handleMaxChange = (event) => {
      maxSelected = roundToNearestThousand(Math.max(event.target.value, minSelected + 1000));
    };
  
    const handleApply = () => {
      onClose(minSelected, maxSelected);
    };
  
    onMount(() => {
      document.addEventListener('mousedown', handleClickOutside);
    });
  
    onDestroy(() => {
      document.removeEventListener('mousedown', handleClickOutside);
    });
  </script>
  
  <div class="fixed inset-0 bg-gray-500 bg-opacity-75 flex justify-center items-center text-black">
    <div class="popup bg-white p-6 rounded-lg shadow-lg">
      <div class="range-slider">
        <div class="text-lg font-bold mb-4">가격 범위</div>
        <div class="mb-6">평균 1박 요금은 ₩165,556원 입니다.</div>
        <label for="min-price">최소 요금: ₩{minSelected.toLocaleString()}</label>
        <label for="max-price">최대 요금: ₩{maxSelected.toLocaleString()}</label>
        <div class="slider">
          <input id="min-price" type="range" min={minPrice} max={maxPrice} bind:value={minSelected} on:input={handleMinChange} step="1000"/>
          <input id="max-price" type="range" min={minPrice} max={maxPrice} bind:value={maxSelected} on:input={handleMaxChange} step="1000"/>
        </div>
      </div>
      <button class="apply-button" on:click={handleApply}>적용</button>
    </div>
  </div>
  
  <style>
    .popup {
      width: 300px;
    }
    label {
      color: black;
      display: block;
      margin-bottom: 8px;
    }
    .range-slider {
      margin-bottom: 16px;
    }
    .slider {
      position: relative;
      height: 40px;
    }
    input[type="range"] {
      -webkit-appearance: none;
      width: 100%;
      position: absolute;
      pointer-events: none;
      background: transparent;
    }
    input[type="range"]::-webkit-slider-runnable-track {
      width: 100%;
      height: 4px; /* 막대기 굵기 */
      cursor: pointer;
      background: #ddd; /* 막대기 색상 */
      border-radius: 2px;
    }
    input[type="range"]::-moz-range-track {
      width: 100%;
      height: 4px; /* 막대기 굵기 */
      cursor: pointer;
      background: #ddd; /* 막대기 색상 */
      border-radius: 2px;
    }
    input[type="range"]::-webkit-slider-thumb {
      -webkit-appearance: none;
      pointer-events: all;
      width: 50px;
      height: 50px;
      border-radius: 50%;
      background: black;
      cursor: pointer;
      position: relative;
      z-index: 2;
      background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGZpbGw9Im5vbmUiIHZpZXdCb3g9IjAgMCAyNCAyNCIgc3Ryb2tlLXdpZHRoPSIxLjUiIHN0cm9rZT0iY3VycmVudENvbG9yIiBjbGFzcz0ic2l6ZS02Ij4KICA8cGF0aCBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiIGQ9Ik0xNSAxMkg5bTEyIDBhOSA5IDAgMSAxLTE4IDAgOSA5IDAgMCAxIDE4IDBaIi8+Cjwvc3ZnPgo=') no-repeat center;
      background-size: contain;
    }
    input[type="range"]#max-price::-webkit-slider-thumb {
      background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGZpbGw9Im5vbmUiIHZpZXdCb3g9IjAgMCAyNCAyNCIgc3Ryb2tlLXdpZHRoPSIxLjUiIHN0cm9rZT0iY3VycmVudENvbG9yIiBjbGFzcz0ic2l6ZS02Ij4KICA8cGF0aCBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiIGQ9Ik0xMiA5djZtMy0zaC02bTEyIDBhOSA5IDAgMSAxLTE4IDAgOSA5IDAgMCAxIDE4IDBaIi8+Cjwvc3ZnPgo=') no-repeat center;
      background-size: contain;
    }
    input[type="range"]::-moz-range-thumb {
      pointer-events: all;
      width: 30px;
      height: 30px;
      border-radius: 50%;
      background: black;
      cursor: pointer;
      position: relative;
      z-index: 2;
      background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGZpbGw9Im5vbmUiIHZpZXdCb3g9IjAgMCAyNCAyNCIgc3Ryb2tlLXdpZHRoPSIxLjUiIHN0cm9rZT0iY3VycmVudENvbG9yIiBjbGFzcz0ic2l6ZS02Ij4KICA8cGF0aCBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiIGQ9Ik0xNSAxMkg5bTEyIDBhOSA5IDAgMSAxLTE4IDAgOSA5IDAgMCAxIDE4IDBaIi8+Cjwvc3ZnPgo=') no-repeat center;
      background-size: contain;
    }
    input[type="range"]#max-price::-moz-range-thumb {
      background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIGZpbGw9Im5vbmUiIHZpZXdCb3g9IjAgMCAyNCAyNCIgc3Ryb2tlLXdpZHRoPSIxLjUiIHN0cm9rZT0iY3VycmVudENvbG9yIiBjbGFzcz0ic2l6ZS02Ij4KICA8cGF0aCBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm9rZS1saW5lam9pbj0icm91bmQiIGQ9Ik0xMiA5djZtMy0zaC02bTEyIDBhOSA5IDAgMSAxLTE4IDAgOSA5IDAgMCAxIDE4IDBaIi8+Cjwvc3ZnPgo=') no-repeat center;
      background-size: contain;
    }
    .apply-button {
      background-color: #4CAF50; /* 녹색 배경 */
      color: white; /* 흰색 글자 */
      padding: 10px 20px; /* 패딩 */
      border: none;
      border-radius: 5px; /* 둥근 모서리 */
      cursor: pointer; /* 커서 포인터 */
      font-size: 16px; /* 글자 크기 */
      font-weight: bold; /* 글자 두껍게 */
    }
    .apply-button:hover {
      background-color: #45a049; /* 호버 시 더 진한 녹색 */
    }
  </style>
  