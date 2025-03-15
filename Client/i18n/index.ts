import i18n, { InitOptions, LanguageDetectorModule } from "i18next";
import { initReactI18next } from "react-i18next";
import { getLanguageCode } from "../utils/general";
import translationEN from "../locales/en/translation.json";
import translationUK from "../locales/uk/translation.json";

const resources = {
  en: {
    translation: translationEN,
  },
  uk: {
    translation: translationUK,
  },
};

const languageDetector: LanguageDetectorModule = {
  type: "languageDetector",
  detect: getLanguageCode,
  init: () => {},
  cacheUserLanguage: () => {},
};

const initOptions: InitOptions = {
  resources,
  fallbackLng: "en",
  interpolation: {
    escapeValue: false,
  },
};

i18n.use(initReactI18next).use(languageDetector).init(initOptions);

export default i18n;
