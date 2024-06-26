import React from 'react';
import '../../styles/Description.css';

const Description = ({ description, roomInformation, location }) => (
  <div className="description">
    <p>{description}</p>
    <div className="room-info">
      <h3>Room Information</h3>
      <ul>
        <li>Beds: {roomInformation.bedCount}</li>
        <li>Bedrooms: {roomInformation.bedroomCount}</li>
        <li>Bathrooms: {roomInformation.bathroomCount}</li>
        <li>Max Occupancy: {roomInformation.maxOccupancy}</li>
      </ul>
    </div>
    <div className="location-info">
      <h3>Location</h3>
      <p>{location.address}</p>
    </div>
  </div>
);

export default Description;