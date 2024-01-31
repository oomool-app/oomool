<template>
  <div class="h-screen flex flex-col items-center justify-center">
    <ResultSuccessCard v-if="IsSuccess"></ResultSuccessCard>
    <ResultFailCard v-if="!IsSuccess"></ResultFailCard>
    <div class="mt-10 flex flex-col items-center">
      <NuxtLink :to="`/final/${roomId}/result2`"
        ><p class="go-to-result text-primary font-bold text-2xl">
          통계 확인하기 ->
        </p></NuxtLink
      >
    </div>
  </div>
</template>

<script setup lang="ts">
const IsSuccess = ref(true);
const roomId = ref(1);
</script>
<style scoped>
.go-to-result {
  animation:
    fade-in 3s,
    to-right 0.8s linear forwards;
}

@keyframes to-right {
  from {
    transform: translateX(0);
  }

  to {
    transform: translateX(3.5rem);
  }
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
