// pages/SearchPage.jsx
import React, { useEffect, useState, useCallback } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import axiosInstance from "../api/axiosInstance"; // axiosInstance 임포트
import "./SearchPage.css"; // CSS 파일 임포트
import SearchBar from "../components/searchBar/SearchBar.jsx"; // SearchBar 컴포넌트 임포트
import useDebounce from "../hooks/useDebounce.js"; // useDebounce 훅 임포트
import Map from "../components/list/Map.jsx"; // Map 컴포넌트 임포트
import AccommodationList from "../components/list/AccommodationList.jsx"; // AccommodationList 컴포넌트 임포트

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

  const debouncedPosition = useDebounce(currentPosition, 1000); // 1000ms 디바운스 적용

  const fetchFilteredAccommodations = useCallback(
    async (latitude, longitude) => {
      try {
        const params = {
          latitude: latitude,
          longitude: longitude,
          distance: 100000,
        };

        if (filters) {
          params.checkInDate = filters.checkIn;
          params.checkOutDate = filters.checkOut;
          params.minPrice = filters.minPrice;
          params.maxPrice = filters.maxPrice;
          params.headCount = filters.capacity;
        }

        const response = await axiosInstance.get("/products/available", {
          params,
        });
        setAccommodations(response.data);
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
          fetchFilteredAccommodations(latitude, longitude);
        },
        (error) => {
          console.error("Error fetching location:", error);
        }
      );
    }
  }, [fetchFilteredAccommodations]);

  useEffect(() => {
    if (debouncedPosition.latitude && debouncedPosition.longitude) {
      fetchFilteredAccommodations(
        debouncedPosition.latitude,
        debouncedPosition.longitude
      );
    }
  }, [debouncedPosition, fetchFilteredAccommodations]);

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
              accommodations={accommodations}
              mapLevel={mapLevel}
              setMapLevel={setMapLevel}
              setCurrentPosition={setCurrentPosition}
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
