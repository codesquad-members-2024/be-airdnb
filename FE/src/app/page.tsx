import GithubProfile from "@/components/github_profile";
import Notion from "@/components/notion";
import Introduction from "@/container/introduction";

export default function Home() {
  return (
    <div className="introduction">
      <div className="title">코드스쿼드 에어비엔비 7팀</div>
      <Introduction />
    </div>
  );
}
