<template>
  <div>
    <h1>Firebase Test Page</h1>
    <div class="d-flex flex-column">
      <button
        class="m-4 p-4 bg-primary text-white"
        @click="requestNotificationPermission"
      >
        Ask Alarm Permission
      </button>
      <button class="m-4 p-4 bg-primary text-white" @click="getMessageToken">
        Get Message Token
      </button>
      <div>
        {{ message }}
      </div>
      <button class="m-4 p-4 bg-primary text-white" @click="saveUserToken">
        Save Token to DB
      </button>
      <button class="m-4 p-4 bg-primary text-white" @click="removeUserToken">
        Remove Token from DB
      </button>
      <div>
        {{ result }}
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { FETCH_FCM_TOKEN } from '@/api/fcmToken';
import { useUserStore } from '@/stores/userStore';

const userStore = useUserStore();
const user = userStore.user ?? {
  id: 1,
  email: 'test@example.com',
  name: 'testuser1',
};
const message = ref<string>('');
const result = ref<string>('');
const { token, fetchToken } = useFCM();

onMounted(async () => {
  // Notification Permission이 Granted이면 token을 가져옴
  if (Notification.permission === 'granted') {
    await getMessageToken();
  }
});

const requestNotificationPermission = (): void => {
  Notification.requestPermission()
    .then((permission) => {
      if (permission === 'granted') {
        console.log('Notification permission granted.');
        message.value = 'Notification permission granted.';
        return;
      }
      console.log('Notification permission denied.');
      message.value = 'Notification permission denied.';
    })
    .catch((error) => {
      console.error(
        'Error occurred while asking for notification permission:',
        error,
      );
      message.value = `Error occurred while asking for notification permission. error: ${error}`;
    });
};

const getMessageToken = async (): Promise<void> => {
  if (token.value === '') {
    await fetchToken().then(() => {
      message.value = token.value;
    });
  }
  message.value = token.value;
};

const saveUserToken = async (): Promise<void> => {
  try {
    const res = await FETCH_FCM_TOKEN.saveToken({
      user_id: user.id,
      token: token.value,
    });

    result.value = res.data;
  } catch (error) {
    console.error('Error occurred while saving token:', error);
    result.value = `Error occurred while saving token. error: ${(error as Error).message}`;
  }
};

const removeUserToken = async (): Promise<void> => {
  try {
    const res = await FETCH_FCM_TOKEN.deleteToken({
      user_id: user.id,
      token: token.value,
    });

    result.value = res.data;
  } catch (error) {
    console.error('Error occurred while removing token:', error);
    result.value = `Error occurred while removing token. error: ${(error as Error).message}`;
  }
};
</script>
