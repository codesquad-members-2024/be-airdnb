<script>
    import { onMount, onDestroy } from 'svelte';
  
    export let minPrice = 100000;
    export let maxPrice = 1000000;
    export let selectedMinPrice;
    export let selectedMaxPrice;
    export let onClose;
  
    const handleClickOutside = (event) => {
      if (!event.target.closest('.popup')) {
        onClose(selectedMinPrice, selectedMaxPrice);
      }
    };
  
    const roundToNearestThousand = (value) => {
      return Math.round(value / 1000) * 1000;
    };
  
    const handleMinChange = (event) => {
      selectedMinPrice = roundToNearestThousand(Math.min(event.target.value, selectedMaxPrice - 1000));
    };
  
    const handleMaxChange = (event) => {
      selectedMaxPrice = roundToNearestThousand(Math.max(event.target.value, selectedMinPrice + 1000));
    };
  
    const handleApply = () => {
      onClose(selectedMinPrice, selectedMaxPrice);
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
        <label for="min-price">최소 요금: ₩{selectedMinPrice.toLocaleString()}</label>
        <label for="max-price">최대 요금: ₩{selectedMaxPrice.toLocaleString()}</label>
        <div class="slider">
          <input id="min-price" type="range" min={minPrice} max={maxPrice} bind:value={selectedMinPrice} on:input={handleMinChange} step="1000"/>
          <input id="max-price" type="range" min={minPrice} max={maxPrice} bind:value={selectedMaxPrice} on:input={handleMaxChange} step="1000"/>
        </div>
      </div>
      <button class="apply-button" on:click={handleApply}>적용</button>
    </div>
  </div>
  
  <style>
    .popup {
      width: 300px;
      z-index: 50; /* Ensure the popup is above other elements */
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
      width: 30px;
      height: 30px;
      border-radius: 50%;
      background: url("../../public/assets/toggleBtn.png");
      background-repeat: no-repeat;
      background-size: contain;
      cursor: pointer;
      position: relative;
      z-index: 2;
    }
    input[type="range"]#max-price::-webkit-slider-thumb {
        background: url("../../public/assets/toggleBtn.png");
      background-repeat: no-repeat;
      background-size: contain;
    }
    input[type="range"]::-moz-range-thumb {
      pointer-events: all;
      width: 20px;
      height: 20px;
      border-radius: 50%;
      background: black;
      cursor: pointer;
      position: relative;
      z-index: 2;
    }
    input[type="range"]#max-price::-moz-range-thumb {
      background: black;
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
      margin-top: 20px; /* 여백 추가 */
      display: block; /* 버튼을 블록 요소로 만듦 */
      width: 100%; /* 버튼이 부모 요소의 너비를 차지하도록 */
      text-align: center; /* 텍스트 중앙 정렬 */
    }
    .apply-button:hover {
      background-color: #45a049; /* 호버 시 더 진한 녹색 */
    }
  </style>
  