<template>
  <div>
    <div
      class="mb-6 text-center flex justify-center self-stretch relative pt-7 pb-7 font-bold"
    >
      <BackButton color="primary" class="absolute left-1 pt-1" />
      <h1 class="text-2xl font-bold text-primary">멤버 목록</h1>
    </div>
    <div class="flex flex-wrap justify-center">
      <div v-for="user in users" :key="user.id" class="m-2 rounded-lg">
        <ManitoCard :user="user" :see="see" class="text-primary"> </ManitoCard>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const users = ref([
  {
    id: 1,
    name: '박세정',
    background: '#FF33A1',
    profileUrl: '/img/곰유령.png',
  },
  {
    id: 2,
    name: '김성수',
    background: '#FF5733',
    profileUrl: '/img/소라게유령.png',
  },
  {
    id: 3,
    name: '김현지',
    background: '#FFD700',
    profileUrl: '/img/토끼유령.png',
  },
  {
    id: 4,
    name: '전은평',
    background: '#33A1FF',
    profileUrl: '/img/헤드셋유령.png',
  },
  {
    id: 5,
    name: '김병현',
    background: '#607D8B',
    profileUrl: '/img/고양이유령.png',
  },
  {
    id: 6,
    name: '정필모',
    background: '#795548',
    profileUrl: '/img/소라게유령.png',
  },
]);

const see = ref(false);
</script>
