import { useRef } from "react";
import { lightImpactFeedback, heavyImpactFeedback } from "../utils/general";

type UseDoublePressParams = {
  onFirstPress?: () => void;
  onSecondPress: () => void;
  delay?: number;
};

const useDoublePress = ({
  onFirstPress = () => {},
  onSecondPress,
  delay = 500,
}: UseDoublePressParams) => {
  const lastPressTime = useRef<number>(0);

  return () => {
    const now = Date.now();

    if (now - lastPressTime.current < delay) {
      console.log("Second press")
      lightImpactFeedback();
      onSecondPress();
    } else {
      console.log("First press")
      heavyImpactFeedback();
      onFirstPress();
    }

    lastPressTime.current = now;
  };
};

export default useDoublePress;
