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
      lightImpactFeedback();
      onSecondPress();
    } else {
      heavyImpactFeedback();
      onFirstPress();
    }

    lastPressTime.current = now;
  };
};

export default useDoublePress;
