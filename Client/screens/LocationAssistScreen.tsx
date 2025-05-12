import { useState, useEffect } from "react";
import { useTranslation } from "react-i18next";
import { View, StyleSheet } from "react-native";
import MapView, { Marker } from "react-native-maps";
import { Coordinate, ScreenProps } from "../types/general";
import { speak, getCurrentCoordinate } from "../utils/general";
import { getLocation } from "../api/axiosRequests";
import ScreenButton from "../components/UI/ScreenButton";
import HeaderButton from "../components/UI/HeaderButton";
import Loading from "../components/UI/Loading";
import withSound from "../hoc/withSound";
import * as Speech from "expo-speech";

function LocationAssistScreen({ playSound, stopSound }: ScreenProps) {
  const { t } = useTranslation();
  const [coordinate, setCoordinate] = useState<Coordinate | null>(null);

  useEffect(() => {
    const getLocation = async () => {
      const loc = await getCurrentCoordinate();
      if (loc && loc.latitude !== undefined && loc.longitude !== undefined) {
        setCoordinate({
          latitude: loc.latitude,
          longitude: loc.longitude,
        });
      }
    };

    getLocation();
    const intervalId = setInterval(getLocation, 10000);

    return () => clearInterval(intervalId);
  }, []);

  if (!coordinate) {
    return (
      <View style={styles.container}>
        <Loading />
      </View>
    );
  }

  const handleDoublePress = async () => {
    try {
      const response = await getLocation(
        coordinate.latitude,
        coordinate.longitude
      );

      speak(response.message);
    } catch (error) {
      console.error("Error fetching location:", error);
    }
  };

  const handleHeaderPress = () => {
    Speech.stop();
    playSound();
  };

  return (
    <View style={styles.container}>
      <HeaderButton
        title={t("LocationAssist.title")}
        onPress={handleHeaderPress}
      />
      <ScreenButton
        beforeDoublePress={stopSound}
        onDoublePress={handleDoublePress}
      />
      <MapView
        style={styles.map}
        initialRegion={{
          latitude: coordinate.latitude,
          longitude: coordinate.longitude,
          latitudeDelta: 0.0922,
          longitudeDelta: 0.0421,
        }}
      >
        <Marker
          coordinate={coordinate}
          title="My Location"
          description="This is your current location"
        />
      </MapView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: "100%",
    height: "100%",
  },
  map: {
    width: "100%",
    height: "100%",
  },
});

export default withSound(LocationAssistScreen);
