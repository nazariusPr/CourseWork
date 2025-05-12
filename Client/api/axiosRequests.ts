import axiosInstance from "./axiosInstance";
import { AxiosResponse } from "axios";
import { Base64, EmergencyCallDto, MessageDto } from "../types/api";

export async function analyzeMultipart(
  formData: FormData
): Promise<MessageDto> {
  const response: AxiosResponse<MessageDto> = await axiosInstance.post(
    "/analyze/multipart",
    formData,
    {
      headers: {
        "Content-Type": "multipart/form-data",
      },
    }
  );
  return response.data;
}

export async function analyzeBase64(base64: Base64): Promise<MessageDto> {
  const response: AxiosResponse<MessageDto> = await axiosInstance.post(
    "/analyze/base64",
    base64
  );
  return response.data;
}

export async function getLocation(
  lat: number,
  lon: number
): Promise<MessageDto> {
  const response: AxiosResponse<MessageDto> = await axiosInstance.get(
    "/location",
    {
      params: { lat, lon },
    }
  );
  return response.data;
}

export async function getEmergencyCall(
  lat: number,
  lon: number
): Promise<EmergencyCallDto[]> {
  const response: AxiosResponse<EmergencyCallDto[]> = await axiosInstance.get(
    "/emergency-call",
    {
      params: { lat, lon },
    }
  );
  return response.data;
}
