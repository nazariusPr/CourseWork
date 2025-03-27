import { Pressable, StyleSheet } from "react-native";
import { Ionicons } from "@expo/vector-icons";
import { EmergencyNumber } from "../../types/general";
import { getResponsiveSize } from "../../utils/responsive";
import { colors } from "../../constants/colors";

type EmergencyCallButtonProps = {
  type: EmergencyNumber;
  onPress: () => void;
};

function EmergencyCallButton({ type, onPress }: EmergencyCallButtonProps) {
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

  return (
    <Pressable
      key={type}
      style={({ pressed }) => [
        styles.circleButton,
        { opacity: pressed ? 0.7 : 1 },
      ]}
      onPress={onPress}
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
