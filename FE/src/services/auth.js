import axios from "axios";

export const handleOAuth = async (code, state, provider) => {
  try {
    const response = await axios.get(
      `https://squadbnb.site/api/login/oauth2/code/${String(
        provider.provider
      )}?code=${code}&state=${state}`
    );
    const data = response.data; // 응답 데이터
    localStorage.setItem("token", data.token);
    localStorage.setItem("userId", data.userId);
    localStorage.setItem("userName", data.userName);
    localStorage.setItem("profileImage", data.profileImage);

    alert("로그인 성공: " + data.userName + "님 환영합니다!");
  } catch (error) {
    alert("로그인 실패: " + error.response.data.message);
  }
  // window.location.reload();
  // window.location.replace("/");
};

export function logout() {
  // 로컬 스토리지에서 사용자 정보 삭제
  const userName = localStorage.getItem("userName");

  localStorage.removeItem("token");
  localStorage.removeItem("userName");
  localStorage.removeItem("userId");

  alert(userName + "님 안녕히가세요!");

  // 서버에 로그아웃 요청 보내기
  fetch("https://squadbnb.site/api/logout", {
    method: "POST",
  }).then((response) => {
    if (response.ok) {
      console.log("Logged out successfully");
    } else {
      console.error("Error during logout");
    }
    // window.location.reload();
    // window.location.replace("/");
  });
}
