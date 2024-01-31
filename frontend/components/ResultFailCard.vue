<template>
  <div
    class="card w-64 h-2/3 border border-primary rounded-xl bg-gradient-to-br from-[#D00000] to-[#EDAFB8] flex flex-col justify-center items-center"
  >
    <div
      class="result pl-3 pr-3 mb-10 rounded-xl text-[#D00000] text-lg font-bold bg-white"
    >
      예측 실패!!
    </div>
    <img class="img mb-6 w-28 h-auto" src="/img/오답유령.png" alt="결과확인" />
    <p class="p-2 text-2xl font-bold text-white">당신의 마니또</p>
    <div class="w-28 flex items-center justify-around">
      <div
        class="w-10 h-10 rounded-full bg-red-600 flex justify-center items-center"
      >
        <img class="w-6 h-auto" src="/img/토끼유령.png" alt="" />
      </div>
      <p class="text-xl font-bold text-white">김현지</p>
    </div>
  </div>
</template>

<script setup lang="ts"></script>
<style scoped>
.card {
  box-shadow: 0.3rem 0.3rem 0.3rem #d00000;
  animation: fadein 2s ease-in-out;
}

.img {
  animation: i-am-sad 0.4s;
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
@keyframes i-am-sad {
  0% {
    transform: translateX(-0.2rem);
  }

  50% {
    transform: translateX(0.2rem);
  }

  100% {
    transform: translateX(-0.2rem);
  }
}
</style>
