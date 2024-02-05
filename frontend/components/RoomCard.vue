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
            class="flex bg-white font-bold h-24 shadow-inner rounded-xl justify-center items-center"
          >
            {{ props.rooms?.daily_question?.question }}
          </div>
        </CardHeader>
        <CardContent class="px-3 pt-0">
          <div class="text-white text-xl not-italic h-3">
            {{ props.rooms?.setting?.title }}
          </div>
        </CardContent>
        <CardFooter class="px-3 pb-3 pl-4">
          <Button
            class="bg-[#04E260] text-black w-14 h-6 text-center align-middle text-xs rounded-full"
            >진행중</Button
          >
        </CardFooter>
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
            <CardFooter class="px-3 pb-3 pl-4">
              <Button
                class="bg-[#F1D302] text-black w-14 h-6 text-center align-middle text-xs rounded-full"
                >시작대기</Button
              >
            </CardFooter>
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
          <CardFooter class="px-3 pb-3 pl-4">
            <Button
              class="bg-[#B0B0B0] text-black w-14 h-6 text-center align-middle text-xs rounded-full"
              >종료됨</Button
            >
          </CardFooter>
        </div>
      </div>
    </Card>
  </div>
</template>

<script setup lang="ts">
interface Room {
  room_uid: string;
  title: string;
  start_date: string;
  end_date: string;
  sequence: number;
  question: string;
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
</script>
