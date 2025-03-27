import axiosInstance from "./axiosInstance";
import { Base64 } from "../types/api";

export function analyzeMultipart(formData: FormData) {
  return axiosInstance.post("/analyze/multipart", formData, {
    headers: {
      "Content-Type": "multipart/form-data",
    },
  });
}

export function analyzeBase64(base64: Base64) {
  return axiosInstance.post("/analyze/base64", base64);
}

export function getLocation(lat: number, lon: number) {
  return axiosInstance.get("/location", {
    params: {
      lat,
      lon,
    },
  });
}
