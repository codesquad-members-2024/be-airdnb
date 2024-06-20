import { BrowserRouter, Route, Routes } from "react-router-dom";
import Layout from "./components/global/layout";
import IntroductionPage from "./pages/inroduction";
import MainPage from "./pages/main";
import RedirectPage from "./services/redirect.jsx";
import SearchPage from "./pages/SearchPage.jsx";
import AccommodationDetail from "./pages/AccommodationDetail";
import BookingListPage from "./pages/BookingListPage";

const App = () => {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route index element={<MainPage />} />
            <Route path="search" element={<SearchPage />} />
            <Route path="accommodation-detail/:id" element={<AccommodationDetail />}/>
            <Route path="/my-bookings" element={<BookingListPage />} />
          </Route>

          {/*-- ETC --*/}
          <Route
            path="/oauth/redirected/github"
            element={<RedirectPage provider="github" />}
          />
          <Route
            path="/oauth/redirected/google"
            element={<RedirectPage provider="google" />}
          />
          <Route path="/intro" element={<IntroductionPage />} />
        </Routes>
      </BrowserRouter>
    </div>
  );
};

export default App;