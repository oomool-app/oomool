<template>
  <div @click="$router.push({ path: `/message` })">
    <div class="relative">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="26"
        height="26"
        viewBox="0 0 28 28"
        fill="white"
      >
        <path
          d="M21 9.33337C21 7.47686 20.2625 5.69638 18.9497 4.38363C17.637 3.07087 15.8565 2.33337 14 2.33337C12.1435 2.33337 10.363 3.07087 9.05025 4.38363C7.7375 5.69638 7 7.47686 7 9.33337C7 17.5 3.5 19.8334 3.5 19.8334H24.5C24.5 19.8334 21 17.5 21 9.33337Z"
          stroke="#F6F6F6"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M16.0181 24.5C15.813 24.8536 15.5186 25.1471 15.1644 25.3511C14.8102 25.5551 14.4086 25.6625 13.9998 25.6625C13.591 25.6625 13.1894 25.5551 12.8352 25.3511C12.481 25.1471 12.1866 24.8536 11.9814 24.5"
          stroke="#F6F6F6"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
      </svg>
      <div v-if="MessageNotRead == 'true'">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="10"
          height="10"
          viewBox="0 0 10 10"
          fill="none"
          class="absolute top-0 right-0"
        >
          <circle cx="5" cy="5" r="5" fill="#FF0000" />
        </svg>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '~/stores/userStore';
import { type ICheckUnreadNotificationInput } from '../repository/modules/interface/notifications.interface';

const userStore = useUserStore();
const { $api } = useNuxtApp();

// 메시지 읽음 여부 가져올 변수
const MessageNotRead = ref('');

const checkUnreadNotifications = async (): Promise<void> => {
  try {
    const storedUser = userStore.getStoredUser();
    if (storedUser != null) {
      const userId: ICheckUnreadNotificationInput = {
        userId: storedUser.id,
      };

      const response =
        await $api.notifications.checkUnreadNotifications(userId);
      MessageNotRead.value = response.data;
    }
  } catch (error) {
    console.error('Error while fetching room list:', error);
  }
};

onBeforeUpdate(async () => {
  await checkUnreadNotifications();
});

onMounted(async () => {
  await checkUnreadNotifications();
});
</script>
