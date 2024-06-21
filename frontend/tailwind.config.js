/** @type {import('tailwindcss').Config} */
export default {
  content: ['./src/**/*.{html,js,svelte,ts}'],
  theme: {
    extend: {
      colors: {
        'airbnb-signature': '#FF5A5F',
        'airbnb-text-bold': '#484848',
        'airbnb-text-normal': '#767676',
      },
      fontFamily: {
        sans: ['Pretendard-Regular'],
        pretendard: ['Pretendard-Regular'],
      },
    },
  },
  plugins: [],
}

