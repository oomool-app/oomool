<template>
  <div class="h-screen flex flex-col items-center justify-center">
    <ResultSuccessCard v-if="IsSuccess"></ResultSuccessCard>
    <ResultFailCard v-if="!IsSuccess"></ResultFailCard>
    <div class="mt-8 flex flex-col items-center mr-6">
      <NuxtLink :to="`/final/${roomUid}/result2`"
        ><p
          class="go-to-result text-primary text-center font-semibold text-2xl"
        >
          <svg
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
          마니또 답변 보러가기
        </p>
      </NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
const IsSuccess = ref(true);
const route = useRoute();
const roomUid = route.params.roomId;
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
