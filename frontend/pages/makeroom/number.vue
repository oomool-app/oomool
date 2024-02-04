<template>
  <div class="box flex-col p-6">
    <div class="flex justify-between">
      <BackButton color="black"></BackButton>
      <h1 class="text-xl font-bold">방 만들기</h1>
      <div class="mr-6"></div>
    </div>
    <MakeRoomProgress :cur-page="'3'"></MakeRoomProgress>
    <div class="font-bold mb-4">몇 명이 함께하나요?</div>
    <div>최소 3명부터 최대 30명까지 선택할 수 있어요.</div>
    {{ store.number }}
    <MakeRoomNumber
      :people-number="store.number"
      @update-number="handleUpdateNumber"
    ></MakeRoomNumber>
    <div>
      <NuxtLink to="date"><Button>다음</Button></NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
const store = useMakeRoomStore();

function handleUpdateNumber(number: number): void {
  store.number = number;
}
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
