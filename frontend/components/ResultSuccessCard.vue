<template>
  <div
    class="card w-64 h-2/3 border border-primary rounded-xl bg-gradient-to-br from-[#01943F] to-[#9BC53D] flex flex-col justify-center items-center"
  >
    <div
      class="result p-1 pl-3 pr-3 mb-10 rounded-xl text-[#01943F] text-lg font-bold bg-white"
    >
      예측 성공!!
    </div>
    <img
      class="img mb-6 w-48 h-auto"
      src="/img/successGhost.png"
      alt="결과확인"
    />
    <p class="p-2 text-2xl font-bold text-white">당신의 마니또</p>
    <div class="w-28 flex items-center justify-around">
      <div
        class="w-10 h-10 rounded-full bg-red-600 flex justify-center items-center"
      >
        <img class="w-6 h-auto" src="/img/rabbitGhost.png" alt="" />
      </div>
      <p class="text-xl font-bold text-white">김현지</p>
    </div>
  </div>
</template>

<script setup lang="ts"></script>
<style scoped>
.card {
  box-shadow: 0.3rem 0.3rem 0.3rem #01943f;
  animation: fadein 2s ease-in-out;
}

.img {
  animation: i-am-happy 0.5s;
  animation-iteration-count: infinite;
}

.result {
  animation: fadein 3s;
}

@keyframes fadein {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}
@keyframes i-am-happy {
  0% {
    transform: translateY(0);
  }

  50% {
    transform: translateY(1rem);
  }
}
</style>
