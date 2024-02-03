<template>
  <div class="box">
    <div class="mb-6 p-6 text-center flex justify-between pt-7 pb-7 font-bold">
      <BackButton color="#61339B" />
      <h1 class="text-2xl font-bold text-primary">멤버 목록</h1>
      <div class="mr-6"></div>
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
    profileUrl: '/img/bearGhost.png',
  },
  {
    id: 2,
    name: '김성수',
    background: '#FF5733',
    profileUrl: '/img/sangwooGhost.png',
  },
  {
    id: 3,
    name: '김현지',
    background: '#FFD700',
    profileUrl: '/img/rabbitGhost.png',
  },
  {
    id: 4,
    name: '전은평',
    background: '#33A1FF',
    profileUrl: '/img/musicGhost.png',
  },
  {
    id: 5,
    name: '김병현',
    background: '#607D8B',
    profileUrl: '/img/catGhost.png',
  },
  {
    id: 6,
    name: '정필모',
    background: '#795548',
    profileUrl: '/img/sangwooGhost.png',
  },
]);

const see = ref(false);
</script>
<style scoped>
.box {
  animation: fade-in 0.5s ease-in-out;
}
@keyframes fade-in {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}
</style>
