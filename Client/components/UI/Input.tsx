import {
  View,
  TextInput,
  Text,
  StyleSheet,
  ViewStyle,
  TextStyle,
  TextInputProps,
  Dimensions,
} from "react-native";
import { colors } from "../../constants/theme";

const { width } = Dimensions.get("window");

type InputProps = {
  label?: string;
  errorMessage?: string;
  containerStyle?: ViewStyle;
  labelStyle?: TextStyle;
  errorStyle?: TextStyle;
  icon?: React.ReactNode;
} & TextInputProps;

const Input = ({
  label,
  value,
  onChangeText,
  errorMessage,
  containerStyle,
  labelStyle,
  errorStyle,
  icon,
  ...rest
}: InputProps) => {
  return (
    <View style={[styles.container, containerStyle]}>
      {label && <Text style={[styles.label, labelStyle]}>{label}</Text>}
      <View style={[styles.inputContainer, errorMessage && styles.inputError]}>
        {icon && <View style={styles.icon}>{icon}</View>}
        <TextInput
          value={value}
          onChangeText={onChangeText}
          style={[styles.input, errorMessage && styles.inputErrorText]}
          {...rest}
        />
      </View>
      {errorMessage && (
        <Text style={[styles.errorText, errorStyle]}>{errorMessage}</Text>
      )}
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    marginBottom: width * 0.05,
  },
  label: {
    fontSize: width * 0.06,
    color: colors.blueGray,
    marginBottom: 5,
  },
  inputContainer: {
    flexDirection: "row",
    alignItems: "center",
    width: "100%",
    borderWidth: 2,
    borderColor: colors.blue,
    borderRadius: width * 0.02,
    paddingHorizontal: width * 0.04,
    paddingVertical: width * 0.02,
  },
  inputError: {
    borderColor: colors.red,
  },
  input: {
    flex: 1,
    fontSize: width * 0.06,
    color: colors.black,
  },
  inputErrorText: {
    color: colors.red,
  },
  icon: {
    marginRight: width * 0.03,
  },
  errorText: {
    fontSize: width * 0.04,
    color: colors.red,
    marginTop: 5,
  },
});

export default Input;
