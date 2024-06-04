"use client";

import { useEffect, useState } from "react";
import { createRoot } from "react-dom/client";
import { darkMapStyle } from "@/styles/mapstyle";

function GoogleMap() {
  const [googleMap, setGoogleMap] = useState<google.maps.Map>();

  useEffect(() => {
    const mapContainer = document.createElement("div");

    mapContainer.id = "map";
    mapContainer.style.minHeight = "100vh";

    document.body.appendChild(mapContainer);

    const instance = new window.google.maps.Map(mapContainer, {
      center: {
        lat: 37.5,
        lng: 127.0,
      },
      zoom: 16,
      mapId: "bc3c0232f45fc3c5",
      disableDefaultUI: true,
      clickableIcons: false,
      minZoom: 10,
      maxZoom: 18,
      gestureHandling: "greedy",
      restriction: {
        latLngBounds: {
          north: 39,
          south: 32,
          east: 132,
          west: 124,
        },
        strictBounds: true,
      },
      styles: darkMapStyle,
    });

    setGoogleMap(instance);

    return () => {
      document.body.removeChild(mapContainer);
    };
  }, []);

  useEffect(() => {
    // 좌표 리스트
    const coordinates = [
      { lat: 37.5, lng: 127.0 },
      { lat: 37.51, lng: 127.01 },
      { lat: 37.52, lng: 127.02 },
      { lat: 37.53, lng: 127.03 },
      { lat: 37.54, lng: 127.04 },
      { lat: 37.55, lng: 127.05 },
      { lat: 37.56, lng: 127.06 },
      { lat: 37.57, lng: 127.07 },
      { lat: 37.58, lng: 127.08 },
      { lat: 37.59, lng: 127.09 },
      // 이하 추가 좌표들...
    ];

    const markerInstances: google.maps.Marker[] = [];

    // 좌표 리스트를 반복하여 각 좌표에 대한 마커를 추가하는 로직
    coordinates.forEach((coord) => {
      const markerContainer = document.createElement("div");
      const markerInstance = new google.maps.marker.AdvancedMarkerElement({
        position: coord,
        map: googleMap,
        title: "마커",
        content: markerContainer,
      });
      createRoot(markerContainer).render(
        <div style={{ backgroundColor: "blue", padding: "10px" }}>마커</div>
      );
      markerInstance.addListener("click", () => {
        alert("마커 클릭");
      });

      markerInstances.push(markerInstance);
    });

    // Clean-up 함수
    return () => {
      // Clean-up 로직
      // (여기서는 각 마커의 map 속성을 null로 설정하여 지도에서 마커를 제거합니다)
      markerInstances.forEach((markerInstance) => {
        markerInstance.map = null;
      });
    };
  }, [googleMap]);

  return <></>;
}

export default GoogleMap;
