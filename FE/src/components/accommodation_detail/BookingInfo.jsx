import React, { useState } from 'react';
import { CSSTransition } from 'react-transition-group';
import '../../styles/BookingInfo.css';

const BookingInfo = ({ accommodationId, checkIn, checkOut, headCount }) => {
  const [pricingData, setPricingData] = useState(null);
  const [showPricing, setShowPricing] = useState(false);

  const fetchPricing = async () => {
    const params = new URLSearchParams({
      accommodationId,
      checkIn,
      checkOut,
      headCount,
    });
  
    const response = await fetch(`https://squadbnb.site/api/booking?${params.toString()}`, {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    const data = await response.json();
    setPricingData(data);
    setShowPricing(true);
  };

  const calculateTotalPrice = () => {
    const { roughTotalPrice, discountPrice, serviceFee, accommodationFee } = pricingData;
    return roughTotalPrice + serviceFee + accommodationFee - discountPrice;
  };

  const calculateAveragePricePerNight = () => {
    const totalPrice = calculateTotalPrice();
    const nights = (new Date(checkOut) - new Date(checkIn)) / (1000 * 60 * 60 * 24);
    return totalPrice / nights;
  };

  return (
    <div className="booking-info">
      <button onClick={fetchPricing}>Check Pricing</button>

      <CSSTransition
        in={showPricing}
        timeout={300}
        classNames="slide"
        unmountOnExit
      >
        <div className="pricing-details">
          <button className="close-button" onClick={() => setShowPricing(false)}>×</button>
          {pricingData && (
            <div className="pricing-content">
              <div className="price-info">
                <div className="price-item green">총 숙박 요금: ₩{pricingData.roughTotalPrice}</div>
                <div className="price-item red">할인 요금: -₩{pricingData.discountPrice}</div>
                <div className="price-item green">서비스 수수료: ₩{pricingData.serviceFee}</div>
                <div className="price-item green">숙박세와 수수료: ₩{pricingData.accommodationFee}</div>
              </div>
              <div className="total-price">결제 금액 : ₩{calculateTotalPrice()}</div>
              <div className="average-price">1박당 평균 요금 : ₩{calculateAveragePricePerNight()}</div>
              <button className="book-now">예약하기</button>
            </div>
          )}
        </div>
      </CSSTransition>
    </div>
  );
};

export default BookingInfo;