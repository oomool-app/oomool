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
        <Popover>
          <PopoverTrigger as-child>
            <CardHeader class="px-3.5 pt-3.5 pb-4">
              <div
                class="flex bg-white text-[#6D6D6D] font-bold px-3 py-10 w-70 h-24 shadow-inner rounded-xl justify-center items-center"
              >
                아직 준비 중이에요!
              </div>
            </CardHeader>
            <CardContent class="px-3 pt-0">
              <div class="text-white text-xl not-italic h-3">
                {{ props.rooms?.setting?.title }}
              </div>
            </CardContent>
          </PopoverTrigger>
          <PopoverContent>
            게임이 시작되지 않았습니다.
            <div v-if="dayBeforeStart >= 1">
              {{ dayBeforeStart }}일 후에 시작됩니다.
            </div>
            <div v-else-if="hourBeforeStart >= 1">
              {{ hourBeforeStart }}시간 후에 시작됩니다.
            </div>
            <div v-else>{{ minBeforeStart }}분 후에 시작됩니다.</div>
            <Button
              class="bg-[#F1D302] text-black w-14 h-6 text-center align-middle text-xs rounded-full absolute right-2"
              >D-7</Button
            >
          </PopoverContent>
        </Popover>
      </div>

      <!--종료됨-->
      <div
        v-else-if="!endDateIsAfterToday"
        @click="$router.push({ path: `guess/${rooms.room_uid}` })"
      >
        <CardHeader class="px-3.5 pt-3.5 pb-4">
          <div
            class="flex bg-white text-[#6D6D6D] font-bold px-3 py-10 w-70 h-24 shadow-inner rounded-xl justify-center items-center"
          >
            종료된 방입니다.
          </div>
        </CardHeader>
        <div class="h-20">
          <CardContent class="px-3 pt-0">
            <div class="text-white text-xl not-italic h-3">
              {{ props.rooms?.setting?.title }}
            </div>
          </CardContent>
        </div>
      </div>
    </Card>
  </div>
</template>

<script setup lang="ts">
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

const today: Date = new Date();

const startDateIsBeforeToday =
  new Date(props.rooms.setting.start_date as string) < today;
const endDateIsAfterToday =
  new Date(props.rooms.setting.end_date as string) > today;
const timeBeforeStart =
  new Date(props.rooms.setting.start_date as string).getTime() -
  today.getTime();
const dayBeforeStart = Math.floor(timeBeforeStart / (1000 * 60 * 60 * 24));
const minBeforeStart = Math.floor(timeBeforeStart / 1000 / 60);
const hourBeforeStart = Math.floor(timeBeforeStart / (1000 * 60 * 60));

const timeBeforeEnd =
  new Date(props.rooms.setting.end_date as string).getTime() - today.getTime();
const dayBeforeEnd = Math.floor(timeBeforeEnd / (1000 * 60 * 60 * 24));
const minBeforeEnd = Math.floor(timeBeforeEnd / 1000 / 60);
const hourBeforeEnd = Math.floor(timeBeforeEnd / (1000 * 60 * 60));
</script>
