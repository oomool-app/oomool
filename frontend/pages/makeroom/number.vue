<template>
  <div class="box grid grid-rows-[6rem,2rem,6rem,5rem] h-screen p-6">
    <div class="grid grid-cols-3">
      <div class="flex items-center">
        <BackButton color="black"></BackButton>
      </div>
      <div class="flex items-center justify-center text-2xl font-extrabold">
        방 만들기
      </div>
    </div>
    <div>
      <MakeRoomProgress :cur-page="'3'"></MakeRoomProgress>
    </div>
    <div>
      <div class="font-bold text-xl pt-2">몇 명이 함께하나요?</div>
      <div class="text-sm">최소 3명부터 최대 30명까지 선택할 수 있어요.</div>
    </div>
    <div class="flex flex-col">
      <div class="flex justify-center items-center text-2xl font-bold mb-3">
        {{ store.number }} 명
      </div>
      <MakeRoomNumber
        :people-number="store.number"
        @update-number="handleUpdateNumber"
      ></MakeRoomNumber>
    </div>
    <div class="flex justify-center items-end pb-6">
      <NuxtLink class="w-full rounded-full" to="date" replace>
        <Button class="w-full rounded-full text-lg">다음</Button>
      </NuxtLink>
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
