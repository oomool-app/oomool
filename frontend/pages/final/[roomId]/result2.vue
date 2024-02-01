<template>
  <div>
    <div class="flex justify-center mt-8">
      <div
        class="w-3/4 p-3 flex justify-between border border-blue-400 bg-blue-200 rounded-xl"
      >
        <div class="w-full pr-2 flex flex-col">
          <div class="text-gray-400 pb-4">
            <p class="text-sm font-bold">김성수와 아이들</p>
            <p style="font-size: 0.65rem">2024-02-08 ~ 2024-02-16</p>
          </div>
          <div class="w-full flex flex-col items-end">
            <p class="font-semibold">당신의 마니또</p>
            <p class="text-xl font-bold">김성수</p>
          </div>
        </div>
        <img class="w-20 h-auto" src="/img/sangwooGhost.png" alt="" />
      </div>
    </div>
    <div class="flex flex-col justify-start">
      <p class="p-4 pl-12 text-primary text-lg font-bold">마니또가 한 생각</p>
      <div class="flex justify-center">
        <Carousel class="relative w-3/4 max-w-xs">
          <CarouselContent>
            <CarouselItem v-for="item in results" :key="item.day">
              <ResultFinalCard :result="item"></ResultFinalCard>
            </CarouselItem>
          </CarouselContent>
        </Carousel>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
const results = ref([
  {
    day: 1,
    question: '내 친구 첫인상은 어땠나요?',
    answer:
      '목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;;',
    image:
      'https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s900-c-k-c0x00ffffff-no-rj',
  },
  {
    day: 2,
    question: '내 친구 취미는?',
    answer: '게임 많이 하는 것 같던데, 게임!!',
    image: 'https://cdn.gametoc.co.kr/news/photo/202308/74641_231767_545.jpg',
  },
  {
    day: 3,
    question: '친구 습관은?',
    answer: '지각하기,,,?',
  },
  {
    day: 4,
    question: '내 친구 첫인상은 어땠나요?',
    answer:
      '목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;; 목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;; 목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;;',
    image:
      'https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s900-c-k-c0x00ffffff-no-rj',
  },
]);
</script>
