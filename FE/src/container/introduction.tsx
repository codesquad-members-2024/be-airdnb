import GithubProfile from "@/components/introduction/github_profile";
import Notion from "@/components/introduction/notion";

export default function Introduction() {
  return (
    <div className="introduction">
      <GithubProfile />
      <Notion />
    </div>
  );
}
