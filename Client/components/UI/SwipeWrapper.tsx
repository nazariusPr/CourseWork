import { ReactNode } from "react";
import { View } from "react-native";
import { Gesture, GestureDetector } from "react-native-gesture-handler";
import { lightImpactFeedback } from "../../utils/general";

type SwipeWrapperProps = {
  children: ReactNode;
  onLeftSwipe: () => void;
  onRightSwipe: () => void;
  withFeedback?: boolean;
};

const MIN_SWIPE_DISTANCE = 50;

export function SwipeWrapper({
  children,
  onLeftSwipe,
  onRightSwipe,
  withFeedback = true,
}: SwipeWrapperProps) {
  const panGesture = Gesture.Pan()
    .onEnd((event) => {
      const { translationX } = event;
      if (Math.abs(translationX) > MIN_SWIPE_DISTANCE) {
        if (translationX < 0) {
          if (withFeedback) {
            lightImpactFeedback();
          }
          onLeftSwipe();
        } else if (translationX > 0) {
          if (withFeedback) {
            lightImpactFeedback();
          }
          onRightSwipe();
        }
      }
    })
    .minDistance(MIN_SWIPE_DISTANCE)
    .runOnJS(true)
    .shouldCancelWhenOutside(true);

  return (
    <GestureDetector gesture={panGesture}>
      <View style={{ flex: 1, zIndex: 1000000 }}>{children}</View>
    </GestureDetector>
  );
}
