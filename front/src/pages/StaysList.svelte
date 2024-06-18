<script>
    import Header from "../components/Header.svelte";
    import MyNaverMap from "../components/MyNaverMap.svelte";
    import Footer from "../components/Footer.svelte";
    import StayDetail from "../components/StayDetail.svelte";

    const mockList = Array.from({ length: 20 }, (_, i) => i + 1);
</script>

<Header scrollMode={false}/>

<div class="flex justify-between items-start pt-4 mt-[80px] pl-3 scroll-auto">
    <!--  리스트  -->
    <div class="flex-1 pl-4">
        <div class="flex flex-col pt-2 space-y-3">
            <!--  300개 이상의 숙소 5월 12일 5월 18일 ₩100,000~₩1,000,000 개스트 3명  -->
            <div class="flex gap-2 text-[14px] text-gray-800">
                <span>300개 이상의 숙소</span>
                <span>·</span>
                <span>5월 12일 - 5월 18일</span>
                <span>·</span>
                <span>₩100,000</span>
                <span>~</span>
                <span>₩100,000</span>
                <span>·</span>
                <span>게스트 3명</span>
            </div>
            <div>
                <div class="font-bold text-black text-3xl">
                    지도에서 선택한 지역의 숙소
                </div>
            </div>

            <!--  숙소 리스트 -->
            <div class="flex flex-col gap-2 max-h-[1000px] items-start justify-start pt-2 space-y-3 overflow-y-scroll">
                {#each mockList as mock}
                <div class="border-b pb-4 pr-4">
                    <!--                예시 1-->
                    <button class="relative flex gap-4">
                        <div class="w-[330px] min-w-[280px] h-[220px] min-h-[200px]">
                            <img class="rounded-3xl" src="/assets/seoul.jpg" alt="이미지1">
                        </div>
                        <div class="flex flex-col items-start gap-1 py-1">
                            <div class="text-gray-500/80 text-[15px]">
                                서초구의 아파트 전체
                            </div>
                            <div class="text-gray-800 text-[1rem] truncate">
                                Spacious and Comfortable cozy house #4
                            </div>
                            <div class="flex flex-wrap pr-[4rem] text-gray-500/80 text-[14px]">
                                <span>최대 인원 3명</span>
                                <span> ∙ </span>
                                <span>원룸</span>
                                <span> ∙ </span>
                                <span>침대 1개</span>
                                <span> ∙ </span>
                                <span>욕실 1개</span>
                                <span> ∙ </span>
                                <span>주방</span>
                                <span> ∙ </span>
                                <span>무선 인터넷</span>
                                <span> ∙ </span>
                                <span>에어컨</span>
                                <span> ∙ </span>
                                <span>헤어드라이어</span>
                            </div>
                            <div class="absolute flex flex-col bottom-0 right-0 space-y-1">
                                <div class="flex gap-1 justify-between">
                                    <span class="font-bold">₩ 81,590</span>
                                    <div class="text-gray-800/80">/</div>
                                    <div class="text-gray-800/80">박</div>
                                </div>
                                <div class="text-[14px] text-gray-500/80 underline hover:text-gray-700">
                                    <span>총액 ₩ 1,493,150</span>
                                </div>
                            </div>
                        </div>
                    </button>
                </div>
                {/each}
            </div>
        </div>
    </div>

    <StayDetail />

    <!--  지도  -->
    <div class="flex-1 h-[1080px]">
        <MyNaverMap />
    </div>
</div>

<div class="-mt-[6rem]">
    <Footer />
</div>
