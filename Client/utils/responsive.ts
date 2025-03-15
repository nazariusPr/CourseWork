import { Dimensions } from "react-native";

const { width } = Dimensions.get("window");

export const getResponsiveSize = (baseSize: number) => {
  if (width > 800) return baseSize * 1.2;
  if (width < 360) return baseSize * 0.9;
  return baseSize;
};
