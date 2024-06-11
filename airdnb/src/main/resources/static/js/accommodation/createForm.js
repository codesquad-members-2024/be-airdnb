document.addEventListener("DOMContentLoaded", () => {
    fetchAccommodationTypes();
    fetchAccommodationRoomTypes();

    document.getElementById("accommodationForm").addEventListener("submit", (event) => {
        event.preventDefault();
        if (validateForm()) {
            submitForm();
        }
    });
});

function fetchAccommodationTypes() {
    fetch('/api/accommodation-types')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
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
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok ' + response.statusText);
            }
            return response.json();
        })
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

function validateForm() {
    let isValid = true;

    // Validate memberId (email)
    const memberId = document.getElementById('memberId');
    const memberIdError = document.getElementById('memberIdError');
    if (!memberId.value || !/\S+@\S+\.\S+/.test(memberId.value)) {
        memberIdError.textContent = '유효한 이메일을 입력하세요.';
        memberIdError.style.display = 'block';
        isValid = false;
    } else {
        memberIdError.style.display = 'none';
    }

    // Validate name (not empty)
    const name = document.getElementById('name');
    const nameError = document.getElementById('nameError');
    if (!name.value.trim()) {
        nameError.textContent = '숙소 이름을 입력하세요.';
        nameError.style.display = 'block';
        isValid = false;
    } else {
        nameError.style.display = 'none';
    }

    // Validate maxCapacity
    const maxCapacity = document.getElementById('maxCapacity');
    const maxCapacityError = document.getElementById('maxCapacityError');
    if (maxCapacity.value < 1 || maxCapacity.value > 16) {
        maxCapacityError.textContent = '숙박 최대 인원은 1에서 16 사이여야 합니다.';
        maxCapacityError.style.display = 'block';
        isValid = false;
    } else {
        maxCapacityError.style.display = 'none';
    }

    // Validate bedroomCount
    const bedroomCount = document.getElementById('bedroomCount');
    const bedroomCountError = document.getElementById('bedroomCountError');
    if (bedroomCount.value < 0 || bedroomCount.value > 50) {
        bedroomCountError.textContent = '침실 개수는 0에서 50 사이여야 합니다.';
        bedroomCountError.style.display = 'block';
        isValid = false;
    } else {
        bedroomCountError.style.display = 'none';
    }

    // Validate bathroomCount
    const bathroomCount = document.getElementById('bathroomCount');
    const bathroomCountError = document.getElementById('bathroomCountError');
    if (bathroomCount.value < 1 || bathroomCount.value > 50) {
        bathroomCountError.textContent = '화장실 개수는 1에서 50 사이여야 합니다.';
        bathroomCountError.style.display = 'block';
        isValid = false;
    } else {
        bathroomCountError.style.display = 'none';
    }

    // Validate bedCount
    const bedCount = document.getElementById('bedCount');
    const bedCountError = document.getElementById('bedCountError');
    if (bedCount.value < 1 || bedCount.value > 50) {
        bedCountError.textContent = '침대 개수는 1에서 50 사이여야 합니다.';
        bedCountError.style.display = 'block';
        isValid = false;
    } else {
        bedCountError.style.display = 'none';
    }

    // Validate perPrice
    const perPrice = document.getElementById('perPrice');
    const perPriceError = document.getElementById('perPriceError');
    if (perPrice.value < 10000 || perPrice.value > 15000000) {
        perPriceError.textContent = '1박당 가격은 10000에서 15000000 사이여야 합니다.';
        perPriceError.style.display = 'block';
        isValid = false;
    } else {
        perPriceError.style.display = 'none';
    }

    return isValid;
}

function submitForm() {
    const form = document.getElementById("accommodationForm");
    const formData = new FormData(form);
    const jsonData = {};

    formData.forEach((value, key) => {
        jsonData[key] = value;
    });

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
