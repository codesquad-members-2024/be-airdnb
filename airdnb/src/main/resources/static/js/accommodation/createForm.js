document.addEventListener("DOMContentLoaded", () => {
    fetchAccommodationTypes();
    fetchAccommodationRoomTypes();
    fetchAmenities();

    document.getElementById("accommodationForm").addEventListener("submit", (event) => {
        event.preventDefault();
        if (validateForm()) {
            submitForm();
        }
    });
});
