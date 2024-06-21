import { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import styles from "/src/styles/nav.module.css";
import logo from "/images/logo.png"; // 로고 이미지 경로
import menuIcon from "/images/menu.png"; // 메뉴 아이콘 이미지 경로
import placeholderIcon from "/images/placeholder.jpg"; // 프로필 아이콘 이미지 경로

import LoginModal from "/src/components/login/loginModal.jsx";
import { logout } from "../../services/auth";

const Nav = () => {
  const [menuOpen, setMenuOpen] = useState(false);
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [activeItem, setActiveItem] = useState("");
  const [showModal, setShowModal] = useState(false);
  const [userName, setUserName] = useState("");
  const [profileImage, setProfileImage] = useState(placeholderIcon);
  const navigate = useNavigate();

  const token = localStorage.getItem("token");

  useEffect(() => {
    if (token) {
      const name = localStorage.getItem("userName");
      const image = localStorage.getItem("profileImage");
      if (name) {
        setUserName(name);
      }
      if (image) {
        setProfileImage(image);
        setIsLoggedIn(true);
      }
    }
  }, [token]);

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  const toggleLogin = () => {
    setShowModal(true);
  };

  const handleCloseModal = () => {
    setShowModal(false);
  };

  const handleItemClick = (item) => {
    setActiveItem(item);
  };

  const handleLogout = () => {
    logout();
    setIsLoggedIn(false);
    setUserName("");
    setProfileImage(placeholderIcon);
  };

  const goToBookings = () => {
    navigate('/my-bookings');
  };

  return (
    <div className="App">
      <nav className={styles.navbar}>
        <Link to="/" className={styles.navLogo}>
          <img src={logo} alt="Logo" />
        </Link>
        <div className={styles.navItems}>
          <a
            href="#"
            className={`${styles.navItem} ${
              activeItem === "숙소" ? styles.active : ""
            }`}
            onClick={() => handleItemClick("숙소")}
          >
            숙소
          </a>
          <a
            href="#"
            className={`${styles.navItem} ${
              activeItem === "체험" ? styles.active : ""
            }`}
            onClick={() => handleItemClick("체험")}
          >
            체험
          </a>
          <a
            href="#"
            className={`${styles.navItem} ${
              activeItem === "온라인 체험" ? styles.active : ""
            }`}
            onClick={() => handleItemClick("온라인 체험")}
          >
            온라인 체험
          </a>
        </div>
        <div className={styles.navMenu}>
          <button className={styles.navMenuButton} onClick={toggleMenu}>
            <img src={menuIcon} alt="Menu" />
            <img src={profileImage} alt="Profile" />
          </button>
          <div
            className={`${styles.navMenuContent} ${
              menuOpen ? styles.show : ""
            }`}
          >
            {isLoggedIn ? (
              <>
                <div className={styles.navMenuItem} onClick={goToBookings}>내 예약</div>
                <div className={styles.navMenuItem}>예약 취소</div>
                <div className={styles.navMenuItem}>위시리스트</div>
                <div className={styles.navMenuItem} onClick={handleLogout}>
                  로그아웃
                </div>
              </>
            ) : (
              <div className={styles.navMenuItem} onClick={toggleLogin}>
                로그인
              </div>
            )}
          </div>
        </div>
      </nav>
      <LoginModal show={showModal} onClose={handleCloseModal} />
    </div>
  );
};

export default Nav;