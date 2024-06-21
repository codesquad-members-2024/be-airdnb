// src/types/kakao.d.ts
declare namespace kakao {
    namespace maps {
      class LatLng {
        constructor(lat: number, lng: number);
      }
  
      class Map {
        constructor(container: HTMLElement, options: { center: LatLng, level: number });
      }
    }
  }
  