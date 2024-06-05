"user client";
interface MarkerOptions {
  lat: number;
  lng: number;
  name: string;
  price: number;
  map: google.maps.Map;
}

function createMarker({
  lat,
  lng,
  name,
  price,
  map,
}: MarkerOptions): google.maps.marker.AdvancedMarkerElement {
  const priceTag = document.createElement("div");
  priceTag.className = "marker";
  priceTag.textContent = price.toLocaleString() + "$";

  const markerInstance = new google.maps.marker.AdvancedMarkerElement({
    position: new google.maps.LatLng(lat, lng),
    map: map,
    title: name,
    content: priceTag,
  });

  markerInstance.addListener("click", () => {
    alert(`숙소: ${name}\n가격: ${price}원`);
  });

  return markerInstance;
}

export default createMarker;
