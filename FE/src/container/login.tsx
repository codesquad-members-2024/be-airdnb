"use client";
import GithubButton from "@/components/github_button";
import GoogleButton from "@/components/google_button";
import KakaoButton from "@/components/kakao_button";


const LoginContainer = () => {
  return (
    <div>
      {/* <h2>Login to continue</h2> */}
        <GithubButton />
        <GoogleButton />
        <KakaoButton />
      {/* <div><GoogleButton /></div> */}
    </div>
  );
};

export default LoginContainer;
