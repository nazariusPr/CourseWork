import { UserType } from "../enums/user";

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
