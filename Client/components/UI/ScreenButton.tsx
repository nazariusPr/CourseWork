import { Pressable, StyleSheet } from "react-native";
import useDoublePress from "../../hooks/useDoublePress";

type ScreenButtonProps = {
  beforeDoublePress?: () => void;
  onDoublePress: () => void;
};

function ScreenButton({
  beforeDoublePress = () => {},
  onDoublePress,
}: ScreenButtonProps) {
  const handlePress = useDoublePress(() => {
    beforeDoublePress();
    onDoublePress();
  });

  return (
    <Pressable
      style={styles.button}
      accessibilityLabel="Double-tap to trigger action"
      onPress={handlePress}
    ></Pressable>
  );
}

const styles = StyleSheet.create({
  button: {
    position: "absolute",
    top: 0,
    left: 0,
    right: 0,
    width: "100%",
    height: "100%",
    backgroundColor: "rgba(0, 0, 0, 0)",
    zIndex: 100,
  },
});

export default ScreenButton;
