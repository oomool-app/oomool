<template>
  <div>
    <Card
      class="w-100% h-auto bg-gradient-to-t from-[#A8BDF9] to-primary border-0 rounded-xl drop-shadow-xl transition-transform transform text-ellipsis overflow-hidden"
    >
      <!--진행중-->
      <div
        v-if="startDateIsBeforeToday && endDateIsAfterToday"
        @click="$router.push({ path: `room/${rooms.roomid}` })"
      >
        <CardHeader>
          <div
            class="flex bg-white font-bold px-5 py-10 w-70 h-36 shadow-inner rounded-xl justify-center items-center"
          >
            {{ props.rooms.question }}
          </div>
        </CardHeader>
        <CardContent>
          <div
            class="text-white text-2xl tracking-tighter not-italic font-bold leading-6"
          >
            {{ props.rooms.title }}
          </div>
        </CardContent>
        <CardFooter>
          <Button class="bg-[#04E260] text-black w-15 h-10">진행중</Button>
        </CardFooter>
      </div>

      <!--시작대기-->
      <div v-else-if="!startDateIsBeforeToday">
        <Popover>
          <PopoverTrigger as-child>
            <CardHeader as-child>
              <div
                class="flex bg-white text-[#6D6D6D] font-bold px-5 py-10 w-70 h-36 shadow-inner rounded-xl justify-center items-center"
              >
                아직 준비 중이에요!
              </div>
            </CardHeader>
            <CardContent as-child>
              <div
                class="text-white text-2xl tracking-tighter not-italic font-bold leading-6"
              >
                {{ props.rooms.title }}
              </div>
            </CardContent>
            <CardFooter as-child>
              <Button class="bg-[#F1D302] text-black w-15 h-10"
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
        @click="$router.push({ path: `guess/${rooms.roomid}` })"
      >
        <CardHeader>
          <div
            class="flex bg-white text-[#6D6D6D] font-bold px-5 py-10 w-70 h-36 shadow-inner rounded-xl justify-center items-center"
          >
            종료된 방입니다.
          </div>
        </CardHeader>
        <CardContent>
          <div
            class="text-white text-2xl tracking-tighter not-italic font-bold leading-6"
          >
            {{ props.rooms.title }}
          </div>
        </CardContent>
        <CardFooter>
          <Button class="bg-[#B0B0B0] text-black w-15 h-10">종료됨</Button>
        </CardFooter>
      </div>
    </Card>
  </div>
</template>

<script setup lang="ts">
interface Room {
  roomid: number;
  sequence: number;
  question: string;
  title: string;
  startdate: string;
  enddate: string;
}

const props = defineProps<{
  rooms: Room;
}>();

const today: Date = new Date();

const startDateIsBeforeToday =
  new Date(props.rooms.startdate as string) < today;
const endDateIsAfterToday = new Date(props.rooms.enddate as string) > today;
const timeBeforeStart =
  new Date(props.rooms.startdate as string).getTime() - today.getTime();
const dayBeforeStart = Math.floor(timeBeforeStart / (1000 * 60 * 60 * 24));
const minBeforeStart = Math.floor(timeBeforeStart / 1000 / 60);
const hourBeforeStart = Math.floor(timeBeforeStart / (1000 * 60 * 60));
</script>
