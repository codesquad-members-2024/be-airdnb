document.addEventListener("DOMContentLoaded", () => {
    fetchAmenities();

    document.querySelector('.create-button').addEventListener('click', toggleCreateForm);
    document.querySelector('.save-button').addEventListener('click', saveAmenity);
});

function fetchAmenities() {
    fetch('/api/amenities')
        .then(response => response.json())
        .then(data => {
            const list = document.getElementById('amenityList');
            list.innerHTML = '';
            data.forEach(amenity => {
                const listItem = document.createElement('li');
                listItem.className = 'list-item';
                listItem.innerHTML = `
                    <span>${amenity.name}</span>
                    <div class="button-container">
                        <button class="update-button" onclick="editAmenity(${amenity.id}, '${amenity.name}')">Update</button>
                        <button class="delete-button" onclick="deleteAmenity(${amenity.id})">Delete</button>
                    </div>
                `;
                list.appendChild(listItem);
            });
        })
        .catch(error => console.error('Error fetching amenities:', error));
}

function toggleCreateForm() {
    const form = document.getElementById('createForm');
    form.style.display = form.style.display === 'none' || form.style.display === '' ? 'block' : 'none';
}

function saveAmenity(event) {
    event.preventDefault();  // Prevent default form submission

    const nameInput = document.getElementById('amenityName');
    const name = nameInput.value.trim();
    if (!name) return;

    fetch('/api/amenity', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({name})
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorResponse => {
                    throw new Error(errorResponse.errorMessage);
                });
            }
            return response.json();
        })
        .then(data => {
            alert('편의시설이 성공적으로 저장되었습니다.');
            nameInput.value = '';
            toggleCreateForm();
            fetchAmenities();
        })
        .catch(error => {
            console.error('편의시설을 저장하는 중 오류가 발생했습니다:', error);
            alert('에러 메세지: ' + error.message);
        });
}

function editAmenity(id, name) {
    const newName = prompt('편의시설 이름을 수정하세요:', name);
    if (!newName) return;

    if (newName === name) {
        alert('에러 메세지: 현재 이름과 동일합니다.');
        return;
    }

    fetch(`/api/amenity/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({name: newName})
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorResponse => {
                    throw new Error(errorResponse.errorMessage);
                });
            }
            return response.json();
        })
        .then(data => {
            alert('편의시설이 성공적으로 업데이트되었습니다.');
            fetchAmenities();
        })
        .catch(error => {
            console.error('편의시설을 업데이트하는 중 오류가 발생했습니다:', error);
            alert('에러 메세지: ' + error.message);
        });
}


function deleteAmenity(id) {
    if (!confirm('정말로 이 편의시설을 삭제하시겠습니까?')) return;

    fetch(`/api/amenity/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                return response.json().then(errorResponse => {
                    throw new Error(errorResponse.errorMessage);
                });
            }
        })
        .then(data => {
            alert('편의시설이 성공적으로 삭제되었습니다.');
            fetchAmenities();
        })
        .catch(error => {
            console.error('편의시설을 삭제하는 중 오류가 발생했습니다:', error);
            alert('에러 메세지: ' + error.message);
        });
}
