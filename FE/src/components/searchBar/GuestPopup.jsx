import React, { useState, useEffect, useRef } from "react";
import "./Guest.css";

const GuestPopup = ({ totalGuests, onClose }) => {
  const [adults, setAdults] = useState(2);
  const [children, setChildren] = useState(0);
  const [infants, setInfants] = useState(0);
  const modalRef = useRef(null);

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (modalRef.current && !modalRef.current.contains(event.target)) {
        onClose(adults + children + infants);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [onClose, adults, children, infants]);

  const increment = (type) => {
    if (type === "adults") setAdults(adults + 1);
    if (type === "children") setChildren(children + 1);
    if (type === "infants") setInfants(infants + 1);
  };

  const decrement = (type) => {
    if (type === "adults" && adults > 0) setAdults(adults - 1);
    if (type === "children" && children > 0) setChildren(children - 1);
    if (type === "infants" && infants > 0) setInfants(infants - 1);
  };

  const handleApply = () => {
    onClose(adults + children + infants);
  };

  return (
    <div className="fixed inset-0 bg-gray-500 bg-opacity-75 flex justify-center items-center text-black">
      <div
        ref={modalRef}
        className="guest-popup bg-white p-6 rounded-lg shadow-lg w-80"
      >
        <div className="space-y-6">
          <div className="flex justify-between items-center">
            <div>
              <div className="font-bold text-gray-800">성인</div>
              <div className="text-gray-500 text-sm">만 13세 이상</div>
            </div>
            <div className="flex items-center space-x-2">
              <button
                className="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center"
                onClick={() => decrement("adults")}
              >
                -
              </button>
              <div>{adults}</div>
              <button
                className="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center"
                onClick={() => increment("adults")}
              >
                +
              </button>
            </div>
          </div>
          <div className="flex justify-between items-center">
            <div>
              <div className="font-bold text-gray-800">어린이</div>
              <div className="text-gray-500 text-sm">2~12세</div>
            </div>
            <div className="flex items-center space-x-2">
              <button
                className="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center"
                onClick={() => decrement("children")}
              >
                -
              </button>
              <div>{children}</div>
              <button
                className="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center"
                onClick={() => increment("children")}
              >
                +
              </button>
            </div>
          </div>
          <div className="flex justify-between items-center">
            <div>
              <div className="font-bold text-gray-800">유아</div>
              <div className="text-gray-500 text-sm">2세 미만</div>
            </div>
            <div className="flex items-center space-x-2">
              <button
                className="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center"
                onClick={() => decrement("infants")}
              >
                -
              </button>
              <div>{infants}</div>
              <button
                className="text-gray-600 bg-gray-300 hover:bg-gray-400 rounded-full w-8 h-8 flex items-center justify-center"
                onClick={() => increment("infants")}
              >
                +
              </button>
            </div>
          </div>
        </div>
        <button className="apply-button mt-6" onClick={handleApply}>
          적용
        </button>
      </div>
    </div>
  );
};

export default GuestPopup;
