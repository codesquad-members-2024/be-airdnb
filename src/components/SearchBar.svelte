<script>
    // 필요한 경우 여기에 스크립트를 추가할 수 있습니다.
  </script>
  
  <div class="fixed top-24 left-1/2 transform -translate-x-1/2 w-full max-w-4xl">
    <div class="bg-white shadow-md rounded-full flex items-center w-full">
      <div class="flex-grow px-6 py-4 border-r">
        <div class="text-xs font-semibold text-gray-600">체크인</div>
        <div class="text-sm text-gray-800">날짜 입력</div>
      </div>
      <div class="flex-grow px-6 py-4 border-r">
        <div class="text-xs font-semibold text-gray-600">체크아웃</div>
        <div class="text-sm text-gray-800">날짜 입력</div>
      </div>
      <div class="flex-grow px-6 py-4 border-r">
        <div class="text-xs font-semibold text-gray-600">요금</div>
        <div class="text-sm text-gray-800">금액대 설정</div>
      </div>
      <div class="flex-grow px-6 py-4 border-r">
        <div class="text-xs font-semibold text-gray-600">인원</div>
        <div class="text-sm text-gray-800">게스트 추가</div>
      </div>
      <div class="px-4 py-4">
        <button class="bg-red-500 text-white rounded-full p-2 focus:outline-none">
          <svg class="w-6 h-6" fill="currentColor" viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
            <path fill-rule="evenodd" d="M12.9 14.32a8 8 0 111.414-1.414l4.294 4.294-1.414 1.414-4.294-4.294zM8 14a6 6 0 100-12 6 6 0 000 12z" clip-rule="evenodd"></path>
          </svg>
        </button>
      </div>
    </div>
  </div>
  
  <style>
    .border-r {
      border-right: 1px solid #e5e7eb; /* Tailwind's gray-200 color */
    }
  </style>
  