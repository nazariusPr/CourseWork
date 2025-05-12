import { Pressable, Text } from "react-native";
import { useLayoutEffect } from "react";
import { useNavigation, useRoute } from "@react-navigation/native";
import { BottomTabNavigationProp } from "@react-navigation/bottom-tabs";
import { RootTabsParamList } from "../../navigation/TabNavigator";
import { Ionicons } from "@expo/vector-icons";
import { getResponsiveSize } from "../../utils/responsive";
import { colors } from "../../constants/theme";
import { heavyImpactFeedback } from "../../utils/general";

type HeaderButtonProps = {
  onPress: () => void;
  title?: string;
  accessibilityLabel?: string;
};

function HeaderButton({
  onPress,
  title,
  accessibilityLabel = "Play",
}: HeaderButtonProps) {
  const navigation =
    useNavigation<BottomTabNavigationProp<RootTabsParamList>>();
  const route = useRoute();
  const resolvedTitle = title ?? route.name;

  const handlePress = () => {
    heavyImpactFeedback();
    onPress();
  };

  useLayoutEffect(() => {
    navigation.setOptions({
      headerTitle: () => (
        <Pressable
          onPress={handlePress}
          accessibilityLabel={`${resolvedTitle} screen`}
          accessibilityRole="button"
          style={{
            flex: 1,
            justifyContent: "center",
            alignItems: "center",
          }}
        >
          <Text
            style={{
              fontSize: getResponsiveSize(18),
              fontWeight: "bold",
              color: colors.black,
            }}
          >
            {resolvedTitle}
          </Text>
        </Pressable>
      ),
      headerRight: () => (
        <Pressable
          onPress={handlePress}
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

export default HeaderButton;
