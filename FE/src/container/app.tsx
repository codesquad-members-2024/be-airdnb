"use client";

import GoogleMap from "@/components/map/GoogleMap";
import { Status, Wrapper } from "@googlemaps/react-wrapper";

const render = (status: Status) => {
  switch (status) {
    case Status.LOADING:
      return <>로딩중...</>;
    case Status.FAILURE:
      return <>에러 발생</>;
    case Status.SUCCESS:
      return <GoogleMap />;
  }
};

function App() {
  return (
    <Wrapper
      apiKey={process.env.NEXT_PUBLIC_GOOGLE_MAP_API_KEY as string}
      render={render}
      libraries={["marker"]}
    />
  );
}

export default App;
