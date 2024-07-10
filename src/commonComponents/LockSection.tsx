import React from 'react';
import { View, StyleSheet, Image } from 'react-native';
type LockProps = {
  id: number
  borderColor: string;
};

const LockImage: React.FC<LockProps> = ({ id, borderColor }) => (
  <Image
    source={require('../assets/lock.png')}
    style={[styles.profileImage, { opacity: id != 2 ? 0.6 : 1, borderColor }]}
  />
);

const LockSection: React.FC = () => {
  const locks = [1, 2, 3
  ];

  return (
    <View style={styles.profileContainer}>
      {locks.map((id, index) => (
        <View key={id} style={styles.profileWrapper}>
          <LockImage
            borderColor={index === 1 ? "rgba(255, 255, 255, 1)" : "rgba(255, 255, 255, 0.3)"}
            id={id}
          />
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
    opacity: 0.5
  },
});

export default LockSection;