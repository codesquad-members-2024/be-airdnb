document.addEventListener("DOMContentLoaded", () => {
    const buttonContainer = document.querySelector('.top-button-container');

    const buttons = [
        { text: '숙소 생성 페이지', url: '/accommodation/create' },
        { text: '숙소 유형 페이지', url: '/accommodation-type' },
        { text: '숙소 방 유형 페이지', url: '/accommodation-room-type' }
    ];

    buttons.forEach(button => {
        const btn = document.createElement('button');
        btn.textContent = button.text;
        btn.onclick = () => {
            window.location.href = button.url;
        };
        buttonContainer.appendChild(btn);
    });
});
