import React from "react";

const AccommodationItem = ({ acc, handleItemClick }) => {
  return (
    <li className="accommodation-item" onClick={() => handleItemClick(acc)}>
      <div className="accommodation-details">
        <h3>{acc.accommodation.name}</h3>
        <p>{acc.accommodation.location.address}</p>
        <p className="accommodation-price">
          {acc.price.toLocaleString()}원 / 박
        </p>
        <div className="accommodation-rating">
          <span>⭐</span>
          <span>
            {acc.accommodation.rating} ({acc.accommodation.numOrReviews} 리뷰)
          </span>
        </div>
      </div>
    </li>
  );
};

export default AccommodationItem;
