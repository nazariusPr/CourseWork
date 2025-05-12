import { useCallback } from "react";
import { Pressable } from "react-native";
import { useTranslation } from "react-i18next";
import { useNavigation } from "@react-navigation/native";
import {
  createBottomTabNavigator,
  BottomTabNavigationProp,
} from "@react-navigation/bottom-tabs";
import { Ionicons } from "@expo/vector-icons";
import { Screen } from "../types/general";
import { getResponsiveSize } from "../utils/responsive";
import { colors } from "../constants/theme";
import { heavyImpactFeedback } from "../utils/general";
import { GestureHandlerRootView } from "react-native-gesture-handler";
import { SwipeWrapper } from "../components/UI/SwipeWrapper";
import LocationAssistScreen from "../screens/LocationAssistScreen";
import VisionAssistScreen from "../screens/VisionAssistScreen";
import EmergencyCallScreen from "../screens/EmergencyCallScreen";

export type RootTabsParamList = {
  LocationAssist: { screen: Screen };
  VisionAssist: { screen: Screen };
  EmergencyCall: { screen: Screen };
};

const Tabs = createBottomTabNavigator<RootTabsParamList>();

const tabIcons: Record<
  keyof RootTabsParamList,
  keyof typeof Ionicons.glyphMap
> = {
  LocationAssist: "navigate-outline",
  VisionAssist: "eye-outline",
  EmergencyCall: "call-outline",
};

const tabOrder: (keyof RootTabsParamList)[] = [
  "LocationAssist",
  "VisionAssist",
  "EmergencyCall",
];

function TabNavigator() {
  const { t } = useTranslation();

  const navigation =
    useNavigation<BottomTabNavigationProp<RootTabsParamList>>();

  const onLeftSwipe = useCallback(() => {
    const currentIndex = navigation.getState()?.index ?? 0;

    if (currentIndex < tabOrder.length - 1) {
      const nextRoute = tabOrder[currentIndex + 1];
      navigation.navigate({
        name: nextRoute,
        params: { screen: nextRoute },
      });
    }
  }, [navigation, tabOrder]);

  const onRightSwipe = useCallback(() => {
    const currentIndex = navigation.getState()?.index ?? 0;

    if (currentIndex > 0) {
      const prevRoute = tabOrder[currentIndex - 1];
      navigation.navigate({
        name: prevRoute,
        params: { screen: prevRoute },
      });
    }
  }, [navigation, tabOrder]);

  return (
    <GestureHandlerRootView style={{ flex: 1 }}>
      <Tabs.Navigator
        screenOptions={({ route, navigation }) => ({
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
            height: getResponsiveSize(120),
          },
          tabBarIcon: ({ color, size }) => (
            <Ionicons name={tabIcons[route.name]} size={size} color={color} />
          ),
          tabBarButton: (props) => (
            <Pressable
              {...props}
              onPress={() => {
                heavyImpactFeedback();
                navigation.navigate(route);
              }}
            />
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
            height: getResponsiveSize(120),
            paddingBottom: getResponsiveSize(20),
            paddingTop: getResponsiveSize(20),
          },
        })}
      >
        <Tabs.Screen
          name="LocationAssist"
          options={{ title: t("LocationAssist.title") }}
          children={() => (
            <SwipeWrapper onLeftSwipe={onLeftSwipe} onRightSwipe={onRightSwipe}>
              <LocationAssistScreen screen="LocationAssist" />
            </SwipeWrapper>
          )}
        />
        <Tabs.Screen
          name="VisionAssist"
          options={{ title: t("VisionAssist.title") }}
          children={() => (
            <SwipeWrapper onLeftSwipe={onLeftSwipe} onRightSwipe={onRightSwipe}>
              <VisionAssistScreen screen="VisionAssist" />
            </SwipeWrapper>
          )}
        />
        <Tabs.Screen
          name="EmergencyCall"
          options={{ title: t("EmergencyCall.title") }}
          children={() => (
            <SwipeWrapper onLeftSwipe={onLeftSwipe} onRightSwipe={onRightSwipe}>
              <EmergencyCallScreen screen="EmergencyCall" />
            </SwipeWrapper>
          )}
        />
      </Tabs.Navigator>
    </GestureHandlerRootView>
  );
}

export default TabNavigator;
