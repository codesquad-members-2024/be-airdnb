import React, { useState, useEffect, useRef } from "react";
import "./Rate.css";

const RatePopup = ({
  minPrice = 100000,
  maxPrice = 1000000,
  selectedMinPrice,
  selectedMaxPrice,
  onClose,
}) => {
  const [min, setMin] = useState(selectedMinPrice);
  const [max, setMax] = useState(selectedMaxPrice);
  const modalRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        onClose(min, max);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [min, max, onClose]);

  const roundToNearestThousand = (value) => Math.round(value / 1000) * 1000;

  const handleMinChange = (event) =>
    setMin(roundToNearestThousand(Math.min(event.target.value, max - 1000)));
  const handleMaxChange = (event) =>
    setMax(roundToNearestThousand(Math.max(event.target.value, min + 1000)));

  const handleApply = () => onClose(min, max);

  return (
    <div className="fixed inset-0 bg-gray-500 bg-opacity-75 flex justify-center items-center text-black">
      <div ref={modalRef} className="popup bg-white p-6 rounded-lg shadow-lg">
        <div className="range-slider">
          <div className="text-lg font-bold mb-4">가격 범위</div>
          <div className="mb-6">평균 1박 요금은 ₩165,556원 입니다.</div>
          <label htmlFor="min-price">최소 요금: ₩{min.toLocaleString()}</label>
          <label htmlFor="max-price">최대 요금: ₩{max.toLocaleString()}</label>
          <div className="slider">
            <input
              id="min-price"
              type="range"
              min={minPrice}
              max={maxPrice}
              value={min}
              onChange={handleMinChange}
              step="1000"
            />
            <input
              id="max-price"
              type="range"
              min={minPrice}
              max={maxPrice}
              value={max}
              onChange={handleMaxChange}
              step="1000"
            />
          </div>
        </div>
        <button className="apply-button" onClick={handleApply}>
          적용
        </button>
      </div>
    </div>
  );
};

export default RatePopup;
