export default function GithubProfile() {
  return (
    <div className="github-profile">
      <h2 id="-team1-">👫 Team7 소개 👫</h2>
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
              <img
                src="https://avatars.githubusercontent.com/u/87180146"
                width={160}
                height={160}
                alt=""
              />
            </td>
            <td>
              <img
                src="https://avatars.githubusercontent.com/u/85686722"
                width={160}
                height={160}
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
