function submitForm() {
    const form = document.getElementById("accommodationForm");
    const formData = new FormData(form);
    const jsonData = {};

    formData.forEach((value, key) => {
        if (key === 'amenities') {
            if (!jsonData['amenityIds']) {
                jsonData['amenityIds'] = [];
            }
            jsonData['amenityIds'].push(Number(value));  // Ensure the values are converted to numbers
        } else {
            jsonData[key] = value;
        }
    });

    // Convert necessary fields to numbers
    jsonData['maxCapacity'] = Number(jsonData['maxCapacity']);
    jsonData['accommodationType'] = Number(jsonData['accommodationType']) || null;
    jsonData['accommodationRoomType'] = Number(jsonData['accommodationRoomType']) || null;
    jsonData['bedroomCount'] = Number(jsonData['bedroomCount']);
    jsonData['bathroomCount'] = Number(jsonData['bathroomCount']);
    jsonData['bedCount'] = Number(jsonData['bedCount']);
    jsonData['dayRate'] = Number(jsonData['dayRate']);
    jsonData['cleaningFee'] = Number(jsonData['cleaningFee']);

    fetch('/api/accommodation', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(jsonData)
    })
        .then(response => {
            if (response.ok) {
                alert('숙소가 성공적으로 등록되었습니다.');
                form.reset();
            } else {
                alert('숙소 등록에 실패했습니다.');
            }
        })
        .catch(error => console.error('Error submitting form:', error));
}

document.getElementById("accommodationForm").addEventListener("submit", function(event) {
    event.preventDefault();
    submitForm();
});
