import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import styles from '/src/styles/BookingListPage.module.css';

const BookingListPage = () => {
  const [bookings, setBookings] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchBookings = async () => {
      try {
        const response = await axios.get('https://squadbnb.site/api/booking/my-bookings', {
          headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
          }
        });
        setBookings(response.data);
      } catch (error) {
        console.error('Error fetching bookings:', error);
      }
    };

    fetchBookings();
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