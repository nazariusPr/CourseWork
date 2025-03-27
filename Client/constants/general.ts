import { EmergencyNumber } from "../types/general";

// Supported language tags
export const supportedLanguages = ["uk", "en"];

export const emergencyNumbers: Record<EmergencyNumber, string> = {
  police: "102",
  ambulance: "103",
  fire: "101",
  emergencyServices: "112",
};
