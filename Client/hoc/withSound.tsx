import { useCallback, useRef } from "react";
import { useFocusEffect } from "@react-navigation/native";
import { Audio } from "expo-av";
import { useTranslation } from "react-i18next";
import { Screen, ScreenProps } from "../types/general";
import { soundMap } from "../constants/sounds";
import { useFirstLaunch } from "../context/FirstLaunchContext";
import { speak, speakStop } from "../utils/general";
import i18n from "../i18n";

interface WithSoundProps {
  screen: Screen;
}

function withSound<T>(WrappedComponent: React.ComponentType<T & ScreenProps>) {
  return (props: T & WithSoundProps) => {
    const { screen } = props;
    const { t } = useTranslation();
    const soundRef = useRef<Audio.Sound | null>(null);
    const visitedScreensRef = useRef<Set<Screen>>(new Set());
    const { isFirstLaunch } = useFirstLaunch();

    const stopSound = useCallback(async () => {
      if (soundRef.current) {
        try {
          await soundRef.current.stopAsync();
          await soundRef.current.unloadAsync();
        } catch (error) {
          console.error("Error stopping sound:", error);
        } finally {
          soundRef.current = null;
        }
      }
    }, []);

    const playSound = useCallback(async () => {
      if (!screen) return;

      const currentLanguage = i18n.language;
      const helpSound = soundMap[currentLanguage]?.[screen];

      if (!helpSound) {
        console.error(`No sound found for ${screen} in ${currentLanguage}`);
        return;
      }

      try {
        await stopSound();
        const { sound } = await Audio.Sound.createAsync(helpSound, {
          shouldPlay: true,
        });
        soundRef.current = sound;
      } catch (error) {
        console.error("Error playing sound:", error);
      }
    }, [screen, stopSound]);

    useFocusEffect(
      useCallback(() => {
        speakStop();

        const isFirstTimeVisiting = !visitedScreensRef.current.has(screen);
        visitedScreensRef.current.add(screen);

        if (isFirstLaunch && isFirstTimeVisiting) {
          playSound();
        } else {
          const clue = t(screen + ".description");
          speak(clue);
        }

        return () => {
          stopSound();
        };
      }, [playSound, stopSound])
    );

    return (
      <WrappedComponent
        {...props}
        playSound={playSound}
        stopSound={stopSound}
      />
    );
  };
}

export default withSound;
