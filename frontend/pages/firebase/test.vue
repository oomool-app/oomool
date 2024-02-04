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
    </div>
  </div>
</template>

<script setup lang="ts">
const message = ref<string | null>('');
const { token } = useFCM();

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

const getMessageToken = (): void => {
  message.value = token.value;
};
</script>
