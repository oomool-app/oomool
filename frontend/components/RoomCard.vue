<template>
  <div>
    <Card
      class="w-100% h-auto bg-gradient-to-t from-[#A8BDF9] to-primary border-0 rounded-xl drop-shadow-xl transition-transform transform text-ellipsis overflow-hidden"
    >
      <!--진행중-->
      <div
        v-if="startDateIsBeforeToday && endDateIsAfterToday"
        class="flex flex-col"
        @click="$router.push({ path: `room/${rooms.room_uid}` })"
      >
        <CardHeader class="px-3.5 pt-3.5 pb-4">
          <div
            class="flex flex-col bg-white h-28 shadow-inner rounded-xl items-center relative px-8"
          >
            <h1 class="z-10 text-sm text-purple-800 absolute top-1 font-bold">
              오늘의 질문
            </h1>
            <h1 class="z-10 pt-9 font-extrabold">
              {{ props.rooms?.daily_question?.question }}
            </h1>
            <img
              src="/img/questionGhost.png"
              class="w-12 h-auto absolute bottom-2 right-2 opacity-30 z-0"
            />
          </div>
        </CardHeader>
        <CardContent class="pl-3 pt-0 pr-0 pb-7 flex flex-row relative">
          <div class="text-white text-xl not-italic h-3">
            {{ props.rooms?.setting?.title }}
          </div>
          <Button
            class="bg-[#F1D302] text-black w-14 h-6 text-center align-middle text-xs rounded-full absolute right-2"
            ><div v-if="dayBeforeEnd >= 1">D-{{ dayBeforeEnd }}</div>
            <div v-else>D-0</div>
          </Button>
        </CardContent>
      </div>

      <!--시작대기-->
      <div v-else-if="!startDateIsBeforeToday">
        <CardHeader class="px-3.5 pt-3.5 pb-4">
          <div
            class="flex flex-col bg-white text-[#6D6D6D] font-bold px-3 py-10 w-70 h-24 shadow-inner rounded-xl justify-center items-center"
          >
            아직 게임이 시작되지 않았어요
            <div v-if="dayBeforeStart > 1" class="flex flex-row">
              <h1 class="text-primary pr-2">{{ dayBeforeStart }}</h1>
              일 후에 시작됩니다.
            </div>
            <div v-else class="flex flex-row">
              <h1 class="text-primary pr-2">내일</h1>
              게임이 시작됩니다!
            </div>
          </div>
        </CardHeader>
        <CardContent class="px-3 pt-0">
          <div class="text-white text-xl not-italic h-3">
            {{ props.rooms?.setting?.title }}
          </div>
        </CardContent>
      </div>

      <!--종료됨-->
      <div v-else-if="!endDateIsAfterToday" @click="isCompletedToGuess">
        <CardHeader class="px-3.5 pt-3.5 pb-4">
          <div
            class="flex flex-col bg-white h-24 shadow-inner rounded-xl items-center relative px-8"
          >
            <h1 class="z-10 pt-9 font-extrabold text-gray-600">
              종료된 방입니다.
            </h1>
            <img
              src="/img/endGhost.png"
              class="w-12 h-auto absolute bottom-2 right-2 opacity-30 z-0"
            />
          </div>
        </CardHeader>
        <CardContent class="pl-3 pt-0 pr-0 pb-7 flex flex-row relative">
          <div class="text-white text-xl not-italic h-3">
            {{ props.rooms?.setting?.title }}
          </div>
        </CardContent>
      </div>
    </Card>
  </div>
</template>

<script setup lang="ts">
const router = useRouter();
const { $api } = useNuxtApp();
interface Room {
  room_uid: string;
  daily_question: {
    daily_date: string;
    question: string;
    sequence: number;
    level: number;
  };
  setting: {
    title: string;
    start_date: string;
    end_date: string;
    question_type: string;
    max_member: number;
  };
}

const props = defineProps<{
  rooms: Room;
}>();

// 변수들을 저장할 ref 변수들 선언
const startDateIsBeforeToday = ref(false);
const endDateIsAfterToday = ref(false);
const dayBeforeStart = ref(0);
const dayBeforeEnd = ref(0);
const getTime = async (): Promise<void> => {
  // 현재 시각 불러오기
  const today: Date = new Date();

  // 시작일
  const startDate = new Date(props.rooms.setting.start_date as string);

  // 종료일
  const endDate = new Date(props.rooms.setting.end_date as string);

  // 기본이 9시로 설정되어 있으므로 0시로 맞춰줘야 함
  startDate.setHours(0);
  endDate.setHours(24);

  // 게임 시작일이 지났는지
  startDateIsBeforeToday.value = startDate < today;

  // 게임 종료일이 아직 안왔는지
  endDateIsAfterToday.value = endDate > today;

  // 시작일까지 얼마나 남았는지
  const timeBeforeStart = startDate.getTime() - today.getTime();

  // 시작일까지 몇일 남았는지
  dayBeforeStart.value = Math.ceil(timeBeforeStart / (1000 * 60 * 60 * 24));

  // 종료일까지 남은 시간
  const timeBeforeEnd = endDate.getTime() - today.getTime();

  // 종료까지 몇일 남았는지
  dayBeforeEnd.value = Math.ceil(timeBeforeEnd / (1000 * 60 * 60 * 24));
};
const correctGuess = ref();
const isCompletedToGuess = async (): Promise<void> => {
  const userId = useUserStore().getStoredUser()?.id;
  const roomUid = props.rooms.room_uid;
  const response = await $api.players.getMyManitto({ roomUid, userId });
  correctGuess.value = response.data.guess;
  if (correctGuess.value === 'true' || correctGuess.value === 'false') {
    await router.push({ path: `final/${roomUid}` });
  } else if (correctGuess.value === 'null') {
    await router.push({ path: `guess/${roomUid}` });
  }
};

onBeforeUpdate(async () => {
  await getTime();
});

onMounted(async () => {
  await getTime();
});
</script>
