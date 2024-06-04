"use client";
import GithubButton from "@/components/github_button";
import GoogleButton from "@/components/google_button";

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
