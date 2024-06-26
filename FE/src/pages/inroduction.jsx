import GithubProfile from "../components/global/introduction/github_profile";
import Notion from "../components/global/introduction/notion";
import "../styles/App.css";

export default function IntroductionPage() {
  return (
    <div className="introduction-page">
      <div className="title">코드스쿼드 에어비엔비 7팀</div>
      <div className="introduction">
        <GithubProfile />
        <Notion />
      </div>
    </div>
  );
}
