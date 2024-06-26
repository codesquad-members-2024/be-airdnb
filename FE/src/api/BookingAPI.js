import { authInstance } from "./axiosInstance";

export const fetchBookings = async () => {
  try {
    const response = await authInstance.get("/booking/my-bookings", {});
    return response.data;
  } catch (error) {
    console.error("Error fetching bookings:", error);
    throw error;
  }
};

export const fetchPricing = async (
  accommodationId,
  checkIn,
  checkOut,
  headCount
) => {
  try {
    const response = await authInstance.get("/booking", {
      params: {
        accommodationId,
        checkIn,
        checkOut,
        headCount,
      },
      headers: {
        "Content-Type": "application/json",
      },
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching pricing:", error);
    throw error;
  }
};

export const handleBooking = async (
  accommodationId,
  checkIn,
  checkOut,
  headCount
) => {
  const bookingData = {
    accommodationId,
    checkIn,
    checkOut,
    headCount,
  };

  try {
    const response = await authInstance.post("/booking", bookingData, {
      headers: {
        "Content-Type": "application/json",
      },
    });

    if (response.status !== 201) {
      throw new Error("Network response was not created");
    }

    return response.data;
  } catch (error) {
    console.error("Error booking accommodation:", error);
    throw error;
  }
};
