import React, { useState } from "react";
import { format } from "date-fns";
import DatePopup from "./DatePopup";
import RatePopup from "./RatePopup";
import GuestPopup from "./GuestPopup";
import "./searchbar.css";
import { useNavigate } from "react-router-dom";

const SearchBar = () => {
  const [checkIn, setCheckIn] = useState(null);
  const [checkOut, setCheckOut] = useState(null);
  const [selectedMinPrice, setSelectedMinPrice] = useState(100000);
  const [selectedMaxPrice, setSelectedMaxPrice] = useState(1000000);
  const [totalGuests, setTotalGuests] = useState(0);
  const [onDatePickerPopup, setOnDatePickerPopup] = useState(false);
  const [onRatePopup, setOnRatePopup] = useState(false);
  const [onGuestPopup, setOnGuestPopup] = useState(false);

  const navigate = useNavigate();

  const dateFormat = "M월 d일";
  const formatDate = (dateString) =>
    (dateString && format(new Date(dateString), dateFormat)) || "";

  const formattedCheckIn = formatDate(checkIn);
  const formattedCheckOut = formatDate(checkOut);

  const handleDateSelected = ({ startDate, endDate }) => {
    setCheckIn(startDate);
    setCheckOut(endDate);
  };

  const handleRateSelected = (min, max) => {
    setOnRatePopup(!onRatePopup);
    setSelectedMinPrice(min);
    setSelectedMaxPrice(max);
  };

  const handleGuestsSelected = (total) => {
    setOnGuestPopup(!onGuestPopup);
    setTotalGuests(total);
  };

  const handleSearch = () => {
    const checkInDate = format(new Date(checkIn), "yyyy-MM-dd");
    const checkOutDate = format(new Date(checkOut), "yyyy-MM-dd");

    const filters = {
      checkIn: checkInDate,
      checkOut: checkOutDate,
      minPrice: selectedMinPrice,
      maxPrice: selectedMaxPrice,
      capacity: totalGuests,
    };

    navigate("/search", { state: { filters } });
  };

  return (
    <div className="search-bar-container">
      <div className="search-bar">
        <button
          type="button"
          className="search-bar-button"
          onClick={() => setOnDatePickerPopup(!onDatePickerPopup)}
        >
          <div className="label">체크인</div>
          <div className="value">{formattedCheckIn || "날짜 입력"}</div>
        </button>
        <button
          type="button"
          className="search-bar-button"
          onClick={() => setOnDatePickerPopup(!onDatePickerPopup)}
        >
          <div className="label">체크아웃</div>
          <div className="value">{formattedCheckOut || "날짜 입력"}</div>
        </button>
        <button
          type="button"
          className="search-bar-button"
          onClick={() => setOnRatePopup(!onRatePopup)}
        >
          <div className="label">요금</div>
          <div className="value">
            ₩{selectedMinPrice.toLocaleString()} - ₩
            {selectedMaxPrice.toLocaleString()}
          </div>
        </button>
        <button
          type="button"
          className="search-bar-button"
          onClick={() => setOnGuestPopup(!onGuestPopup)}
        >
          <div className="label">인원</div>
          <div className="value">게스트 {totalGuests}명</div>
        </button>
        <div className="search-icon-container">
          <button className="search-icon-button" onClick={handleSearch}>
            <svg
              className="search-icon"
              fill="currentColor"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                fillRule="evenodd"
                d="M12.9 14.32a8 8 0 111.414-1.414l4.294 4.294-1.414 1.414-4.294-4.294zM8 14a6 6 0 100-12 6 6 0 000 12z"
                clipRule="evenodd"
              ></path>
            </svg>
          </button>
        </div>

        {onDatePickerPopup && (
          <DatePopup
            checkIn={checkIn}
            checkOut={checkOut}
            onDateSelected={handleDateSelected}
            onToggle={() => setOnDatePickerPopup(!onDatePickerPopup)}
          />
        )}
      </div>

      {onRatePopup && (
        <RatePopup
          selectedMinPrice={selectedMinPrice}
          selectedMaxPrice={selectedMaxPrice}
          onClose={handleRateSelected}
        />
      )}

      {onGuestPopup && (
        <GuestPopup totalGuests={totalGuests} onClose={handleGuestsSelected} />
      )}
    </div>
  );
};

export default SearchBar;
