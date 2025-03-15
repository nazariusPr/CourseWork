import { Pressable } from "react-native";
import { useLayoutEffect } from "react";
import { useNavigation } from "@react-navigation/native";
import { BottomTabNavigationProp } from "@react-navigation/bottom-tabs";
import { RootTabsParamList } from "../../navigation/TabNavigator";
import { Ionicons } from "@expo/vector-icons";
import { getResponsiveSize } from "../../utils/responsive";
import { colors } from "../../constants/colors";

type HeaderRightButtonProps = {
  onPress: () => void;
  accessibilityLabel?: string;
};

function HeaderRightButton({
  onPress,
  accessibilityLabel = "Play",
}: HeaderRightButtonProps) {
  const navigation =
    useNavigation<BottomTabNavigationProp<RootTabsParamList>>();

  useLayoutEffect(() => {
    navigation.setOptions({
      headerRight: () => (
        <Pressable
          onPress={onPress}
          style={{ marginRight: getResponsiveSize(15) }}
          accessibilityLabel={accessibilityLabel}
          accessibilityRole="button"
        >
          <Ionicons
            name="volume-medium"
            size={getResponsiveSize(32)}
            color={colors.black}
          />
        </Pressable>
      ),
    });
  }, [navigation, onPress, accessibilityLabel]);

  return <></>;
}

export default HeaderRightButton;
