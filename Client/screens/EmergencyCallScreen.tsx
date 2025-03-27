import { View, StyleSheet, FlatList } from "react-native";
import { EmergencyNumber, ScreenProps } from "../types/general";
import { Linking } from "react-native";
import { emergencyNumbers } from "../constants/general";
import { getResponsiveSize } from "../utils/responsive";
import HeaderRightButton from "../components/UI/HeaderRightButton";
import EmergencyCallButton from "../components/EmergencyCall/EmergencyCallButton";
import withSound from "../hoc/withSound";

type EmergencyType = keyof typeof emergencyNumbers;

function EmergencyCallScreen({ stopSound, playSound }: ScreenProps) {
  const openDialer = (phoneNumber: string) => {
    stopSound();
    const url = `tel:${phoneNumber}`;
    Linking.openURL(url).catch((err) =>
      console.error("Failed to open dialer", err)
    );
  };

  return (
    <View style={styles.container}>
      <HeaderRightButton onPress={playSound} />
      <FlatList
        data={Object.entries(emergencyNumbers)}
        keyExtractor={([key]) => key}
        numColumns={2}
        contentContainerStyle={styles.buttonsContainer}
        renderItem={({ item }) => {
          const [key, phoneNumber] = item;
          return (
            <EmergencyCallButton
              type={key as EmergencyType}
              onPress={() => openDialer(phoneNumber)}
            />
          );
        }}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: "center",
    alignItems: "center",
    padding: getResponsiveSize(20),
  },
  buttonsContainer: {
    flex: 1,
    height: "100%",
    justifyContent: "center",
    paddingHorizontal: getResponsiveSize(10),
  },
});

export default withSound(EmergencyCallScreen);
