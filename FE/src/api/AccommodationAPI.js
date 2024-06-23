// src/api/accommodationAPI.js
import axiosInstance from "./axiosInstance"; // axiosInstance 임포트

export const fetchFilteredAccommodations = async (
  latitude,
  longitude,
  filters
) => {
  try {
    const response = await axiosInstance.get("/products/available", {
      params: {
        checkInDate: filters.checkIn,
        checkOutDate: filters.checkOut,
        minPrice: filters.minPrice,
        maxPrice: filters.maxPrice,
        headCount: filters.headCount,
        latitude: latitude,
        longitude: longitude,
        distance: 10,
      },
    });
    return response.data;
  } catch (error) {
    console.error("Failed to fetch filtered accommodations:", error);
    throw error;
  }
};
