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

export interface AccommodationListCard {
  id: number;
  title: string;
  price: string;
  totalPrice: string;
  rating?: string;
  reviews?: string;
  discount?: string;
  image: string;
}
