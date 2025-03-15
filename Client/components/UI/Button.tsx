import React from "react";
import {
  Pressable,
  Text,
  TextStyle,
  ViewStyle,
  StyleSheet,
  Dimensions,
} from "react-native";
import { colors } from "../../constants/colors";

const { width } = Dimensions.get("window");

type ButtonProps = {
  children: string;
  onPress: () => void;
  containerStyle?: ViewStyle;
  textStyle?: TextStyle;
  disabled?: boolean;
};

const Button = ({
  children,
  onPress,
  containerStyle,
  textStyle,
  disabled,
}: ButtonProps) => {
  return (
    <Pressable
      style={({ pressed }) => [
        styles.button,
        containerStyle,
        disabled && styles.disabled,
        pressed && styles.pressed,
      ]}
      onPress={onPress}
      disabled={disabled}
    >
      <Text style={[styles.text, textStyle]}>{children}</Text>
    </Pressable>
  );
};

const styles = StyleSheet.create({
  button: {
    backgroundColor: colors.blue,
    paddingVertical: width * 0.03,
    paddingHorizontal: width * 0.05,
    borderRadius: 5,
    alignItems: "center",
    justifyContent: "center",
  },
  text: {
    color: colors.white,
    fontSize: width * 0.08,
    fontWeight: "bold",
  },
  disabled: {
    backgroundColor: colors.lightGray,
  },
  pressed: {
    opacity: 0.6,
  },
});

export default Button;
