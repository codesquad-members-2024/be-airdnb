interface LoginResponse {
  token: string;
  userId: number; // 화면에 보여줄때만사용 절대이걸로요청 xx 토큰으로요청
  userName: string;
}
