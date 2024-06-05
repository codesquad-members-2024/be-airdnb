import React from "react";
import { AccommodationListCard } from "../types";
import styles from "./AccommodationCard.module.css";

interface AccommodationCardProps {
  accommodation: AccommodationListCard;
}

const AccommodationCard: React.FC<AccommodationCardProps> = ({
  accommodation,
}) => {
  return (
    <div className={styles.card}>
      {accommodation.discount && (
        <div className={styles.discount}>{accommodation.discount}</div>
      )}
      <img
        src={accommodation.image}
        alt={accommodation.title}
        className={styles.image}
      />
      <div className={styles.info}>
        <h3>{accommodation.title}</h3>
        <p className={styles.price}>{accommodation.price}</p>
        <p className={styles.totalPrice}>{accommodation.totalPrice}</p>
        {accommodation.rating && (
          <p className={styles.rating}>
            ⭐ {accommodation.rating} ({accommodation.reviews})
          </p>
        )}
      </div>
    </div>
  );
};

export default AccommodationCard;
