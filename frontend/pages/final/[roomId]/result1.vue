<template>
  <div class="h-screen flex flex-col items-center justify-center">
    <ResultSuccessCard
      v-if="guessIsAlright === 'success'"
      :manitto="manitto"
    ></ResultSuccessCard>
    <ResultFailCard
      v-else-if="guessIsAlright === 'fail'"
      :manitto="manitto"
    ></ResultFailCard>
    <div class="mt-8 flex flex-col items-center shadow-lg go-to-result">
      <NuxtLink :to="`/final/${roomUid}/result2`">
        <Button class="rounded-xl"
          ><svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
            fill="currentColor"
            class="w-6 h-6 inline pb-1"
          >
            <path
              d="M19.5 22.5a3 3 0 0 0 3-3v-8.174l-6.879 4.022 3.485 1.876a.75.75 0 1 1-.712 1.321l-5.683-3.06a1.5 1.5 0 0 0-1.422 0l-5.683 3.06a.75.75 0 0 1-.712-1.32l3.485-1.877L1.5 11.326V19.5a3 3 0 0 0 3 3h15Z"
            />
            <path
              d="M1.5 9.589v-.745a3 3 0 0 1 1.578-2.642l7.5-4.038a3 3 0 0 1 2.844 0l7.5 4.038A3 3 0 0 1 22.5 8.844v.745l-8.426 4.926-.652-.351a3 3 0 0 0-2.844 0l-.652.351L1.5 9.589Z"
            />
          </svg>
          &nbsp;마니또 답변 보러가기</Button
        >
      </NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
const route = useRoute();
const roomUid = route.params.roomId as string;
const { $api } = useNuxtApp();
const manitto = ref();
const guessIsAlright = ref();

// 마니또 및 예측 여부 조회
const getMyManitto = async (): Promise<void> => {
  const userId = useUserStore().getStoredUser()?.id;
  const response = await $api.players.getMyManitto({ roomUid, userId });
  manitto.value = response.data.manitto;
  if (response.data.guess === 'true') {
    guessIsAlright.value = 'success';
  } else {
    guessIsAlright.value = 'fail';
  }
};
onMounted(async () => {
  await getMyManitto();
});
</script>
<style scoped>
.go-to-result {
  animation: fade-in 3s;
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
