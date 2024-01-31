<template>
  <div>
    <div class="mb-6 bg-background">
      <div
        class="mb-6 text-center flex justify-center self-stretch relative pt-7 pb-7 font-bold"
      >
        <BackButton color="primary" class="absolute left-1 pt-1" />
        <h1 class="text-2xl font-bold text-primary">알림</h1>
      </div>

      <div
        class="max-w-md text-gray-900 divide-y divide-gray-400 dark:text-white dark:divide-gray-700"
      >
        <div v-for="message in messages" :key="message.messageid">
          <MessageCard :messages="message" class="pt-3 pb-3"></MessageCard>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const messages = [
  {
    messageid: 1,
    type: '생성',
    roomid: 1,
    roomtitle: '공통플젝 A809',
    title: '오늘의 질문이 생성되었습니다.',
    createdtime: '2024-01-29 10:08',
    readtime: '2024-01-29 10:09',
  },
  {
    messageid: 2,
    type: '답변 등록',
    roomid: 2,
    roomtitle: '서울8반 화이팅',
    title: '내 마니또가 답변을 등록했어요!',
    createdtime: '2024-01-29 14:13',
    readtime: '2024-01-30',
  },
  {
    messageid: 3,
    type: '종료',
    roomid: 3,
    roomtitle: '소문난 칠공주',
    title: '모든 세션이 종료되었어요. 결과를 확인하세요!',
    createdtime: '2024-01-22',
    readtime: '2024-01-23',
  },
  {
    messageid: 4,
    type: '생성',
    roomid: 4,
    roomtitle: '싸피초등학교 16기',
    title: '6일차 질문이 생성되었습니다.',
    createdtime: '2024-01-01',
    readtime: '2024-01-30',
  },
];
</script>
