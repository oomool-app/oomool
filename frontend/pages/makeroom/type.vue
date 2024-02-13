<template>
  <div class="box grid grid-rows-[6rem,2rem,6rem,5rem] h-screen p-6">
    <div class="grid grid-cols-3">
      <NuxtLink class="flex items-center" to="name" replace>
        <DefaultBackButton color="black"></DefaultBackButton>
      </NuxtLink>
      <div class="flex items-center justify-center text-2xl font-extrabold">
        방 만들기
      </div>
    </div>
    <div>
      <MakeRoomProgress :cur-page="'2'"></MakeRoomProgress>
    </div>
    <div>
      <div class="font-bold text-xl pt-2">우리들, 어떤 사이예요?</div>
      <div class="text-sm">매일 제공되는 질문의 유형이 결정돼요!</div>
    </div>
    <div>
      <MakeRoomTypeButton
        :game-type="store.type"
        @update-type="handleUpdateType"
      ></MakeRoomTypeButton>
      <div v-if="typeCheck" class="text-red-600 mt-2">
        질문의 유형을 정해주세요!
      </div>
    </div>
    <div class="flex justify-center items-end pb-6">
      <NuxtLink class="w-full rounded-full" to="number" replace>
        <Button class="w-full rounded-full text-lg" @click="check">다음</Button>
      </NuxtLink>
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
