export type ScreenProps = {
  playSound: () => void;
  stopSound: () => void;
};

export type Coordinate = { latitude: number; longitude: number };

export type Screen = "LocationAssist" | "VisionAssist";

export type LanguageCode = "uk" | "en";
