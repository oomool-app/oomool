<template>
  <div class="mb-6 bg-background">
    <div
      class="top-container mb-6 text-center flex justify-between items-center p-6 font-bold"
    >
      <BackButton color="#61339b" />
      <h1 class="text-2xl font-bold text-primary">알림</h1>
      <div class="mr-4"></div>
    </div>

    <div
      class="body-container max-w-md text-gray-900 dark:text-white dark:divide-gray-700"
    >
      <div v-if="unread !== undefined" class="divide-y divide-gray-400">
        <div v-for="message in unread" :key="message.title">
          <MessageCard
            :messages="message"
            class="pt-3 pb-3 h-20 bg-[#F1EBFC]"
          ></MessageCard>
        </div>
      </div>
      <div v-if="read !== undefined" class="divide-y divide-gray-400">
        <div v-for="message in read" :key="message.title">
          <MessageCard :messages="message" class="pt-3 pb-3 h-20"></MessageCard>
        </div>
      </div>
    </div>
    <div
      v-if="read?.length === 0 && unread?.length === 0"
      class="flex flex-col items-center m-6 mt-10"
    >
      <p class="m-12 font-semibold">아직 알림이 없어요</p>
      <img class="w-5/6" src="/img/emptyGhost.png" alt="알림없음" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '~/stores/userStore';
import { type IGetAllNotificationsInput } from '../repository/modules/interface/notifications.interface';

const { $api } = useNuxtApp();

const userStore = useUserStore();

const read = ref();
const unread = ref();
const getAllNotifications = async (): Promise<void> => {
  try {
    const storedUser = userStore.getStoredUser();

    if (storedUser != null) {
      const userId: IGetAllNotificationsInput = {
        userId: storedUser.id,
      };
      const response = await $api.notifications.getAllNotifications(userId);
      read.value = response.data.read;
      unread.value = response.data.unread;
      sortByCreateat();
    }
  } catch (error) {
    console.error('Error while fetching room list:', error);
  }
};

// 안 읽은 알림은 어차피 제일 최신임
// 그래서 읽은 알림 생성 기준으로 정렬하고 출력해주면 됨
const sortByCreateat = (): void => {
  read.value.sort(
    (a: any, b: any) =>
      new Date(b.created_at as string).getTime() -
      new Date(a.created_at as string).getTime(),
  );
};

onMounted(async () => {
  await getAllNotifications();
});
</script>
<style scoped>
.top-container {
  animation: fade-in 0.5s ease-in-out;
}

.body-container {
  animation: fade-in2 0.6s ease-in-out;
}

@keyframes fade-in {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

@keyframes fade-in2 {
  0% {
    opacity: 0;
  }

  20% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>
