import { Platform, Alert } from "react-native";
import { supportedLanguages } from "../constants/general";
import { LanguageCode, Coordinate } from "../types/general";
import * as Localization from "expo-localization";
import * as Location from "expo-location";
import * as Speech from "expo-speech";
import * as Haptics from "expo-haptics";

export const getLanguageCode = (): LanguageCode => {
  const locale = Localization.getLocales()[0].languageTag;
  const languageCode = locale.split("-")[0] as LanguageCode;
  return supportedLanguages.includes(languageCode) ? languageCode : "en";
};

export const speak = async (text: string) => {
  try {
    speakStop();
    const languageCode = getLanguageCode();

    const voices = await Speech.getAvailableVoicesAsync();
    let bestVoice = undefined;

    if (voices.length > 0) {
      bestVoice = voices.find(
        (voice) =>
          voice.language.includes(languageCode) &&
          (voice.quality === Speech.VoiceQuality.Enhanced ||
            voice.name.includes("premium") ||
            voice.name.includes("natural"))
      );
    }

    const params = {
      language: languageCode,
      voice: bestVoice?.identifier,
      pitch: 1.05,
      rate: 0.9,
      volume: 0.9,
    };

    const sentences = text.split(/(?<=[.!?])\s+/);
    for (let i = 0; i < sentences.length; i++) {
      await Speech.speak(sentences[i], params);
      if (i < sentences.length - 1) {
        await new Promise((resolve) => setTimeout(resolve, 150));
      }
    }
  } catch (error) {
    console.error("Speech error:", error);
  }
};

export const speakStop = async () => {
  await Speech.stop();
};

export const getCurrentCoordinate = async (): Promise<Coordinate> => {
  const { status } = await Location.requestForegroundPermissionsAsync();
  if (status !== "granted") {
    Alert.alert("Permission denied", "Location access is required.");
    return { latitude: 0, longitude: 0 };
  }

  const loc = await Location.getCurrentPositionAsync({});
  return {
    latitude: loc.coords.latitude,
    longitude: loc.coords.longitude,
  };
};

export const lightImpactFeedback = () => {
  Haptics.impactAsync(Haptics.ImpactFeedbackStyle.Light);
};

export const heavyImpactFeedback = () => {
  Haptics.impactAsync(Haptics.ImpactFeedbackStyle.Heavy);
};
