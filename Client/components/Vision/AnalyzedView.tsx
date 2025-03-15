import React, { useEffect, useRef } from "react";
import { View, Image, StyleSheet, Dimensions, Animated } from "react-native";
import { getResponsiveSize } from "../../utils/responsive";

const { width, height } = Dimensions.get("window");

type AnalyzedViewProps = {
  uri: string;
};

function AnalyzedView({ uri }: AnalyzedViewProps) {
  const linePosition = useRef(new Animated.Value(0)).current;

  useEffect(() => {
    Animated.loop(
      Animated.sequence([
        Animated.timing(linePosition, {
          toValue: height * 0.3,
          duration: 2000,
          useNativeDriver: true,
        }),
        Animated.timing(linePosition, {
          toValue: height * -0.3,
          duration: 2000,
          useNativeDriver: true,
        }),
        Animated.timing(linePosition, {
          toValue: height * 0,
          duration: 2000,
          useNativeDriver: true,
        }),
      ])
    ).start();
  }, [linePosition]);

  return (
    <View style={styles.overlay}>
      <Image source={{ uri }} style={styles.image} />
      <Animated.View
        style={[styles.line, { transform: [{ translateY: linePosition }] }]}
      />
    </View>
  );
}

const styles = StyleSheet.create({
  overlay: {
    position: "absolute",
    width: "100%",
    height: "100%",
    backgroundColor: "rgba(0, 0, 0, 0.5)",
    justifyContent: "center",
    alignItems: "center",
    zIndex: 150,
  },
  image: {
    width: getResponsiveSize(width * 0.8),
    height: getResponsiveSize(height * 0.6),
    resizeMode: "contain",
    borderRadius: getResponsiveSize(10),
    zIndex: 150,
  },
  line: {
    position: "absolute",
    width: "95%",
    height: 4,
    backgroundColor: "green",
    zIndex: 160,
  },
});

export default AnalyzedView;
