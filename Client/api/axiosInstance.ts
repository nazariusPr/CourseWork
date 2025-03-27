import axios, { AxiosInstance } from "axios";
import { getLanguageCode } from "../utils/general";

const axiosInstance: AxiosInstance = axios.create({
  baseURL: "http://192.168.110.182:9090/api/v1",
  timeout: 0,
  headers: {
    "Content-Type": "application/json",
    Accept: "application/json",
    "Accept-Language": getLanguageCode(),
  },
});

export default axiosInstance;
