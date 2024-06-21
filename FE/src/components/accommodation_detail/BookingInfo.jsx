import React, { useState } from "react";
import { CSSTransition } from "react-transition-group";
import axios from "axios";
import "../../styles/BookingInfo.css";

const BookingInfo = ({ accommodationId, checkIn, checkOut, headCount }) => {
  const [pricingData, setPricingData] = useState(null);
  const [showPricing, setShowPricing] = useState(false);

  const fetchPricing = async () => {
    try {
      const response = await axios.get("https://squadbnb.site/api/booking", {
        params: {
          accommodationId,
          checkIn,
          checkOut,
          headCount,
        },
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        },
      });
      setPricingData(response.data);
      setShowPricing(true);
    } catch (error) {
      console.error("Error fetching pricing:", error);
    }
  };

  const calculateTotalPrice = () => {
    const { roughTotalPrice, discountPrice, serviceFee, accommodationFee } =
      pricingData;
    return roughTotalPrice + serviceFee + accommodationFee - discountPrice;
  };

  const calculateAveragePricePerNight = () => {
    const totalPrice = calculateTotalPrice();
    const nights =
      (new Date(checkOut) - new Date(checkIn)) / (1000 * 60 * 60 * 24);
    return totalPrice / nights;
  };

  const handleBooking = async () => {
    const bookingData = {
      accommodationId,
      checkIn,
      checkOut,
      headCount,
    };

    try {
      const response = await axios.post(
        "https://squadbnb.site/api/booking",
        bookingData,
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          },
        }
      );

      if (response.status !== 201) {
        throw new Error("Network response was not created");
      }

      const data = response.data;
      // Handle success - you can add any success handling logic here
      alert(`예약 성공!
      숙소 이름: ${data.accName}
      예약 번호: ${data.bookingId}
      체크인: ${data.checkIn}
      체크아웃: ${data.checkOut}
      인원: ${data.headCount}명`);

      window.location.href = "https://squadbnb.site";
    } catch (error) {
      console.error("Error booking accommodation:", error);
      alert("Failed to book the accommodation.");
    }
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
          <button
            className="close-button"
            onClick={() => setShowPricing(false)}
          >
            ×
          </button>
          {pricingData && (
            <div className="pricing-content">
              <div className="price-info">
                <div className="price-item green">
                  총 숙박 요금: ₩{pricingData.roughTotalPrice}
                </div>
                <div className="price-item red">
                  할인 요금: -₩{pricingData.discountPrice}
                </div>
                <div className="price-item green">
                  서비스 수수료: ₩{pricingData.serviceFee}
                </div>
                <div className="price-item green">
                  숙박세와 수수료: ₩{pricingData.accommodationFee}
                </div>
              </div>
              <div className="total-price">
                결제 금액 : ₩{calculateTotalPrice()}
              </div>
              <div className="average-price">
                1박당 평균 요금 : ₩{calculateAveragePricePerNight()}
              </div>
              <button className="book-now" onClick={handleBooking}>
                예약하기
              </button>
            </div>
          )}
        </div>
      </CSSTransition>
    </div>
  );
};

export default BookingInfo;
