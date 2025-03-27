import { Platform } from "react-native";
import { supportedLanguages } from "../constants/general";
import { LanguageCode } from "../types/general";
import * as Localization from "expo-localization";
import * as Speech from "expo-speech";

export const getLanguageCode = (): LanguageCode => {
  const locale = Localization.getLocales()[0].languageTag;
  const languageCode = locale.split("-")[0] as LanguageCode;

  // return supportedLanguages.includes(languageCode) ? languageCode : "en";
  return "en";
};

export const speak = async (text: string) => {
  await Speech.stop();
  const languageCode = getLanguageCode();
  const voices: Record<LanguageCode, string> = {
    en:
      Platform.OS === "ios"
        ? "com.apple.ttsbundle.siri_female_en-GB"
        : "en-US-language",
    uk:
      Platform.OS === "ios"
        ? "com.apple.ttsbundle.siri_female_uk-UA_premium"
        : "uk-UA-language",
  };

  Speech.speak(text, {
    language: languageCode,
    pitch: 1.15,
    rate: 0.95,
    // voice: voices[languageCode],
  });
};
