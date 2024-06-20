import React from 'react';

const ImageGallery = ({ pictures }) => (
  <div className="image-gallery">
    {pictures.map((picture, index) => (
      <img key={index} src={picture} alt={`Accommodation ${index}`} />
    ))}
  </div>
);

export default ImageGallery;