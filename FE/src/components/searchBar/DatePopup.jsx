import React, { useState, useEffect, useRef } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { ko } from "date-fns/locale";
import "./custom-datepicker.css";

const DatePopup = ({
  checkIn,
  checkOut,
  dowLabels,
  monthLabels,
  onDateSelected,
  onToggle,
}) => {
  const [startDate, setStartDate] = useState(checkIn);
  const [endDate, setEndDate] = useState(checkOut);
  const modalRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        onToggle();
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [onToggle]);

  const handleSelect = (dates) => {
    const [start, end] = dates;
    setStartDate(start);
    setEndDate(end);
    onDateSelected({ startDate: start, endDate: end });
  };

  const renderHeader = ({
    monthDate,
    decreaseMonth,
    increaseMonth,
    prevMonthButtonDisabled,
    nextMonthButtonDisabled,
  }) => (
    <div className="custom-datepicker-header">
      <button
        onClick={decreaseMonth}
        disabled={prevMonthButtonDisabled}
        className="custom-datepicker-navigation-button"
      >
        <svg
          className="w-6 h-6"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M15 19l-7-7 7-7"
          />
        </svg>
      </button>
      <span className="text-lg font-semibold">
        {monthDate.getFullYear()}년 {monthDate.getMonth() + 1}월
      </span>
      <button
        onClick={increaseMonth}
        disabled={nextMonthButtonDisabled}
        className="custom-datepicker-navigation-button"
      >
        <svg
          className="w-6 h-6"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth={2}
            d="M9 5l7 7-7 7"
          />
        </svg>
      </button>
    </div>
  );

  return (
    <div className="custom-datepicker-container">
      <div className="custom-datepicker-header">
        <button
          onClick={onToggle}
          className="text-gray-600 hover:text-gray-900"
        >
          <svg
            className="w-6 h-6"
            fill="none"
            stroke="currentColor"
            viewBox="0 0 24 24"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              strokeLinecap="round"
              strokeLinejoin="round"
              strokeWidth={2}
              d="M6 18L18 6M6 6l12 12"
            />
          </svg>
        </button>
      </div>
      <div ref={modalRef}>
        <DatePicker
          selected={startDate}
          onChange={handleSelect}
          startDate={startDate}
          endDate={endDate}
          selectsRange
          inline
          locale={ko}
          monthsShown={2}
          dateFormat="yyyy년 MM월 dd일"
          dayClassName={() => "text-center"}
          renderCustomHeader={renderHeader}
          className="react-datepicker"
          wrapperClassName="react-datepicker__month-wrapper"
        />
      </div>
    </div>
  );
};

export default DatePopup;
