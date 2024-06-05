import { useEffect, useState } from "react";
import fetchAccommodations from "./fetchAccommodation";
import { AccommodationWithPrice } from "./types";
import createMarker from "./GoogleMapMarker";

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
        lat: 37.497942,
        lng: 127.027621,
      },
      zoom: 14,
      mapId: "bc3c0232f45fc3c5",
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
      const markerInstances: google.maps.marker.AdvancedMarkerElement[] = [];

      const test = createMarker({
        lat: 37.497942,
        lng: 127.027621,
        name: "테스트",
        price: 10000,
        map: googleMap,
      });
      markerInstances.push(test);

      accommodations.forEach((accommodation) => {
        const coo: number[] = accommodation.accomodation.location.point;
        console.log(accommodation.accomodation.name, coo[0], coo[1]);

        const marker = createMarker({
          lat: coo[1],
          lng: coo[0],
          name: accommodation.accomodation.name,
          price: accommodation.price,
          map: googleMap,
        });

        markerInstances.push(marker);
      });

      return () => {
        markerInstances.forEach((markerInstance) => {
          markerInstance.map = null;
        });
      };
    }
  }, [googleMap, accommodations]);

  return <></>;
}

export default GoogleMap;
