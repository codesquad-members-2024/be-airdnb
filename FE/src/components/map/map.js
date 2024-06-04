"use client";
import { useEffect, useRef, useState } from "react";

const MapComponent = () => {
  const mapRef = useRef(null);
  const [map, setMap] = useState(null);

  useEffect(() => {
    // GoogleMap.js 스크립트 로드 기다리기
    window.initMap = initMap;

    // 스크립트가 이미 페이지에 존재하는지 확인
    if (!window.google) {
      const script = document.createElement("script");
      script.src = `https://maps.googleapis.com/maps/api/js?key=${process.env.NEXT_PUBLIC_GOOGLE_MAP_API_KEY}&callback=initMap`;
      script.defer = true;
      document.head.appendChild(script);
    } else {
      initMap();
    }

    return () => {
      window.initMap = null;
    };
  }, []);

  // 지도 초기화
  const initMap = () => {
    if (window.google && !map) {
      navigator.geolocation.getCurrentPosition(function (position) {
        const currentLocation = {
          lat: position.coords.latitude,
          lng: position.coords.longitude,
        };

        const map = new window.google.maps.Map(mapRef.current, {
          center: currentLocation,
          zoom: 13,
        });

        const customIcon = {
          url: "../../public/images/logo.png",
          scaledSize: new google.maps.Size(40, 40), // 마커의 크기를 지정
          origin: new google.maps.Point(0, 0), // 이미지의 시작점 (기본적으로 0,0)
          anchor: new google.maps.Point(20, 20), // 마커의 앵커 포인트를 지정
        };

        new window.google.maps.Marker({
          position: currentLocation,
          map: map,
          title: "Your Location",
          icon: customIcon,
        });

        new window.google.maps.Circle({
          strokeColor: "#FF0000",
          strokeOpacity: 0.8,
          strokeWeight: 2,
          fillColor: "#FF0000",
          fillOpacity: 0.35,
          map: map,
          center: currentLocation,
          radius: 2000, // 2km
        });

        setMap(map);
      });
    }
  };

  return (
    <div id="map" ref={mapRef} style={{ height: "400px", width: "100%" }} />
  );
};

export default MapComponent;
