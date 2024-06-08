export function logout() {
  // 로컬 스토리지에서 사용자 정보 삭제
  localStorage.removeItem("token");
  localStorage.removeItem("userName");
  localStorage.removeItem("userId");

  // 서버에 로그아웃 요청 보내기
  fetch("https://squadbnb.site/api/logout", {
    method: "POST",
  }).then((response) => {
    if (response.ok) {
      // 로그아웃 성공 시 추가 작업 (필요한 경우)
      console.log("Logged out successfully");
    } else {
      // 로그아웃 실패 시 처리 (필요한 경우)
      console.error("Error during logout");
    }
    window.location.reload();
    window.location.replace("/");
  });
}
