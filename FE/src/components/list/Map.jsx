import React, { useEffect } from "react";

const Map = ({
  currentPosition,
  mapLevel,
  setMapLevel,
  setCurrentPosition,
  onMapLoad,
}) => {
  useEffect(() => {
    if (currentPosition.latitude && currentPosition.longitude) {
      const script = document.createElement("script");
      script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${
        import.meta.env.VITE_KAKAO_MAP_API_KEY
      }&autoload=false&libraries=services,clusterer,drawing`;
      script.async = true;
      document.head.appendChild(script);

      script.onload = () => {
        window.kakao.maps.load(() => {
          const mapContainer = document.getElementById("map");
          const mapOption = {
            center: new window.kakao.maps.LatLng(
              currentPosition.latitude,
              currentPosition.longitude
            ),
            level: mapLevel,
          };
          const map = new window.kakao.maps.Map(mapContainer, mapOption);

          window.kakao.maps.event.addListener(map, "dragend", () => {
            const latlng = map.getCenter();
            const latitude = latlng.getLat();
            const longitude = latlng.getLng();
            setCurrentPosition({ latitude, longitude });
          });

          window.kakao.maps.event.addListener(map, "zoom_changed", () => {
            const level = map.getLevel();
            setMapLevel(level); // 현재 지도 레벨을 상태에 저장
          });

          onMapLoad(map);
        });
      };

      script.onerror = () => {
        console.error("Failed to load Kakao Maps script");
      };

      return () => {
        document.head.removeChild(script);
      };
    }
  }, [currentPosition, mapLevel, setMapLevel, setCurrentPosition, onMapLoad]);

  return <div id="map" style={{ width: "100%", height: "100%" }}></div>;
};

export default Map;
