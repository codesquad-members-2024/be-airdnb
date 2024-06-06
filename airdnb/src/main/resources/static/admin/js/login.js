document.addEventListener('DOMContentLoaded', function() {
    const loginBtn = document.getElementById('login-btn');

    loginBtn.addEventListener('click', function() {
        const adminId = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        const data = {
            adminId: adminId,
            password: password
        };

        fetch('/admin/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (response.status === 200) {
                    // 로그인 성공
                    window.location.href = '/main';
                } else {
                    // 로그인 실패
                    alert("로그인 실패. 이메일 또는 비밀번호를 확인하세요.");
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    });
});
