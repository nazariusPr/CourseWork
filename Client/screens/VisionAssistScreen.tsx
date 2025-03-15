import { CameraView, useCameraPermissions } from "expo-camera";
import { useState, useRef } from "react";
import { Button, StyleSheet, Text, View } from "react-native";
import { ScreenProps } from "../types/general";
import HeaderRightButton from "../components/UI/HeaderRightButton";
import ScreenButton from "../components/UI/ScreenButton";
import AnalyzedView from "../components/Vision/AnalyzedView";
import withSound from "../hoc/withSound";

function VisionAssistScreen({ stopSound, playSound }: ScreenProps) {
  const [permission, requestPermission] = useCameraPermissions();
  const cameraRef = useRef<CameraView>(null);
  const [photoUri, setPhotoUri] = useState<string | null>(null);

  const handleDoublePress = async () => {
    if (cameraRef.current && !photoUri) {
      try {
        const photo = await cameraRef.current.takePictureAsync();
        if (photo) {
          setPhotoUri(photo.uri);
          await new Promise((resolve) => setTimeout(resolve, 10000));
        }
      } catch (error) {
        console.error("Error taking picture:", error);
      } finally {
        setPhotoUri(null);
      }
    }
  };

  if (!permission) {
    return <View />;
  }

  if (!permission.granted) {
    return (
      <View style={styles.container}>
        <Text style={styles.message}>
          We need your permission to show the camera
        </Text>
        <Button onPress={requestPermission} title="Grant Permission" />
      </View>
    );
  }

  return (
    <View style={styles.container}>
      <HeaderRightButton onPress={playSound} />
      {photoUri && <AnalyzedView uri={photoUri} />}
      <ScreenButton
        beforeDoublePress={stopSound}
        onDoublePress={handleDoublePress}
      />
      <CameraView
        ref={cameraRef}
        style={styles.camera}
        facing="back"
        autofocus="on"
      ></CameraView>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    width: "100%",
    height: "100%",
    justifyContent: "center",
  },
  camera: {
    flex: 1,
    width: "100%",
    height: "100%",
  },
  message: {
    textAlign: "center",
    paddingBottom: 10,
  },
});

export default withSound(VisionAssistScreen);
