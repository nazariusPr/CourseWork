import axios, { AxiosInstance } from "axios";
import { getLanguageCode } from "../utils/general";

const axiosInstance: AxiosInstance = axios.create({
  baseURL: process.env.BASE_URL,
  timeout: 1000000,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
    "Accept-Language": getLanguageCode(),
  },
});

export default axiosInstance;
