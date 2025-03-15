import { useRef } from "react";

function useDoublePress(callback: () => void, delay: number = 500) {
  const lastPressTime = useRef<number>(0);

  return () => {
    const now = Date.now();
    if (now - lastPressTime.current < delay) {
      callback();
    }
    lastPressTime.current = now;
  };
}

export default useDoublePress;
