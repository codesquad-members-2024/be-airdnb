/* eslint-disable react-hooks/rules-of-hooks */
import { useEffect, useState } from "react";
import fetchAccommodations from "./fetchAccommodation";
import { AccommodationWithPrice } from "./types";

function GoogleMap() {
  const [googleMap, setGoogleMap] = useState<google.maps.Map>();
  const [accommodations, setAccommodations] = useState<
    AccommodationWithPrice[]
  >([]);

  useEffect(() => {
    const mapContainer = document.createElement("div");
    mapContainer.id = "map";
    mapContainer.style.minHeight = "100vh";
    document.body.appendChild(mapContainer);

    const instance = new window.google.maps.Map(mapContainer, {
      center: {
        lat: 37.5,
        lng: 127.0,
      },
      zoom: 16,
      // mapId: "bc3c0232f45fc3c5",
      disableDefaultUI: true,
      clickableIcons: false,
      minZoom: 10,
      maxZoom: 18,
      gestureHandling: "greedy",
      restriction: {
        latLngBounds: {
          north: 39,
          south: 32,
          east: 132,
          west: 124,
        },
        strictBounds: true,
      },
    });

    setGoogleMap(instance);

    return () => {
      // Cleanup function
      if (document.body.contains(mapContainer)) {
        document.body.removeChild(mapContainer);
      }
    };
  }, []);

  useEffect(() => {
    if (googleMap) {
      fetchAccommodations()
        .then((data) => {
          setAccommodations(data);
        })
        .catch((error) => {
          console.error("Error fetching accommodations:", error);
        });
    }
  }, [googleMap]);

  useEffect(() => {
    if (googleMap) {
      const markerInstances: google.maps.Marker[] = [];

      accommodations.forEach((accommodation) => {
        const [lat, lng] = accommodation.accomodation.location.point;

        const markerInstance = new google.maps.Marker({
          position: { lat, lng },
          map: googleMap,
          title: accommodation.accomodation.name,
          icon: {
            url: "/images/logo.png",
            scaledSize: new google.maps.Size(100, 100),
          },
        });

        markerInstance.addListener("click", () => {
          alert(
            `숙소: ${accommodation.accomodation.name}\n가격: ${accommodation.price}원`
          );
        });

        markerInstances.push(markerInstance);
      });

      return () => {
        markerInstances.forEach((markerInstance) => {
          markerInstance.setMap(null);
        });
      };
    }
  }, [googleMap, accommodations]);

  return <></>;
}

export default GoogleMap;
