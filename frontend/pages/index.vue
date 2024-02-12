<template>
  <div class="bg-primary">
    <div class="top-container flex justify-between pt-6">
      <!--로고-->
      <img src="/img/logo.png" class="pl-4 w-40 h-auto" />

      <!--알림-->
      <MessageButton as-child></MessageButton>
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
      class="bottom-container flex flex-col gap-5 w-375 h-803 pt-3 bg-white rounded-t-lg"
    >
      <h1
        class="text-2xl font-bold p-4 sticky top-0 bg-white h-12 rounded-t-lg z-40"
      >
        나의 방 목록
      </h1>

      <!--방 유형 선택 버튼-->
      <div class="flex flex-row">
        <div
          v-show="selected1"
          class="flex flex-row pl-6 pr-28 space-x-3 items-center drop-shadow-xl"
        >
          <img src="/img/maintap1.png" class="w-24 h-auto" @click="select1" />
          <img
            src="/img/maintap2_2.png"
            class="w-16 h-8 justify-self-center"
            @click="select2"
          />
          <img
            src="/img/maintap3_2.png"
            class="w-16 h-8 justify-self-end"
            @click="select3"
          />
        </div>
        <div
          v-show="selected2"
          class="flex flex-row pl-6 pr-28 space-x-3 items-center drop-shadow-xl"
        >
          <img
            src="/img/maintap1_2.png"
            class="w-16 h-8 justify-self-start"
            @click="select1"
          />
          <img src="/img/maintap2.png" class="w-24 h-auto" @click="select2" />
          <img
            src="/img/maintap3_2.png"
            class="w-16 h-8 justify-self-end"
            @click="select3"
          />
        </div>
        <div
          v-show="selected3"
          class="flex flex-row pl-6 pr-28 space-x-3 justify-items-center items-center drop-shadow-xl"
        >
          <img
            src="/img/maintap1_2.png"
            class="w-16 h-8 justify-self-start"
            @click="select1"
          />
          <img src="/img/maintap2_2.png" class="w-16 h-8" @click="select2" />
          <img
            src="/img/maintap3.png"
            class="w-24 h-auto justify-self-end"
            @click="select3"
          />
        </div>
      </div>

      <div class="flex flex-col justify-center pl-6 pr-6 pb-10 gap-3 z-0">
        <div v-if="selected1" class="gap-3 space-y-8 pt-3">
          <div v-for="room in rooms" :key="room.title">
            <RoomCard :rooms="room"></RoomCard>
          </div>
        </div>
        <div v-else-if="selected2" class="gap-3 space-y-8 pt-3">
          <div v-for="temproom in temprooms" :key="temproom.invite_code">
            <TempRoomCard :temprooms="temproom"></TempRoomCard>
          </div>
        </div>
        <div v-else-if="selected3" class="gap-3 space-y-8 pt-3">
          <div v-for="room in rooms" :key="room.title">
            <RoomCard :rooms="room"></RoomCard>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useUserStore } from '~/stores/userStore';
import { useRouter } from 'vue-router';
import {
  type IGetRoomListInput,
  type IGetTempRoomListInput,
} from '../repository/modules/interface/users.interface';
import type { ISaveTokenInput } from '~/repository/modules/interface/pushNotifications.interface';

useBodyColor('#61339B');
const { token, fetchToken } = useFCM();
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
    }
  } catch (error) {
    console.error('Error while fetching room list:', error);
  }
};

const today: Date = new Date();

const inProgressRooms = (rooms: any): any => {
  return rooms.filter(
    (room: any) => new Date(room.setting.end_date as string) > today,
  );
};
const notProgressRooms = (rooms: any): any => {
  return rooms.filter(
    (room: any) => new Date(room.setting.end_date as string) < today,
  );
};

const selected1: Ref<boolean> = ref(true);
const selected2: Ref<boolean> = ref(false);
const selected3: Ref<boolean> = ref(false);

const select1 = async (): Promise<void> => {
  await getRoomList();
  if (rooms.value != null) {
    rooms.value = inProgressRooms(rooms.value);
    // 생성일 기준 정렬
    sortByCreatedat();
  }
  selected1.value = true;
  selected2.value = false;
  selected3.value = false;
};

const select2 = async (): Promise<void> => {
  sortByCreatedatTemp();
  selected1.value = false;
  selected2.value = true;
  selected3.value = false;
};

const select3 = async (): Promise<void> => {
  await getRoomList();
  if (rooms.value != null) {
    rooms.value = notProgressRooms(rooms.value);
  }
  selected1.value = false;
  selected2.value = false;
  selected3.value = true;
};

// 생성일 기준 방 정렬
const sortByCreatedat = (): void => {
  rooms.value.sort(
    (a: any, b: any) =>
      new Date(b.created_at as string).getTime() -
      new Date(a.created_at as string).getTime(),
  );
};

// 생성일 기준 대기방 정렬
const sortByCreatedatTemp = (): void => {
  temprooms.value.sort(
    (a: any, b: any) =>
      new Date(b.created_at as string).getTime() -
      new Date(a.created_at as string).getTime(),
  );
};

const isSupported = (): boolean => {
  return 'Notification' in window && 'serviceWorker' in navigator;
};

onMounted(async () => {
  if (isSupported()) {
    if (Notification.permission === 'default') {
      await router.push({ path: '/notification' });
      return;
    }
    if (Notification.permission === 'granted' && token.value === '') {
      await fetchToken().then(saveToken);
    }
  }
  await getRoomList();
  await getTempRoomList();
  await select1();
});

const saveToken = async (): Promise<void> => {
  const storedUser = userStore.getStoredUser(); // storedUser를 피니아를 통해 불러옴
  if (storedUser != null) {
    const tokens: ISaveTokenInput = {
      user_id: storedUser.id,
      token: token.value,
    };
    console.log(tokens);
    const response = await $api.pushNotifications.saveToken(tokens);
    console.log(response.data);
  }
};
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
