<template>
  <div class="box flex-col p-6">
    <div class="flex justify-between">
      <BackButton color="black"></BackButton>
      <h1 class="text-xl font-bold">방 만들기</h1>
      <div class="mr-6"></div>
    </div>
    <MakeRoomProgress :cur-page="'2'"></MakeRoomProgress>
    <div class="font-bold mb-4">우리들, 어떤 사이예요?</div>
    <div>매일 제공되는 질문의 유형이 결정돼요!</div>
    <div v-if="typeCheck" class="text-red-600">질문의 유형을 정해주세요!</div>
    <MakeRoomTypeButton
      :game-type="store.type"
      @update-type="handleUpdateType"
    ></MakeRoomTypeButton>
    <div>
      <NuxtLink to="number"><Button @click="check">다음</Button></NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
const store = useMakeRoomStore();

function handleUpdateType(title: string): void {
  store.type = title;
}
const typeCheck = ref<boolean>(false);
const check = (e: any): void => {
  if (store.type === '') {
    typeCheck.value = true;
    e.preventDefault();
  }
};
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
