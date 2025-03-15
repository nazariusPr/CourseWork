import { useState } from "react";
import {
  View,
  Text,
  StyleSheet,
  Pressable,
  Dimensions,
  ViewStyle,
  TextStyle,
} from "react-native";
import Ionicons from "react-native-vector-icons/Ionicons";
import { colors } from "../../constants/colors";

const { width } = Dimensions.get("window");

type CheckboxProps = {
  children: string;
  onCheck: () => void;
  onUncheck: () => void;
  containerStyle?: ViewStyle;
  labelStyle?: TextStyle;
};

const Checkbox = ({
  children,
  onCheck,
  onUncheck,
  containerStyle,
  labelStyle,
}: CheckboxProps) => {
  const [checked, setChecked] = useState(false);

  const toggleCheckbox = () => {
    checked ? onUncheck() : onCheck();
    setChecked(!checked);
  };

  return (
    <View style={[styles.container, containerStyle]}>
      <Pressable onPress={toggleCheckbox} style={styles.checkboxContainer}>
        <View style={[styles.checkbox, checked && styles.checked]}>
          {checked && (
            <Ionicons
              name="checkmark"
              size={width * 0.08}
              color={colors.white}
              style={styles.checkmark}
            />
          )}
        </View>
        <Text style={[styles.label, labelStyle]}>{children}</Text>
      </Pressable>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flexDirection: "row",
    alignItems: "center",
    marginHorizontal: width * 0.03,
  },
  checkboxContainer: {
    flexDirection: "row",
    alignItems: "center",
  },
  checkbox: {
    width: width * 0.08,
    height: width * 0.08,
    borderWidth: 2,
    borderColor: colors.blue,
    borderRadius: width * 0.02,
    marginRight: width * 0.03,
    justifyContent: "center",
    alignItems: "center",
  },
  checked: {
    backgroundColor: colors.blue,
  },
  checkmark: {
    position: "absolute",
    top: `${width * 0.08 * -0.25}%`,
    left: `${width * 0.08 * -0.15}%`,
  },
  label: {
    fontSize: width * 0.08,
    color: colors.blueGray,
  },
});

export default Checkbox;
