/* eslint-disable @typescript-eslint/no-unsafe-argument */
/* eslint-disable @typescript-eslint/strict-boolean-expressions */
/* eslint-disable no-unused-vars */
/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable no-undef */

import {
  cleanupOutdatedCaches,
  createHandlerBoundToURL,
  precacheAndRoute,
} from 'workbox-precaching';
import { clientsClaim } from 'workbox-core';
// import { NavigationRoute, registerRoute } from 'workbox-routing';

import { initializeApp } from 'firebase/app';
import { getMessaging } from 'firebase/messaging/sw';

// self.__WB_MANIFEST is default injection point
precacheAndRoute(self.__WB_MANIFEST);

// clean old assets
cleanupOutdatedCaches();

// 오프라인 접속 관련 설정

// let allowlist;
// if (import.meta.env.DEV) {
//   allowlist = [/^\/$/];
// }

// to allow work offline
// registerRoute(new NavigationRoute(createHandlerBoundToURL('/'), { allowlist }));

const firebaseApp = initializeApp({
  apiKey: 'AIzaSyDjqMDfzdTrqulvOhLZNVbWAuvQvaW75B8',
  authDomain: 'oomool.firebaseapp.com',
  projectId: 'oomool',
  storageBucket: 'oomool.appspot.com',
  messagingSenderId: '88697065613',
  appId: '1:88697065613:web:9ff9caa45540b4f34c52c4',
  measurementId: 'G-B0EENVGFTK',
});

const messaging = getMessaging(firebaseApp);

self.addEventListener('push', function (event) {
  console.log(event);

  if (event.data) {
    // 알림 메세지일 경우엔 event.data.json().notification;
    console.log(event.data.json());
    const data = event.data.json().data;
    const options = {
      title: data.title,
      body: data.body,
      data: {
        click_action: data.click_action, // 이 필드는 밑의 클릭 이벤트 처리에 사용됨
      },
    };

    event.waitUntil(self.registration.showNotification(data.title, options));
  } else {
    console.log('This push event has no data.');
  }
});

self.skipWaiting();
clientsClaim();
