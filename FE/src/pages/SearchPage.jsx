import React, { useEffect, useState, useCallback } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import "./SearchPage.css"; // CSS 파일 임포트
import SearchBar from "../components/searchBar/SearchBar.jsx"; // SearchBar 컴포넌트 임포트
import useDebounce from "../hooks/useDebounce.js"; // useDebounce 훅 임포트
import Map from "../components/list/Map.jsx"; // Map 컴포넌트 임포트
import AccommodationList from "../components/list/AccommodationList.jsx"; // AccommodationList 컴포넌트 임포트
import { fetchFilteredAccommodations } from "../api/AccommodationAPI.js";

const SearchPage = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const { filters } = location.state || {};
  const [accommodations, setAccommodations] = useState([]);
  const [currentPosition, setCurrentPosition] = useState({
    latitude: null,
    longitude: null,
  });
  const [mapLevel, setMapLevel] = useState(5); // 지도 레벨 상태 추가
  const [map, setMap] = useState(null);

  const debouncedPosition = useDebounce(currentPosition, 1000); // 1000ms 디바운스 적용

  const fetchAccommodations = useCallback(
    async (latitude, longitude) => {
      try {
        const data = await fetchFilteredAccommodations(
          latitude,
          longitude,
          filters
        );
        setAccommodations(data);
      } catch (error) {
        console.error("Failed to fetch filtered accommodations:", error);
      }
    },
    [filters]
  );

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(
        (position) => {
          const { latitude, longitude } = position.coords;
          setCurrentPosition({ latitude, longitude });
          fetchAccommodations(latitude, longitude);
        },
        (error) => {
          console.error("Error fetching location:", error);
        }
      );
    }
  }, [fetchAccommodations]);

  useEffect(() => {
    if (debouncedPosition.latitude && debouncedPosition.longitude) {
      fetchAccommodations(
        debouncedPosition.latitude,
        debouncedPosition.longitude
      );
    }
  }, [debouncedPosition, fetchAccommodations]);

  useEffect(() => {
    if (map && accommodations.length > 0) {
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
    }
  }, [map, accommodations]);

  const handleItemClick = (acc) => {
    navigate(`/accommodation-detail/${acc.accommodation.id}`, {
      state: {
        accommodationId: acc.accommodation.id,
        checkIn: filters.checkIn,
        checkOut: filters.checkOut,
        headCount: filters.capacity,
      },
    });
  };

  return (
    <div className="search-page-container">
      <div className="search-bar-wrapper">
        <SearchBar />
      </div>
      <div className="filtered-results">
        <AccommodationList
          accommodations={accommodations}
          handleItemClick={handleItemClick}
        />
        <div className="map-container">
          {currentPosition.latitude && currentPosition.longitude ? (
            <Map
              currentPosition={currentPosition}
              mapLevel={mapLevel}
              setMapLevel={setMapLevel}
              setCurrentPosition={setCurrentPosition}
              onMapLoad={setMap}
            />
          ) : (
            <p>현재 위치를 가져오는 중...</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default SearchPage;
