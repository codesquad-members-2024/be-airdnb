function toggleCreateForm() {
    const createForm = document.getElementById('createForm');
    createForm.style.display = createForm.style.display === 'none' ? 'block' : 'none';
}

function saveAccommodationType() {
    const typeName = document.getElementById('typeName').value;
    fetch('api/accommodation-type', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name: typeName })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('숙소 유형을 저장하는 데 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            alert('숙소 유형이 성공적으로 저장되었습니다.');
            location.reload();
        })
        .catch(error => {
            console.error('숙소 유형을 저장하는 중 오류가 발생했습니다:', error);
            alert('숙소 유형을 저장하는 데 실패했습니다.');
        });
}

document.addEventListener('DOMContentLoaded', function () {
    fetch('api/accommodation-types')
        .then(response => response.json())
        .then(data => {
            const listElement = document.getElementById('accommodationTypeList');
            data.forEach(type => {
                const listItem = document.createElement('li');
                listItem.classList.add('list-item'); // 스타일링을 위한 클래스 추가

                const nameSpan = document.createElement('span');
                nameSpan.textContent = type.name;
                listItem.appendChild(nameSpan);

                const buttonsDiv = document.createElement('div'); // 버튼을 위한 컨테이너

                const updateButton = document.createElement('button');
                updateButton.textContent = '수정';
                updateButton.classList.add('update-button'); // 스타일링을 위한 클래스 추가
                updateButton.onclick = function () {
                    const newName = prompt('새로운 유형 이름을 입력하세요:', type.name);
                    if (newName) {
                        updateAccommodationType(type.id, newName);
                    }
                };
                buttonsDiv.appendChild(updateButton);

                const deleteButton = document.createElement('button');
                deleteButton.textContent = '삭제';
                deleteButton.classList.add('delete-button'); // 스타일링을 위한 클래스 추가
                deleteButton.onclick = function () {
                    if (confirm('정말로 이 숙소 유형을 삭제하시겠습니까? 이로 인해 관련된 모든 숙소와의 연결이 끊어집니다.')) {
                        deleteAccommodationType(type.id);
                    }
                };
                buttonsDiv.appendChild(deleteButton);

                listItem.appendChild(buttonsDiv); // 버튼 컨테이너를 리스트 아이템에 추가
                listElement.appendChild(listItem);
            });
        })
        .catch(error => console.error('숙소 유형을 불러오는 중 오류가 발생했습니다:', error));
});

function updateAccommodationType(id, newName) {
    fetch(`api/accommodation-type/${id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({name: newName})
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('숙소 유형을 업데이트하는 데 실패했습니다.');
            }
            return response.json();
        })
        .then(data => {
            alert('숙소 유형이 성공적으로 업데이트되었습니다.');
            location.reload();
        })
        .catch(error => {
            console.error('숙소 유형을 업데이트하는 중 오류가 발생했습니다:', error);
            alert('숙소 유형을 업데이트하는 데 실패했습니다.');
        });
}

function deleteAccommodationType(id) {
    fetch(`api/accommodation-type/${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (response.ok) {
                alert('숙소 유형이 성공적으로 삭제되었습니다.');
                location.reload();
            } else {
                throw new Error('숙소 유형을 삭제하는 데 실패했습니다.');
            }
        })
        .catch(error => {
            console.error('숙소 유형을 삭제하는 중 오류가 발생했습니다:', error);
            alert('숙소 유형을 삭제하는 데 실패했습니다.');
        });
}
