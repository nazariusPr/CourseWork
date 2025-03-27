import { useState, useEffect, useLayoutEffect } from "react";
import { View, StyleSheet, Alert, Pressable } from "react-native";
import MapView, { Marker } from "react-native-maps";
import { Coordinate, ScreenProps } from "../types/general";
import { speak } from "../utils/general";
import { getLocation } from "../api/axiosRequests";
import ScreenButton from "../components/UI/ScreenButton";
import HeaderRightButton from "../components/UI/HeaderRightButton";
import withSound from "../hoc/withSound";
import * as Location from "expo-location";
import * as Speech from "expo-speech";

function LocationAssistScreen({ playSound, stopSound }: ScreenProps) {
  const [coordinate, setCoordinate] = useState<Coordinate | null>(null);

  useEffect(() => {
    const getLocation = async () => {
      playSound;
      const { status } = await Location.requestForegroundPermissionsAsync();
      if (status !== "granted") {
        Alert.alert("Permission denied", "Location access is required.");
        return;
      }

      const loc = await Location.getCurrentPositionAsync({});
      setCoordinate({
        latitude: loc.coords.latitude,
        longitude: loc.coords.longitude,
      });
    };

    getLocation();
    const intervalId = setInterval(getLocation, 10000);

    return () => clearInterval(intervalId);
  }, []);

  if (!coordinate) {
    return (
      <View style={styles.container}>
        {/* Loading or Empty state when no coordinates available */}
      </View>
    );
  }

  const handleDoublePress = async () => {
    try {
      const response = await getLocation(
        coordinate.latitude,
        coordinate.longitude
      );

      speak(response.data.message);
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
      <HeaderRightButton onPress={handleHeaderPress} />
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
