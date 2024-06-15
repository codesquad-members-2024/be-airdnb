function toggleCreateForm() {
    const createForm = document.getElementById('createForm');
    createForm.style.display = createForm.style.display === 'none' ? 'block' : 'none';
}

function saveAccommodationType() {
    const typeName = document.getElementById('typeName').value;
    fetch('/api/accommodation-room-type', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: typeName })
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
            alert('숙소 방 유형이 성공적으로 저장되었습니다.');
            location.reload();
        })
        .catch(error => {
            console.error('숙소 방 유형을 저장하는 중 오류가 발생했습니다:', error);
            alert('에러 메세지: ' + error.message);
        });
}

function confirmUpdate(oldName, newName) {
    return oldName === newName;
}

document.addEventListener('DOMContentLoaded', function () {
    fetch('/api/accommodation-room-types')
        .then(response => response.json())
        .then(data => {
            const listElement = document.getElementById('accommodationRoomTypeList');
            data.forEach(type => {
                const listItem = document.createElement('li');
                listItem.classList.add('list-item');

                const nameSpan = document.createElement('span');
                nameSpan.textContent = type.name;
                listItem.appendChild(nameSpan);

                const buttonsDiv = document.createElement('div');
                buttonsDiv.classList.add('button-container');

                const updateButton = document.createElement('button');
                updateButton.textContent = '수정';
                updateButton.classList.add('update-button');
                updateButton.onclick = function () {
                    const newName = prompt('새로운 유형 이름을 입력하세요:', type.name);
                    if (newName) {
                        if (confirmUpdate(type.name, newName)) {
                            alert('에러메세지 : 현재 이름과 동일합니다.');
                        } else {
                            updateAccommodationType(type.id, newName);
                        }
                    }
                };
                buttonsDiv.appendChild(updateButton);

                const deleteButton = document.createElement('button');
                deleteButton.textContent = '삭제';
                deleteButton.classList.add('delete-button');
                deleteButton.onclick = function () {
                    if (confirm('정말로 이 숙소 유형을 삭제하시겠습니까? 이로 인해 관련된 모든 숙소와의 연결이 끊어집니다.')) {
                        deleteAccommodationType(type.id);
                    }
                };
                buttonsDiv.appendChild(deleteButton);

                listItem.appendChild(buttonsDiv);
                listElement.appendChild(listItem);
            });
        })
        .catch(error => console.error('숙소 방 유형을 불러오는 중 오류가 발생했습니다:', error));
});

function updateAccommodationType(id, newName) {
    fetch(`/api/accommodation-room-type/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: newName })
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
            alert('숙소 방 유형이 성공적으로 업데이트되었습니다.');
            location.reload();
        })
        .catch(error => {
            console.error('숙소 방 유형을 업데이트하는 중 오류가 발생했습니다:', error);
            alert('에러 메세지: ' + error.message);
        });
}

function deleteAccommodationType(id) {
    fetch(`/api/accommodation-room-type/${id}`, {
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
            alert('숙소 방 유형이 성공적으로 삭제되었습니다.');
            location.reload();
        })
        .catch(error => {
            console.error('숙소 방 유형을 삭제하는 중 오류가 발생했습니다:', error);
            alert('에러 메세지: ' + error.message);
        });
}
