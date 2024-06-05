"use client";
import GithubButton from "@/components/login/github_button";
import GoogleButton from "@/components/login/google_button";

const LoginContainer = () => {
  return (
    <div>
      {/* <h2>Login to continue</h2> */}
      <GithubButton />
      <GoogleButton />
      {/* <div><GoogleButton /></div> */}
    </div>
  );
};

export default LoginContainer;
