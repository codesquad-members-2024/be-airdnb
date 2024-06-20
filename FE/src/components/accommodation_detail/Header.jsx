import React from 'react';

const Header = ({ name, rating, numOfReviews }) => (
  <div className="header">
    <h1>{name}</h1>
    <div>
      <span>⭐ {rating}</span>
      <span>({numOfReviews} reviews)</span>
    </div>
  </div>
);

export default Header;