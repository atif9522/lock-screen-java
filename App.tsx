import React from 'react';
import { View, StyleSheet, Text } from 'react-native';
import LockSection from './src/commonComponents/LockSection';

const App: React.FC = () => {
  return (
    <View style={styles.mainContainer}>
      <View style={styles.adsContainer}>
        <Text style={styles.adText}>Ads here</Text>
      </View>
      <View style={styles.contentContainer}>
        <LockSection />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  adText: {
    fontSize: 100,
    color: "#000"
  },
  mainContainer: {
    backgroundColor: "#FFF",
    display: "flex",
    maxWidth: 480,
    width: "100%",
    flexDirection: "column",
    alignItems: "stretch",
    flex: 1,
    position: 'relative',
    margin: 0,
  },
  adsContainer: {
    color: "#000",
    alignSelf: "center",
    marginTop: 192,
    width: 391,
    fontFamily: "Inter, sans-serif",
    fontSize: 100,
    fontWeight: "400",
    paddingHorizontal: 40
  },
  contentContainer: {
    backgroundColor: "#FD6315",
    width: "100%",
    padding: 27,
    paddingHorizontal: 33,
    position: 'absolute',
    bottom: 0
  },
  separatorWrapper: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'stretch',
    width: '46%',
    marginLeft: 20,
  },
});

export default App;
