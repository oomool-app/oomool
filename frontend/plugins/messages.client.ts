// plugins/firebase.ts
import type { FirebaseApp } from 'firebase/app';
import { getMessaging, type Messaging } from 'firebase/messaging';

export default defineNuxtPlugin(async (nuxtApp) => {
  // Firebase 초기화
  const firebaseApp: FirebaseApp = useFirebaseApp();

  console.log('Load Firebase Messaging');

  // FCM 인스턴스를 가져옴
  const messaging: Messaging = getMessaging(firebaseApp);

  // 'fcm' 키로 FCM 인스턴스를 Nuxt.js 애플리케이션에 주입
  return {
    provide: {
      fcm: messaging,
    },
  };
});
