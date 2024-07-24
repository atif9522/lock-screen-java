import React, {useRef} from 'react';
import {
  View,
  StyleSheet,
  Image,
  PanResponder,
  Animated,
  BackHandler,
} from 'react-native';

type LockProps = {
  id: number;
  borderColor: string;
};

const LockImage: React.FC<LockProps> = ({id, borderColor}) => (
  <Image
    source={require('../assets/lock.png')}
    style={[styles.profileImage, {opacity: id !== 2 ? 0.6 : 1, borderColor}]}
  />
);

const LockSection: React.FC = () => {
  const locks = [1, 2, 3];
  const pan = useRef(new Animated.ValueXY()).current;

  const panResponder = useRef(
    PanResponder.create({
      onStartShouldSetPanResponder: () => true,
      onPanResponderMove: Animated.event([null, {dx: pan.x}], {
        useNativeDriver: false,
      }),
      onPanResponderRelease: (e, {dx}) => {
        if (dx < -50 || dx > 50) {
          // Swiped left or right
          BackHandler.exitApp(); // Close the application
        }
        Animated.spring(pan, {
          toValue: {x: 0, y: 0},
          useNativeDriver: false,
        }).start();
      },
    }),
  ).current;

  return (
    <View style={styles.profileContainer}>
      {locks.map((id, index) => (
        <View key={id} style={styles.profileWrapper}>
          {id === 2 ? (
            <Animated.View
              style={[pan.getLayout()]}
              {...panResponder.panHandlers}>
              <LockImage borderColor="rgba(255, 255, 255, 1)" id={id} />
            </Animated.View>
          ) : (
            <LockImage borderColor="rgba(255, 255, 255, 0.3)" id={id} />
          )}
        </View>
      ))}
    </View>
  );
};

const styles = StyleSheet.create({
  profileContainer: {
    gap: 20,
    display: 'flex',
    flexDirection: 'row',
    justifyContent: 'space-between',
  },
  profileWrapper: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'stretch',
    width: '27%',
  },
  profileImage: {
    opacity: 0.5,
  },
});

export default LockSection;
