function fetchAccommodationTypes() {
    fetch('/api/accommodation-types')
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('accommodationType');
            data.forEach(type => {
                const option = document.createElement('option');
                option.value = type.id;
                option.textContent = type.name;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching accommodation types:', error));
}

function fetchAccommodationRoomTypes() {
    fetch('/api/accommodation-room-types')
        .then(response => response.json())
        .then(data => {
            const select = document.getElementById('accommodationRoomType');
            data.forEach(roomType => {
                const option = document.createElement('option');
                option.value = roomType.id;
                option.textContent = roomType.name;
                select.appendChild(option);
            });
        })
        .catch(error => console.error('Error fetching accommodation room types:', error));
}

function fetchAmenities() {
    fetch('/api/amenities')
        .then(response => response.json())
        .then(data => {
            const container = document.getElementById('amenities');
            data.forEach(amenity => {
                const label = document.createElement('label');
                const checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.name = 'amenities';
                checkbox.value = amenity.id;
                label.appendChild(checkbox);
                label.appendChild(document.createTextNode(amenity.name));
                container.appendChild(label);
            });
        })
        .catch(error => console.error('Error fetching amenities:', error));
}
