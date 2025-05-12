import { useState, useCallback } from "react";
import { useFocusEffect } from "@react-navigation/native";
import { View, StyleSheet, FlatList } from "react-native";
import { ScreenProps } from "../types/general";
import { Linking } from "react-native";
import { getResponsiveSize } from "../utils/responsive";
import { useTranslation } from "react-i18next";
import { getEmergencyCall } from "../api/axiosRequests";
import { EmergencyCallDto } from "../types/api";
import { getCurrentCoordinate } from "../utils/general";
import Loading from "../components/UI/Loading";
import HeaderButton from "../components/UI/HeaderButton";
import EmergencyCallButton from "../components/EmergencyCall/EmergencyCallButton";
import withSound from "../hoc/withSound";

function EmergencyCallScreen({ stopSound, playSound }: ScreenProps) {
  const [loading, setLoading] = useState(true);
  const [emergencyNumbers, setEmergencyNumbers] =
    useState<Array<EmergencyCallDto> | null>(null);
  const { t } = useTranslation();

  const openDialer = (phoneNumber: string) => {
    stopSound();
    const url = `tel:${phoneNumber}`;
    Linking.openURL(url).catch((err) =>
      console.error("Failed to open dialer", err)
    );
  };

  useFocusEffect(
    useCallback(() => {
      const fetchEmergencyNumbers = async () => {
        try {
          const coordinate = await getCurrentCoordinate();
          setLoading(true);
          const data = await getEmergencyCall(
            coordinate.latitude,
            coordinate.longitude
          );
          setEmergencyNumbers(data);
        } catch (error) {
          console.error("Failed to fetch emergency numbers", error);
        } finally {
          setLoading(false);
        }
      };

      fetchEmergencyNumbers();
    }, [])
  );

  if (loading) {
    return <Loading />;
  }

  return (
    <View style={styles.container}>
      <HeaderButton title={t("EmergencyCall.title")} onPress={playSound} />
      <FlatList
        data={emergencyNumbers}
        keyExtractor={(item) => item.emergencyService}
        numColumns={2}
        contentContainerStyle={styles.buttonsContainer}
        renderItem={({ item }) => {
          return (
            <EmergencyCallButton
              type={item.emergencyService}
              onSecondPress={() => {
                openDialer(item.phoneNumber);
              }}
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
