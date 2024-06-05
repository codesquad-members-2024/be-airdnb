import { AccommodationWithPrice } from "./types";

async function fetchAccommodations(): Promise<AccommodationWithPrice[]> {
  try {
    const response = await fetch(
      "https://squadbnb.site/api/products/available?checkIn=2024-07-01&checkOut=2024-07-02&longitude=77&latitude=13&distance=100000000&continue"
    );

    if (!response.ok) {
      throw new Error(
        `Failed to fetch accommodations: ${response.status} ${response.statusText}`
      );
    }
    const responseData = await response.json(); // 한 번만 읽음
    return responseData;
  } catch (error) {
    console.error("Error fetching accommodations:", error);
    throw error; // 이 부분을 선택적으로 처리할 수 있습니다.
  }
}

export default fetchAccommodations;
