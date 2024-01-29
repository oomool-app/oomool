<template>
  <div class="bg-primary">
    <div class="flex justify-between pt-4">
      <!--로고-->
      <h1 class="text-2xl font-bold text-left pl-6 text-white">OooMool</h1>
      <!--알림-->
      <MessageButton class="absolute right-7"></MessageButton>
    </div>

    <!--방 만들기, 방 참여하기 버튼 -->
    <div
      class="flex flex-row justify-center pt-20 pb-5 gap-2 self-stretch space-x-4 text-center"
    >
      <MakeRoomButton></MakeRoomButton>
      <InputCodeModal></InputCodeModal>
    </div>

    <!-- 방 목록-->
    <div
      class="flex flex-col gap-5 w-375 h-803 pt-5 pr-15 bg-white rounded-t-lg"
    >
      <h1 class="text-2xl font-bold p-4">나의 방 목록</h1>

      <div
        class="flex flex-col self-end justify-center pl-6 pr-6 gap-5 space-y-4"
      >
        <div v-for="room in rooms" :key="room.title">
          <RoomCard :rooms="room"></RoomCard>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const rooms = [
  {
    roomid: 1,
    sequence: 2,
    question: '내 마니또가 멋있어보였던 순간은?',
    title: '공통플젝 A809',
    startdate: '2024-01-25',
    enddate: '2024-01-30',
  },
  {
    roomid: 2,
    sequence: 3,
    question: '내 마니또의 첫인상은?',
    title: '서울8반 화이팅',
    startdate: '2024-01-26',
    enddate: '2024-02-03',
  },
  {
    roomid: 3,
    sequence: 7,
    question: ' ',
    title: '소문난 칠공주',
    startdate: '2024-01-01',
    enddate: '2024-01-07',
  },
  {
    roomid: 4,
    sequence: 2,
    question: '내 마니또에게 배울 점이 있다면?',
    title: '싸피초등학교 16기',
    startdate: '2024-01-30',
    enddate: '2024-02-08',
  },
];
</script>
