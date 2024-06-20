const fetchFilteredAccommodations = useCallback(
  async (latitude, longitude) => {
    try {
      const response = await axios.get("/api/products/available", {
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
      setAccommodations(response.data);
    } catch (error) {
      console.error("Failed to fetch filtered accommodations:", error);
    }
  },
  [filters]
);
