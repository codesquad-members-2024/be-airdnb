import GithubProfile from "../components/introduction/github_profile";
import Notion from "../components/introduction/notion";
import SerchBar from "../components/searchBar/SearchBar";
import "../styles/App.css";

export default function IntroductionPage() {
  return (
    <div className="introduction">
      <div className="title">코드스쿼드 에어비엔비 7팀</div>
      <div className="introduction">
        <GithubProfile />
        <Notion />
      </div>
    </div>
  );
}
