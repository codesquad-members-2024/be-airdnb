import { useState, useEffect } from "react";

interface LatLng {
  lat: number;
  lng: number;
}

interface Bounds {
  northeast: LatLng;
  southwest: LatLng;
}

function useMapBoundsChanged(
  map: google.maps.Map | null,
  delay: number
): Bounds | null {
  const [debouncedBoundsChanged, setDebouncedBoundsChanged] =
    useState<Bounds | null>(null);

  useEffect(() => {
    let timerId: number;

    function debounce(func: (...args: any[]) => void, delay: number) {
      return function (this: any, ...args: any[]) {
        clearTimeout(timerId);
        timerId = window.setTimeout(() => {
          func.apply(this, args);
        }, delay);
      };
    }

    if (map) {
      const handleBoundsChanged = debounce(() => {
        const bounds = map.getBounds();
        if (bounds) {
          const ne = bounds.getNorthEast();
          const sw = bounds.getSouthWest();
          const query: Bounds = {
            northeast: { lat: ne.lat(), lng: ne.lng() },
            southwest: { lat: sw.lat(), lng: sw.lng() },
          };
          setDebouncedBoundsChanged(query);
        }
      }, delay);

      map.addListener("bounds_changed", handleBoundsChanged);

      return () => {
        if (timerId) clearTimeout(timerId);
        google.maps.event.removeListener(handleBoundsChanged);
      };
    }
  }, [map, delay]);

  return debouncedBoundsChanged;
}

export default useMapBoundsChanged;
