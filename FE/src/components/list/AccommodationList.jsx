// components/AccommodationList.jsx
import React from "react";
import AccommodationItem from "./AccommodationItem.jsx"; // AccommodationItem 컴포넌트 임포트

const AccommodationList = ({ accommodations, handleItemClick }) => {
  return (
    <div className="accommodation-list">
      <ul>
        {accommodations.map((acc) => (
          <AccommodationItem
            key={acc.accommodation.id}
            acc={acc}
            handleItemClick={handleItemClick}
          />
        ))}
      </ul>
    </div>
  );
};

export default AccommodationList;
