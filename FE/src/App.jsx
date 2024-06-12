import { BrowserRouter, Route, Routes } from "react-router-dom";
import Layout from "./components/global/layout";
import IntroductionPage from "./pages/inroduction";
import LoginPage from "./pages/loginPage";
import RedirectPage from "./services/redirect.jsx";

const App = () => {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<IntroductionPage />} />
            <Route path="/login" element={<LoginPage />} />
            <Route
              path="/oauth/redirected/github"
              element={<RedirectPage provider="github" />}
            />
            <Route
              path="/oauth/redirected/google"
              element={<RedirectPage provider="google" />}
            />
          </Route>
        </Routes>
        <RedirectPage provider="github" />
        <RedirectPage provider="google" />
      </BrowserRouter>
    </div>
  );
};

export default App;
