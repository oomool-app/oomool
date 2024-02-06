<template>
  <div class="bg-primary">
    <div class="top-container flex justify-between pt-6">
      <!--로고-->
      <img src="/img/logo.png" class="pl-4 w-40 h-auto" />

      <!--알림-->
      <h1 class="text-2xl font-bold text-left pl-6 text-white"></h1>
      <MessageButton as-child class="absolute right-5"></MessageButton>
    </div>

    <!--방 만들기, 방 참여하기 버튼 -->
    <div
      class="mid-container flex flex-row justify-center pt-20 pb-5 gap-2 self-stretch space-x-4 text-center"
    >
      <MakeRoomButton></MakeRoomButton>
      <InputCodeModal></InputCodeModal>
    </div>

    <!-- 방 목록-->
    <div
      class="bottom-container flex flex-col gap-5 w-375 h-803 pt-3 pr-15 bg-white rounded-t-lg"
    >
      <h1
        class="text-2xl font-bold p-4 sticky top-0 bg-white h-16 rounded-t-lg z-40"
      >
        나의 방 목록
      </h1>

      <div class="flex flex-col justify-center pl-6 pr-6 gap-3 space-y-4 z-0">
        <div v-for="room in rooms" :key="room.title">
          <RoomCard :rooms="room"></RoomCard>
        </div>
        <div v-for="temproom in temprooms" :key="temproom.invite_code">
          <TempRoomCard :temprooms="temproom"></TempRoomCard>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '~/stores/userStore';
import { useRouter } from 'vue-router';
import { ref } from 'vue';
import {
  type IGetRoomListInput,
  type IGetTempRoomListInput,
} from '../repository/modules/interface/users.interface';

useBodyColor('#61339B');

const userStore = useUserStore();
const router = useRouter();
const { $api } = useNuxtApp();

const rooms = ref();
const temprooms = ref();

// 유저아이디로 방 목록 가져오기
const getRoomList = async (): Promise<void> => {
  try {
    const storedUser = userStore.getStoredUser();

    if (storedUser != null) {
      const userId: IGetRoomListInput = {
        userId: storedUser.id,
      };

      const response = await $api.users.getRoomList(userId);
      rooms.value = response.data;
    } else {
      // 사용자 정보가 없을 경우 로그인 페이지로 리다이렉트
      await router.push('/login');
    }
  } catch (error) {
    console.error('Error while fetching room list:', error);
  }
};

// 유저아이디로 대기방 목록 가져오기
const getTempRoomList = async (): Promise<void> => {
  try {
    const storedUser = userStore.getStoredUser();

    if (storedUser != null) {
      const userId: IGetTempRoomListInput = {
        userId: storedUser.id,
      };

      const response = await $api.users.getTempRoomList(userId);
      temprooms.value = response.data.temp_room;
    } else {
      // 사용자 정보가 없을 경우 로그인 페이지로 리다이렉트
      await router.push('/login');
    }
  } catch (error) {
    console.error('Error while fetching room list:', error);
  }
};

onBeforeMount(async () => {
  const storedUser = userStore.getStoredUser();
  if (storedUser == null) {
    await router.push('/login');
  }

  await getRoomList();
  await getTempRoomList();
  if (Notification.permission === 'default') {
    await router.push({ path: '/notification' });
  }
});
</script>

<style scoped lang="scss">
body {
  background-color: #61339b;
}

.top-container {
  animation: fade-in 0.5s ease-in-out;
}

.mid-container {
  animation: fade-in2 0.6s ease-in-out;
}

.bottom-container {
  animation: fade-in3 0.6s ease-in-out;
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
@keyframes fade-in3 {
  0% {
    opacity: 0;
  }

  40% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>
