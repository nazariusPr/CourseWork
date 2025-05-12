import axios, { AxiosInstance } from "axios";
import { getLanguageCode } from "../utils/general";

const axiosInstance: AxiosInstance = axios.create({
  baseURL: process.env.EXPO_PUBLIC_SERVER_URL,
  timeout: 0,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
    "Accept-Language": getLanguageCode(),
  },
});

export default axiosInstance;
