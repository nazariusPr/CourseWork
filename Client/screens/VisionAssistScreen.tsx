import { CameraView, useCameraPermissions } from "expo-camera";
import { useState, useRef } from "react";
import { useTranslation } from "react-i18next";
import { Button, StyleSheet, Text, View } from "react-native";
import { ScreenProps } from "../types/general";
import { analyzeBase64 } from "../api/axiosRequests";
import { speak } from "../utils/general";
import HeaderButton from "../components/UI/HeaderButton";
import ScreenButton from "../components/UI/ScreenButton";
import AnalyzedView from "../components/Vision/AnalyzedView";
import Loading from "../components/UI/Loading";
import withSound from "../hoc/withSound";

function VisionAssistScreen({ stopSound, playSound }: ScreenProps) {
  const [isCameraReady, setIsCameraReady] = useState<boolean>(false);
  const [permission, requestPermission] = useCameraPermissions();
  const [photoUri, setPhotoUri] = useState<string | null>(null);
  const { t } = useTranslation();
  const cameraRef = useRef<CameraView>(null);

  const handleDoublePress = async () => {
    if (!cameraRef.current && photoUri) return;

    try {
      const photo = await cameraRef.current?.takePictureAsync({ base64: true });
      if (photo) {
        const uri = photo.uri;
        setPhotoUri(uri);

        if (photo.base64) {
          const response = await analyzeBase64({ base64: photo.base64 });
          console.log("Message : " + response.message);
          speak(response.message);
        }
      }
    } catch (error) {
      console.error("Error taking picture:", error);
    } finally {
      setPhotoUri(null);
    }
  };

  if (!permission) {
    return <Loading />;
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
      <HeaderButton title={t("VisionAssist.title")} onPress={playSound} />
      {photoUri && <AnalyzedView uri={photoUri} />}
      <ScreenButton
        beforeDoublePress={stopSound}
        onDoublePress={handleDoublePress}
      />
      {!isCameraReady && <Loading />}
      <CameraView
        ref={cameraRef}
        style={styles.camera}
        facing="back"
        autofocus="on"
        onCameraReady={() => setIsCameraReady(true)}
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
