import { useTranslation } from "react-i18next";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { Ionicons } from "@expo/vector-icons";
import { Screen } from "../types/general";
import { getResponsiveSize } from "../utils/responsive";
import { colors } from "../constants/colors";
import LocationAssistScreen from "../screens/LocationAssistScreen";
import VisionAssistScreen from "../screens/VisionAssistScreen";

export type RootTabsParamList = {
  LocationAssist: { screen: Screen };
  VisionAssist: { screen: Screen };
};

const Tabs = createBottomTabNavigator<RootTabsParamList>();

const tabIcons: Record<
  keyof RootTabsParamList,
  keyof typeof Ionicons.glyphMap
> = {
  LocationAssist: "navigate-outline",
  VisionAssist: "eye-outline",
};

function TabNavigator() {
  const { t } = useTranslation();

  return (
    <Tabs.Navigator
      screenOptions={({ route }) => ({
        headerShown: true,
        headerTitleStyle: {
          fontSize: getResponsiveSize(18),
          fontWeight: "bold",
          color: colors.black,
        },
        headerTitleAlign: "center",
        headerStyle: {
          backgroundColor: colors.offWhite,
          borderBottomWidth: 1,
          borderTopColor: colors.lightGray,
          shadowColor: colors.black,
          shadowOpacity: 0.1,
          shadowOffset: { width: 0, height: -1 },
          elevation: 5,
        },
        tabBarIcon: ({ color, size }) => (
          <Ionicons name={tabIcons[route.name]} size={size} color={color} />
        ),
        tabBarActiveTintColor: colors.blue,
        tabBarInactiveTintColor: colors.blueGray,
        tabBarLabelStyle: {
          fontSize: getResponsiveSize(12),
          fontWeight: "bold",
        },
        tabBarStyle: {
          backgroundColor: colors.offWhite,
          borderTopWidth: 1,
          borderTopColor: colors.lightGray,
          shadowColor: colors.black,
          shadowOpacity: 0.1,
          shadowOffset: { width: 0, height: -1 },
          elevation: 5,
        },
      })}
    >
      <Tabs.Screen
        name="LocationAssist"
        options={{ title: t("LocationAssist.title") }}
        children={() => <LocationAssistScreen screen="LocationAssist" />}
      />
      <Tabs.Screen
        name="VisionAssist"
        options={{ title: t("VisionAssist.title") }}
        children={() => <VisionAssistScreen screen="VisionAssist" />}
      />
    </Tabs.Navigator>
  );
}

export default TabNavigator;
