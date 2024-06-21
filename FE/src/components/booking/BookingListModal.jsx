import React from 'react';
import PropTypes from 'prop-types';
import styles from '/src/styles/BookingListModal.module.css';

const BookingListModal = ({ show, onClose, bookings }) => {
  if (!show) return null;

  return (
    <div className={styles.modalOverlay}>
      <div className={styles.modal}>
        <button className={styles.closeButton} onClick={onClose}>×</button>
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
    </div>
  );
};

BookingListModal.propTypes = {
  show: PropTypes.bool.isRequired,
  onClose: PropTypes.func.isRequired,
  bookings: PropTypes.array.isRequired,
};

export default BookingListModal;