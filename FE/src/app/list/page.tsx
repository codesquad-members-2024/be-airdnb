"use client";
import React, { useRef } from "react";
import { Wrapper, Status } from "@googlemaps/react-wrapper";

import { AccommodationListCard } from "@/components/types";
import accommodationsData from "@/data/accommodations.json";
import AccommodationCard from "@/components/accommodation/AccommodationCard";
import styles from "@/styles/Home.module.css";
import GoogleMap from "@/components/map/GoogleMap";

const render = (status: Status): React.ReactElement => {
  if (status === Status.LOADING) return <div>Loading...</div>;
  if (status === Status.FAILURE) return <div>Failed to load map</div>;
  return <div>Loading...</div>;
};

const Home: React.FC = () => {
  const accommodations: AccommodationListCard[] = accommodationsData;
  const mapRef = useRef<HTMLDivElement | null>(null);

  return (
    <div className={styles.container}>
      <div className={styles.list}>
        {accommodations.map((accommodation) => (
          <AccommodationCard
            key={accommodation.id}
            accommodation={accommodation}
          />
        ))}
      </div>
      <div className={styles.map} ref={mapRef}>
        <Wrapper
          apiKey={process.env.NEXT_PUBLIC_GOOGLE_MAP_API_KEY as string}
          render={render}
          libraries={["marker"]}
        >
          <GoogleMap mapRef={mapRef} />
        </Wrapper>
      </div>
    </div>
  );
};

export default Home;
