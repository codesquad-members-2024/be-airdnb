import React, { useEffect, useState } from 'react';
import Header from '../components/accommodation_detail/Header';
import ImageGallery from '../components/accommodation_detail/ImageGallery';
import Description from '../components/accommodation_detail/Description';
import HostInfo from '../components/accommodation_detail/HostInfo';
import BookingInfo from '../components/accommodation_detail/BookingInfo';
import '../styles/AccommodationDetail.css';
import { da } from 'date-fns/locale';

const AccommodationDetail = () => {
  const [data, setData] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await fetch('https://squadbnb.site/api/accommodation/1');
        if (!response.ok) {
          throw new Error('Network response was not ok ' + response.statusText);
        }
        const result = await response.json();
        console.log('Fetch result:', result); // 응답 확인을 위한 로그
        setData(result);
      } catch (error) {
        console.error('Fetch error:', error); // 에러 로그 출력
      }
    };

    fetchData();
  }, []);

  if (!data) return <div>Loading...</div>;

  return (
    <div className="app">
      <Header name={data.name} rating={data.rating} numOfReviews={data.numOfReviews} />
      <ImageGallery pictures={data.pictures} />
      <Description description={data.description} roomInformation={data.roomInformation} location={data.location} />
      <HostInfo host={data.host} />
      <BookingInfo 
        accommodationId={data.accommodationId} 
        checkIn={data.checkIn}
        checkOut={data.checkOut}
        headCount={data.headCount} 
      />
    </div>
  );
};

export default AccommodationDetail;