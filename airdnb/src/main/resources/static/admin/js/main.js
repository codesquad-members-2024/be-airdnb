function validateEmail(email) {
    var emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
}

function validatePassword(password) {
    return password.trim().length >= 4;
}

function handleSignupButtonClick() {
    var signupBtn = document.getElementById('signup-btn');
    var authCodeContainer = document.getElementById('auth-code-container');
    var nextBtn = document.getElementById('next-btn');
    var adminId = document.getElementById('admin-id');
    var password = document.getElementById('password');

    // Check if ID and password are not empty
    if (adminId.value.trim() === "" || password.value.trim() === "") {
        alert("아이디, 비밀번호는 공백이 될 수 없습니다.");
        return;
    }

    // Check if the ID is in email format
    if (!validateEmail(adminId.value)) {
        alert("유효한 이메일 주소를 입력하세요.");
        adminId.value = "";
        adminId.focus();
        return;
    }

    // Check if the password length is less than 4
    if (!validatePassword(password.value)) {
        alert("비밀번호는 4자 이상이어야 합니다.");
        password.value = "";
        return;
    }

    // Disable the ID and password fields
    adminId.disabled = true;

    // Prepare data to be sent
    var data = {
        adminId: adminId.value,
    };

    // Send data to the server
    fetch('/admin/send-mail', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
        .then(response => {
            // Handle response
            // For example, show a success message or redirect to another page
            console.log(response);
        })
        .catch(error => {
            console.error('Error:', error);
            // Handle error
        });

    // Hide the signup button and show the auth code input and next button
    signupBtn.classList.add('hidden');
    authCodeContainer.classList.remove('hidden');
    nextBtn.classList.remove('hidden');
}

function handleNextButtonClick() {
    // Add your logic for the next button here
    // This function will be executed when the next button is clicked
}

document.getElementById('signup-btn').addEventListener('click', handleSignupButtonClick);
document.getElementById('next-btn').addEventListener('click', handleNextButtonClick);
