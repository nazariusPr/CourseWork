import { useTranslation } from "react-i18next";
import { Pressable, StyleSheet } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { EmergencyService } from "../../types/general";
import { getResponsiveSize } from "../../utils/responsive";
import { colors } from "../../constants/theme";
import { speak } from "../../utils/general";
import useDoublePress from "../../hooks/useDoublePress";

type EmergencyCallButtonProps = {
  type: EmergencyService;
  onSecondPress: () => void;
};

function EmergencyCallButton({
  type,
  onSecondPress,
}: EmergencyCallButtonProps) {
  const { t } = useTranslation();
  const getIcon = () => {
    switch (type) {
      case "police":
        return "shield-checkmark-outline";
      case "ambulance":
        return "heart-outline";
      case "fire":
        return "flame-outline";
      default:
        return "call-outline";
    }
  };

  const onFirstPress = () => {
    const description = t(`EmergencyCall.services.${type}`);
    speak(description);
  };

  const handlePress = useDoublePress({
    onFirstPress,
    onSecondPress,
  });

  return (
    <Pressable
      key={type}
      style={({ pressed }) => [
        styles.circleButton,
        { opacity: pressed ? 0.7 : 1 },
      ]}
      onPress={handlePress}
    >
      <Ionicons
        name={getIcon()}
        size={getResponsiveSize(60)}
        color={colors.white}
      />
    </Pressable>
  );
}

const styles = StyleSheet.create({
  circleButton: {
    width: "42%",
    height: getResponsiveSize(145),
    borderRadius: getResponsiveSize(45),
    backgroundColor: colors.lightBlue,
    justifyContent: "center",
    alignItems: "center",
    margin: getResponsiveSize(10),
    elevation: getResponsiveSize(5),
  },
});

export default EmergencyCallButton;
