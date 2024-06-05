export interface Location {
  address: string;
  zipCode: number;
  point: [number, number];
}

export interface Accommodation {
  id: number;
  name: string;
  imageUrl: string | null;
  location: Location;
  type: string;
}

export interface AccommodationWithPrice {
  accomodation: Accommodation;
  price: number;
}
