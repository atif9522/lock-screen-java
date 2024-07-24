import React, {useState, useEffect} from 'react';
import {View, Text, StyleSheet, Switch, Button} from 'react-native';
import {useFocusEffect} from '@react-navigation/native';

const HomeScreen = ({navigation}: any) => {
  const [isLockEnabled, setIsLockEnabled] = useState(false);

  useFocusEffect(
    React.useCallback(() => {
      // Reset isLockEnabled when the screen is focused
      setIsLockEnabled(false);
    }, []),
  );

  useEffect(() => {
    if (isLockEnabled) {
      navigation.navigate('LockScreen');
    }
  }, [isLockEnabled, navigation]);

  return (
    <View style={styles.container}>
      <Text style={styles.title}>Home Screen</Text>
      <View style={styles.switchContainer}>
        <Text>Enable Lock Screen</Text>
        <Switch
          value={isLockEnabled}
          onValueChange={value => setIsLockEnabled(value)}
        />
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    padding: 20,
  },
  title: {
    fontSize: 24,
    marginBottom: 20,
  },
  switchContainer: {
    flexDirection: 'row',
    alignItems: 'center',
    marginBottom: 20,
  },
});

export default HomeScreen;
