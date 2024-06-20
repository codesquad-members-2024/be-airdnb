<script>
    const destinations = [
      { city: "서울", distance: "차로 30분 거리", image: "/assets/home/seoul.png" },
      { city: "의정부시", distance: "차로 30분 거리", image: "/assets/home/uijeongbu.png" },
      { city: "대구", distance: "차로 3.5시간 거리", image: "/assets/home/daegu.png" },
      { city: "대전", distance: "차로 2시간 거리", image: "/assets/home/daejeon.png" },
      { city: "광주", distance: "차로 4.5시간 거리", image: "/assets/home/gwangju.png" },
      { city: "수원시", distance: "차로 45분 거리", image: "/assets/home/suwon.png" },
      { city: "울산", distance: "차로 4.5시간 거리", image: "/assets/home/ulsan.png" },
      { city: "부천시", distance: "차로 45분 거리", image: "/assets/home/bucheon.png" }
    ];
  
    const places = [
      { title: "자연생활을 만끽할 수 있는 숙소", image: "/assets/home/nature.png" },
      { title: "독특한 공간", image: "/assets/home/unique.png" },
      { title: "집 전체", image: "/assets/home/whole_house.png" },
      { title: "반려동물 동반 가능", image: "/assets/home/pet_friendly.png" }
    ];
  </script>
  
  <style>
    .destination-card img, .place-card img {
      border-radius: 8px;
    }
  </style>
  
  <div class="py-12">
    <div class="container mx-auto px-4">
      <!-- 가까운 여행지 둘러보기 섹션 -->
      <h2 class="text-2xl font-bold mb-6 text-left">가까운 여행지 둘러보기</h2>
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-2 lg:grid-cols-4 gap-12">
        {#each destinations as { city, distance, image }}
          <div class="destination-card flex items-center space-x-4">
            <img src={image} alt={city} class="w-24 h-24 object-cover flex-none" />
            <div class="text-left">
              <div class="text-lg font-semibold">{city}</div>
              <div class="text-gray-600">{distance}</div>
            </div>
          </div>
        {/each}
      </div>
  
      <!-- 어디서나, 여행은 살아보는 거야! 섹션 -->
      <h2 class="text-2xl font-bold mt-12 mb-6 text-left">어디서나, 여행은 살아보는 거야!</h2>
      <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        {#each places as { title, image }}
          <div class="place-card flex flex-col items-start">
            <img src={image} alt={title} class="w-80 h-80 object-cover mb-2" />
            <div class="text-left w-full">
              <div class="text-xl">{title}</div>
            </div>
          </div>
        {/each}
      </div>
    </div>
  </div>
  