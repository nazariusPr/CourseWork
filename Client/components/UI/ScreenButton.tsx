import { Pressable, StyleSheet } from "react-native";
import { zIndexes } from "../../constants/theme";
import useDoublePress from "../../hooks/useDoublePress";

type ScreenButtonProps = {
  beforeDoublePress?: () => void;
  onFirstPress?: () => void;
  onDoublePress: () => void;
};

function ScreenButton({
  beforeDoublePress = () => {},
  onFirstPress = () => {},
  onDoublePress,
}: ScreenButtonProps) {
  const callback = () => {
    beforeDoublePress();
    onDoublePress();
  };
  const handlePress = useDoublePress({
    onFirstPress,
    onSecondPress: callback,
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
    zIndex: zIndexes.modal,
  },
});

export default ScreenButton;
