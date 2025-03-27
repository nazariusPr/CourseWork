import { Screen } from "../types/general";

export const soundMap: Record<string, Record<Screen, any>> = {
  en: {
    LocationAssist: require("../assets/en/location.wav"),
    VisionAssist: require("../assets/en/vision.wav"),
    EmergencyCall: require("../assets/en/emergency.wav"),
  },
  uk: {
    LocationAssist: require("../assets/uk/location.wav"),
    VisionAssist: require("../assets/uk/vision.wav"),
    EmergencyCall: require("../assets/uk/emergency.wav"),
  },
} as const;
