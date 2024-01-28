<template>
  <div>
    <div class="mb-6 bg-background">
      <BackButton color="black" />
      <h1 class="text-2xl font-bold pb-5">알림</h1>

      <div class="max-w-md text-gray-900 divide-y divide-gray-200 dark:text-white dark:divide-gray-700">
        <div v-for="message in messages" :key="message.messageid">
          <MessageCard :messages="message"></MessageCard>
        </div>
      </div>
    </div>
  </div>
</template>


<script setup lang="ts">
const messages = [
  {
    messageid: 1,
    roomid: 1,
    roomtitle : '공통플젝 A809',
    title: '7일차 질문이 생성되었습니다.',
    createdtime : '2024-01-26',
    readtime : '2024-01-27',
  },
  {
    messageid: 2,
    roomid: 2,
    roomtitle : '서울8반 화이팅',
    title: '2일차 질문이 생성되었습니다.',
    createdtime : '2024-01-26',
    readtime : '2024-01-30',
  },
  {
    messageid: 3,
    roomid: 3,
    roomtitle : '소문난 칠공주',
    title: '3일차 질문이 생성되었습니다.',
    createdtime : '2024-01-22',
    readtime : '2024-01-23',
  },
  {
    messageid: 4,
    roomid: 4,
    roomtitle : '싸피초등학교 16기',
    title: '6일차 질문이 생성되었습니다.',
    createdtime : '2024-01-01',
    readtime : '2024-01-30',
  },
]

</script>
