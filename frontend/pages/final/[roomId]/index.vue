<template>
  <div class="total h-screen flex flex-col items-center justify-center">
    <ResultClickCard @click="goToResult"></ResultClickCard>
    <div class="text mt-10 flex flex-col items-center">
      <p class="text-primary font-bold text-2xl mb-1">카드를 눌러서</p>
      <p class="text-primary font-bold text-2xl">결과를 확인하세요!</p>
    </div>
  </div>
</template>

<script setup lang="ts">
const router = useRouter();
const route = useRoute();
const roomUid = route.params.roomId as string;
const goToResult = async (): Promise<void> => {
  await router.push({ path: `/final/${roomUid}/result1` });
};
</script>
<style scoped>
.total {
  animation: fade-in 1.2s ease-in-out;
}

.text {
  animation: text-zoom 0.6s ease-in-out;
}
@keyframes fade-in {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

@keyframes text-zoom {
  from {
    scale: 0.8;
  }

  to {
    scale: 1;
  }
}
</style>
