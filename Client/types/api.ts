import { UserType } from "../enums/user";
import { EmergencyService } from "./general";

export type AuthenticationDto = {
  email: string;
  password: string;
};

export type RegisterDto = {
  userType: UserType;
} & AuthenticationDto;

export type CodeDto = {
  code: string;
};

export type GoogleDto = {
  token: string;
  userType?: UserType;
};

export type Base64 = {
  base64: string;
};

export type MessageDto = {
  message: string;
};

export type EmergencyCallDto = {
  id: string;
  phoneNumber: string;
  emergencyService: EmergencyService;
  country: string;
};
