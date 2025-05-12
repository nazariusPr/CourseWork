import {
  createContext,
  useContext,
  useState,
  useEffect,
  ReactNode,
} from "react";
import AsyncStorage from "@react-native-async-storage/async-storage";

const FIRST_LAUNCH_KEY = "hasLaunched";

type FirstLaunchProviderProps = {
  children: ReactNode;
};

type FirstLaunchContextType = {
  isFirstLaunch: boolean | null;
};

const FirstLaunchContext = createContext({} as FirstLaunchContextType);

export function useFirstLaunch() {
  return useContext(FirstLaunchContext);
}

export const FirstLaunchProvider = ({ children }: FirstLaunchProviderProps) => {
  const [isFirstLaunch, setIsFirstLaunch] = useState<boolean | null>(null);

  useEffect(() => {
    const checkFirstLaunch = async () => {
      const stored = await AsyncStorage.getItem(FIRST_LAUNCH_KEY);
      if (stored === null) {
        await AsyncStorage.setItem(FIRST_LAUNCH_KEY, "true");
        setIsFirstLaunch(true);
      } else {
        setIsFirstLaunch(false);
      }
    };

    checkFirstLaunch();
  }, []);

  return (
    <FirstLaunchContext.Provider value={{ isFirstLaunch }}>
      {children}
    </FirstLaunchContext.Provider>
  );
};
