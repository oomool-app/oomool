<template>
  <div>
  <Card class=" w-100% h-auto bg-gradient-to-t from-[#A8BDF9] to-primary border-0 rounded-xl drop-shadow-xl transition-transform transform hover:filter hover:brightness-90" @click="$router.push({ path: `room/${rooms.roomid }`})">
    <div v-if="startDateIsBeforeToday && endDateIsAfterToday">
      <CardHeader >
        <div class="flex bg-white font-bold px-5 py-10 w-70 h-40 shadow-inner rounded-xl justify-center items-center">{{ props.rooms.question }}</div>
      </CardHeader>
      <CardContent>
          <div class=" text-white text-2xl tracking-tighter not-italic font-bold leading-6">{{ props.rooms.title}}</div>
      </CardContent>
      <CardFooter >
        <Button class="bg-[#04E260] text-black w-15 h-10">진행중</Button>
      </CardFooter>
    </div>
    <div v-else-if="!startDateIsBeforeToday">
      <CardHeader >
        <div class="flex bg-white text-[#6D6D6D] font-bold px-5 py-10 w-70 h-40 shadow-inner rounded-xl justify-center items-center">아직 준비 중이에요!</div>
      </CardHeader>
      <CardContent>
          <div class=" text-white text-2xl tracking-tighter not-italic font-bold leading-6">{{ props.rooms.title }}</div>
      </CardContent>
      <CardFooter >
        <Button class="bg-[#F1D302] text-black w-15 h-10">시작대기</Button>
      </CardFooter>
    </div>
    <div v-else-if="!endDateIsAfterToday">
      <CardHeader >
        <div class="flex bg-white text-[#6D6D6D] font-bold px-5 py-10 w-70 h-40 shadow-inner rounded-xl justify-center items-center">종료된 방입니다.</div>
      </CardHeader>
      <CardContent>
          <div class=" text-white text-2xl tracking-tighter not-italic font-bold leading-6">{{ props.rooms.title }}</div>
      </CardContent>
      <CardFooter >
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

  const startDateIsBeforeToday = new Date(props.rooms.startdate as string) < today;
  const endDateIsAfterToday = new Date(props.rooms.enddate as string) > today;

</script>
