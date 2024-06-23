// components/Map.jsx
import React, { useEffect } from "react";

const Map = ({
  currentPosition,
  accommodations,
  mapLevel,
  setMapLevel,
  setCurrentPosition,
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

          accommodations.forEach((acc) => {
            const markerPosition = new window.kakao.maps.LatLng(
              acc.accommodation.location.point[1], // latitude
              acc.accommodation.location.point[0] // longitude
            );
            const marker = new window.kakao.maps.Marker({
              position: markerPosition,
              title: acc.accommodation.name,
            });
            marker.setMap(map);

            const overlayContent = document.createElement("div");
            overlayContent.className = "customoverlay";
            overlayContent.innerHTML = `
              <h4>${acc.accommodation.name}</h4>
              <p>${acc.price.toLocaleString()}원</p>
            `;

            const customOverlay = new window.kakao.maps.CustomOverlay({
              position: markerPosition,
              content: overlayContent,
              yAnchor: 1,
            });

            customOverlay.setMap(map);
          });

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
        });
      };
      script.onerror = () => {
        console.error("Failed to load Kakao Maps script");
      };

      return () => {
        document.head.removeChild(script);
      };
    }
  }, [
    currentPosition,
    accommodations,
    mapLevel,
    setMapLevel,
    setCurrentPosition,
  ]);

  return <div id="map" style={{ width: "100%", height: "100%" }}></div>;
};

export default Map;
