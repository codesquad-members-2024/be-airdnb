document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    // You can add your login logic here, e.g., AJAX call to your server
    console.log("Email:", email);
    console.log("Password:", password);

    // For demonstration purposes, we simply show an alert
    alert("로그인 시도: " + email);
});
