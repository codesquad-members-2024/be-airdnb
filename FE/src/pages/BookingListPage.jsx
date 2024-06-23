import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchBookings } from "../api/BookingAPI";
import styles from "../styles/BookingListPage.module.css";

const BookingListPage = () => {
  const [bookings, setBookings] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const getBookings = async () => {
      try {
        const data = await fetchBookings();
        setBookings(data);
      } catch (error) {
        console.error("Error fetching bookings:", error);
      }
    };

    getBookings();
  }, [navigate]);

  return (
    <div className={styles.page}>
      <div className={styles.title}>내 예약</div>
      <div className={styles.container}>
        {bookings.length > 0 ? (
          <ul>
            {bookings.map((booking) => (
              <li key={booking.id}>
                <p>예약자: {booking.bookerName}</p>
                <p>체크인: {booking.checkIn}</p>
                <p>체크아웃: {booking.checkOut}</p>
                <p>인원: {booking.headCount}명</p>
                <p>상태: {booking.status}</p>
                <p>결제 상태: {booking.payment.status}</p>
                <p>총 요금: ₩{booking.payment.totalPrice}</p>
              </li>
            ))}
          </ul>
        ) : (
          <p>예약 내역이 없습니다.</p>
        )}
      </div>
    </div>
  );
};

export default BookingListPage;
