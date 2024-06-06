document.addEventListener('DOMContentLoaded', function () {
    const emailVerifyBtn = document.getElementById('email-verify-btn');
    const authCodeContainer = document.getElementById('auth-code-container');
    const passwordContainer = document.getElementById('password-container');
    const signupBtn = document.getElementById('signup-btn');

    function validateEmail(email) {
        var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    }

    function validatePassword(password) {
        return password.trim().length >= 4;
    }

    function validateAuthCode(authCode) {
        var authCodeRegex = /^\d{6}$/;
        return authCodeRegex.test(authCode);
    }

    function handleEmailVerifyButtonClick() {
        var adminId = document.getElementById('admin-id');

        if (adminId.value.trim() === "") {
            alert("아이디는 공백이 될 수 없습니다.");
            return;
        }

        if (!validateEmail(adminId.value)) {
            alert("유효한 이메일 주소를 입력하세요.");
            adminId.value = "";
            adminId.focus();
            return;
        }

        adminId.disabled = true;
        emailVerifyBtn.disabled = true;

        var data = {
            adminId: adminId.value,
        };

        fetch('/admin/send-mail', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                console.log(response);
            })
            .catch(error => {
                console.error('Error:', error);
            });

        authCodeContainer.classList.remove('hidden');
        passwordContainer.classList.remove('hidden');
        signupBtn.classList.remove('hidden');
    }

    function handleSignupButtonClick() {
        var adminId = document.getElementById('admin-id');
        var authCode = document.getElementById('auth-code');
        var password = document.getElementById('password');

        if (adminId.value.trim() === "" || password.value.trim() === "") {
            alert("아이디, 비밀번호는 공백이 될 수 없습니다.");
            return;
        }

        if (!validateEmail(adminId.value)) {
            alert("유효한 이메일 주소를 입력하세요.");
            adminId.value = "";
            adminId.focus();
            return;
        }

        if (!validatePassword(password.value)) {
            alert("비밀번호는 4자 이상이어야 합니다.");
            password.value = "";
            return;
        }

        if (!validateAuthCode(authCode.value)) {
            alert("인증번호는 6자리 숫자여야 합니다.");
            authCode.value = "";
            authCode.focus();
            return;
        }

        var data = {
            adminId: adminId.value,
            authCode: authCode.value,
            password: password.value,
        };

        fetch('/admin', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                console.log(response);
                if (response.status === 200) {
                    alert("회원가입 성공!");
                    window.location.href = '/admin/login'; // 상태 코드가 200이면 /admin/login 페이지로 이동
                }
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }

    emailVerifyBtn.addEventListener('click', handleEmailVerifyButtonClick);
    signupBtn.addEventListener('click', handleSignupButtonClick);
});
