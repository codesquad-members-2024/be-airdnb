/* eslint-disable @next/next/no-img-element */
import Image from "next/image";

export default function GithubProfile() {
  return (
    <div className="github-profile">
      <h2 id="-team1-">👫 Team1 소개 👫</h2>
      <table>
        <thead>
          <tr>
            <th>개발(BE)</th>
            <th>개발(BE)</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>
              <Image
                src="https://avatars.githubusercontent.com/u/87180146"
                alt=""
              />
            </td>
            <td>
              <Image
                src="https://avatars.githubusercontent.com/u/85686722"
                alt=""
              />
            </td>
          </tr>
          <tr>
            <td>
              <a className="link" href="https://github.com/Miensoap">
                @Miensoap
              </a>
            </td>
            <td>
              <a className="link" href="https://github.com/soyesenna">
                @soyesenna
              </a>
            </td>
          </tr>
          <tr>
            <td>Soap</td>
            <td>Senna</td>
          </tr>
        </tbody>
      </table>
    </div>
  );
}
