/** @type {import('tailwindcss').Config} */
export default {
  content: ["./src/**/*.{html,js,svelte,ts}"],
  theme: {
    extend: {
      colors: {
        primary: "#E84C60", // 메인 색상
        secondary: "#118917",
      },
      fontFamily: {
        sans: ["Noto Sans KR", "Noto Sans KR 500"],
        pretendard: ["Pretendard-Regular"],
      },
      keyframes: {
        slidein: {
          from: {
            opacity: "0",
            transform: "translateY(-10px)",
          },
          to: {
            opacity: "1",
            transform: "translateY(0)",
          },
        },
      },
      animation: {
        slidein: "slidein 0.5s",
        slidein300: "slidein 1s ease 300ms",
        slidein500: "slidein 1s ease 500ms",
        slidein700: "slidein 1s ease 700ms",
      },
      screens: {
        "2xl": "1700px",
      },
    },
  },
  plugins: [require("tailwindcss-animated")],
};
