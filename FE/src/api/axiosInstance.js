import axios from "axios";

export const authInstance = axios.create({
  baseURL: `https://${import.meta.env.VITE_API_BASE_URL}`, // 기본 URL 설정
});

authInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export const baseInstance = axios.create({
  baseURL: `https://${import.meta.env.VITE_API_BASE_URL}`, // 기본 URL 설정
});
