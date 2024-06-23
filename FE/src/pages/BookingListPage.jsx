import PropTypes from "prop-types";
import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { fetchBookings } from "../api/BookingAPI";
import styles from "/src/styles/BookingListPage.module.css";

const BookingListPage = () => {
  const [bookings, setBookings] = useState([]);
  const navigate = useNavigate();
  const token = localStorage.getItem("token"); // 토큰을 로컬 스토리지에서 가져옴

  useEffect(() => {
    const getBookings = async () => {
      try {
        const data = await fetchBookings(token);
        setBookings(data);
      } catch (error) {
        console.error("Error fetching bookings:", error);
      }
    };

    getBookings();
  }, [token, navigate]);

  return (
    <div className={styles.page}>
      <h2>내 예약</h2>
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
  );
};

BookingListPage.propTypes = {
  token: PropTypes.string,
};

export default BookingListPage;
