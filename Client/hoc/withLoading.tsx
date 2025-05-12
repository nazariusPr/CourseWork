import { useState } from "react";
import { View, StyleSheet } from "react-native";
import Loading from "../components/UI/Loading";

function withLoading<T>(
  WrappedComponent: React.ComponentType<
    T & { setLoading: (state: boolean) => void }
  >
) {
  return (props: T) => {
    const [loading, setLoading] = useState<boolean>(false);

    return (
      <View style={styles.container}>
        {loading && <Loading />}
        <WrappedComponent {...props} setLoading={setLoading} />
      </View>
    );
  };
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default withLoading;
