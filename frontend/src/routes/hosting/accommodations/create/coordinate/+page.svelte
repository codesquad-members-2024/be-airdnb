<script>
    import QuestionSection from "../QuestionSection.svelte";
    import {onMount} from "svelte";
    import RoutingBtn from "../../../../../components/RoutingBtn.svelte";
    import {urlPrefix} from "../Constants.js";

    onMount(() => {
        const container = document.getElementById('map');
        const options = {
            center: new kakao.maps.LatLng(37.490823, 127.033435),
            level: 3
        };
        const map = new kakao.maps.Map(container, options);

        // 지도를 클릭한 위치에 표출할 마커입니다
        let marker = new kakao.maps.Marker({
            // 지도 중심좌표에 마커를 생성합니다
            position: map.getCenter()
        });
        // 지도에 마커를 표시합니다
        marker.setMap(map);

        // 지도에 클릭 이벤트를 등록합니다
        // 지도를 클릭하면 마지막 파라미터로 넘어온 함수를 호출합니다
        kakao.maps.event.addListener(map, 'click', function(mouseEvent) {
            // 클릭한 위도, 경도 정보를 가져옵니다
            let latlng = mouseEvent.latLng;

            // 마커 위치를 클릭한 위치로 옮깁니다
            marker.setPosition(latlng);
            let message = '위도는 ' + latlng.getLat() + ' 이고, ';
            message += '경도는 ' + latlng.getLng() + ' 입니다';
            let resultDiv = document.getElementById('clickLatlng');
            resultDiv.innerHTML = message;})
    })
</script>

<QuestionSection title="Q5. 숙소 위치를 입력해주세요" description="">
    <div class="w-full aspect-video">
        <div id="map" class="w-full h-full"></div>
        <div id="clickLatlng" class="mt-4"></div>
    </div>
</QuestionSection>

<div class="flex-grow"></div>

<div class="flex mt-5 w-[75%] justify-between">
    <RoutingBtn targetUrl="{urlPrefix}/placeCategory" btnText="이전"/>
    <RoutingBtn targetUrl="{urlPrefix}/location" btnText="다음"/>
</div>