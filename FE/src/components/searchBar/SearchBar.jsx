import React, { useState } from "react";
import { format, differenceInDays } from "date-fns";
import DatePopup from "./DatePopup";
import RatePopup from "./RatePopup";
import GuestPopup from "./GuestPopup";
import "./searchbar.css";

const SearchBar = () => {
  const [checkIn, setCheckIn] = useState(null);
  const [checkOut, setCheckOut] = useState(null);
  const [selectedMinPrice, setSelectedMinPrice] = useState(100000);
  const [selectedMaxPrice, setSelectedMaxPrice] = useState(1000000);
  const [totalGuests, setTotalGuests] = useState(0);
  const [onDatePickerPopup, setOnDatePickerPopup] = useState(false);
  const [onRatePopup, setOnRatePopup] = useState(false);
  const [onGuestPopup, setOnGuestPopup] = useState(false);

  const dateFormat = "M월 d일";
  const urlDateFormat = "yyyy-MM-dd";
  const dowLabels = ["일", "월", "화", "수", "목", "금", "토"];
  const monthLabels = [
    "1월",
    "2월",
    "3월",
    "4월",
    "5월",
    "6월",
    "7월",
    "8월",
    "9월",
    "10월",
    "11월",
    "12월",
  ];

  const toggleDatePicker = () => setOnDatePickerPopup(!onDatePickerPopup);
  const toggleRatePopup = () => setOnRatePopup(!onRatePopup);
  const toggleGuestPopup = () => setOnGuestPopup(!onGuestPopup);

  const formatDate = (dateString) =>
    (dateString && format(new Date(dateString), dateFormat)) || "";
  const formatUrlDate = (dateString) =>
    (dateString && format(new Date(dateString), "yyyy-MM-dd")) || "";

  const urlFormmattedCheckIn = formatUrlDate(checkIn);
  const urlFormmattedCheckOut = formatUrlDate(checkOut);
  const formattedCheckIn = formatDate(checkIn);
  const formattedCheckOut = formatDate(checkOut);

  const handleDateSelected = ({ startDate, endDate }) => {
    // setOnDatePickerPopup(!onDatePickerPopup);
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
    const checkInDate = urlFormmattedCheckIn;
    const checkOutDate = urlFormmattedCheckOut;
    const length = differenceInDays(new Date(checkOut), new Date(checkIn));
    const url = `/homes?checkin=${checkInDate}&checkout=${checkOutDate}&length=${length}&capacity=${totalGuests}&price_min=${selectedMinPrice}&price_max=${selectedMaxPrice}`;
    window.location.href = url;
  };

  return (
    <div className="search-bar-container">
      <div className="search-bar">
        <button
          type="button"
          className="search-bar-button"
          onClick={toggleDatePicker}
        >
          <div className="label">체크인</div>
          <div className="value">{formattedCheckIn || "날짜 입력"}</div>
        </button>
        <button
          type="button"
          className="search-bar-button"
          onClick={toggleDatePicker}
        >
          <div className="label">체크아웃</div>
          <div className="value">{formattedCheckOut || "날짜 입력"}</div>
        </button>
        <button
          type="button"
          className="search-bar-button"
          onClick={toggleRatePopup}
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
          onClick={toggleGuestPopup}
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
            dowLabels={dowLabels}
            monthLabels={monthLabels}
            onDateSelected={handleDateSelected}
            onToggle={toggleDatePicker}
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
