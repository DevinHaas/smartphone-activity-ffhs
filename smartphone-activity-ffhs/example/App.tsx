import { SmartphoneActivityFfhsView } from "smartphone-activity-ffhs";
import { View } from "react-native";

export default function App() {

  return (
    <View style={styles.container}>
      <SmartphoneActivityFfhsView
        url="https://www.example.com"
        onLoad={({ nativeEvent: { url } }) => console.log(`Loaded: ${url}`)}
        style={{ flex: 1, width: "100%" }}
      />
    </View>
  );
}

const styles = {
  header: {
    fontSize: 30,
    margin: 20
  },
  groupHeader: {
    fontSize: 20,
    marginBottom: 20
  },
  group: {
    margin: 20,
    backgroundColor: "#000",
    borderRadius: 10,
    padding: 20
  },
  container: {
    flex: 1,
    backgroundColor: "#000"
  },
  view: {
    flex: 1,
    height: 1000
  }
};
