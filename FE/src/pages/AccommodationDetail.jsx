import React, { useEffect, useState } from "react";
import { useLocation } from "react-router-dom";
import Header from "../components/accommodation_detail/Header";
import ImageGallery from "../components/accommodation_detail/ImageGallery";
import Description from "../components/accommodation_detail/Description";
import HostInfo from "../components/accommodation_detail/HostInfo";
import BookingInfo from "../components/accommodation_detail/BookingInfo";
import Map from "../components/list/Map";
import "../styles/AccommodationDetail.css";

const AccommodationDetail = () => {
  const { state } = useLocation();
  const { accommodationId, checkIn, checkOut, headCount } = state;
  const [data, setData] = useState(null);
  const [map, setMap] = useState(null);
  const [mapLevel, setMapLevel] = useState(5);
  const [currentPosition, setCurrentPosition] = useState({
    latitude: null,
    longitude: null,
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch(
          `https://squadbnb.site/api/accommodation/${accommodationId}`
        );
        if (!response.ok) {
          throw new Error("Network response was not ok " + response.statusText);
        }
        const result = await response.json();
        console.log("Fetch result:", result); // 응답 확인을 위한 로그
        setData(result);
        setCurrentPosition({
          latitude: result.location.point[1],
          longitude: result.location.point[0],
        });
      } catch (error) {
        console.error("Fetch error:", error); // 에러 로그 출력
      }
    };

    fetchData();
  }, [accommodationId]);

  useEffect(() => {
    if (map && currentPosition.latitude && currentPosition.longitude) {
      const markerPosition = new window.kakao.maps.LatLng(
        currentPosition.latitude,
        currentPosition.longitude
      );
      const marker = new window.kakao.maps.Marker({
        position: markerPosition,
        title: data.name,
      });
      marker.setMap(map);
    }
  }, [map, currentPosition, data]);

  if (!data) return <div>Loading...</div>;

  return (
    <div className="app">
      <Header
        name={data.name}
        rating={data.rating}
        numOfReviews={data.numOfReviews}
      />
      <ImageGallery pictures={data.pictures} />
      <Description
        description={data.description}
        roomInformation={data.roomInformation}
        location={data.location}
      />
      <HostInfo host={data.host} />
      <BookingInfo
        accommodationId={accommodationId}
        checkIn={checkIn}
        checkOut={checkOut}
        headCount={headCount}
      />
      <div className="map-container">
        <Map
          currentPosition={currentPosition}
          mapLevel={mapLevel}
          setMapLevel={setMapLevel}
          setCurrentPosition={setCurrentPosition}
          onMapLoad={setMap}
        />
      </div>
    </div>
  );
};

export default AccommodationDetail;
