<script>
    import {onMount} from "svelte";

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

<div id="map" class="w-[720px] h-screen z-10"></div>
<div id="clickLatlng" class="mt-4"></div>