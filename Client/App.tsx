import { I18nextProvider } from "react-i18next";
import { NavigationContainer } from "@react-navigation/native";
import { StatusBar } from "expo-status-bar";
import i18n from "./i18n";
import TabNavigator from "./navigation/TabNavigator";

const App = () => (
  <I18nextProvider i18n={i18n}>
    <StatusBar style="dark" />
    <NavigationContainer>
      <TabNavigator />
    </NavigationContainer>
  </I18nextProvider>
);

export default App;
