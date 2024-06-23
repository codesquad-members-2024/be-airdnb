// src/api/axiosInstance.js
import axios from "axios";

const token = localStorage.getItem("token");

export const authInstance = axios.create({
  baseURL: `https://${import.meta.env.VITE_API_BASE_URL}`, // 기본 URL 설정
  headers: {
    Authorization: token ? `Bearer ${token}` : "",
  },
});

export const baseInstance = axios.create({
  baseURL: `https://${import.meta.env.VITE_API_BASE_URL}`, // 기본 URL 설정
});
