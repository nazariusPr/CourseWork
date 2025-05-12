export type ScreenProps = {
  playSound: () => void;
  stopSound: () => void;
};

export type LoadingProps = {
  setLoading: (loading: boolean) => void;
};

export type Coordinate = { latitude: number; longitude: number };

export type Screen = "LocationAssist" | "VisionAssist" | "EmergencyCall";

export type LanguageCode = "uk" | "en";

export type EmergencyService =
  | "police"
  | "ambulance"
  | "fire"
  | "emergencyService";
