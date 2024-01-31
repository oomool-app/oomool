<template>
  <div
    class="card w-64 h-2/3 border border-primary rounded-xl bg-gradient-to-br from-[#61339B] to-[#A8BDF9] flex justify-center items-center"
  >
    <img class="img w-48 h-auto" src="/img/궁금유령.png" alt="결과확인" />
  </div>
</template>

<script setup lang="ts"></script>
<style scoped>
.card {
  animation: test 0.7s ease-in-out;
  animation-iteration-count: infinite;
  box-shadow: 0.3rem 0.3rem 0.3rem #61339b;
}

@keyframes test {
  50% {
    transform: scale(0.98);
  }

  100% {
    transform: scale(1);
  }
}
</style>
