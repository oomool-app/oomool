<template>
  <div>
    <div class="flex justify-between p-6 pb-2">
      <BackButton color="#61339b"></BackButton>
      <NuxtLink to="/">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="#61339b"
          stroke="#61339b"
          stroke-width="1"
          class="w-7 h-7 mr-2"
        >
          <path
            d="M11.47 3.841a.75.75 0 0 1 1.06 0l8.69 8.69a.75.75 0 1 0 1.06-1.061l-8.689-8.69a2.25 2.25 0 0 0-3.182 0l-8.69 8.69a.75.75 0 1 0 1.061 1.06l8.69-8.689Z"
          />
          <path
            d="m12 5.432 8.159 8.159c.03.03.06.058.091.086v6.198c0 1.035-.84 1.875-1.875 1.875H15a.75.75 0 0 1-.75-.75v-4.5a.75.75 0 0 0-.75-.75h-3a.75.75 0 0 0-.75.75V21a.75.75 0 0 1-.75.75H5.625a1.875 1.875 0 0 1-1.875-1.875v-6.198a2.29 2.29 0 0 0 .091-.086L12 5.432Z"
          />
        </svg>
      </NuxtLink>
    </div>

    <div class="flex flex-col justify-start">
      <ContentHeader
        class="p-4 pl-12"
        header-name="마니또가 한 생각"
      ></ContentHeader>
      <div class="flex flex-col items-center justify-center">
        <Carousel
          :opts="{
            align: 'start',
            loop: false,
          }"
          :plugins="[
            Autoplay({
              delay: 5000,
              stopOnMouseEnter: true,
              stopOnInteraction: false,
            }),
          ]"
          class="relative w-3/4 max-w-xs mb-4"
        >
          <CarouselContent>
            <CarouselItem v-for="item in results" :key="item.day">
              <ResultFinalCard :result="item"></ResultFinalCard>
            </CarouselItem>
          </CarouselContent>
        </Carousel>
        <div class="flex">
          <div
            v-for="(item, index) in results"
            :key="item.day"
            class="bg-gray-300 rounded-full w-3 h-3 m-0.5 p-1"
            @click="changeCursor(index)"
          ></div>
        </div>
        <Button class="w-1/2 m-4 rounded-xl"
          >이미지로 저장하기 &nbsp;&nbsp;
          <svg
            xmlns="http://www.w3.org/2000/svg"
            fill="none"
            viewBox="0 0 24 24"
            stroke-width="2"
            stroke="currentColor"
            class="w-5 h-5"
          >
            <path
              stroke-linecap="round"
              stroke-linejoin="round"
              d="M3 16.5v2.25A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75V16.5M16.5 12 12 16.5m0 0L7.5 12m4.5 4.5V3"
            />
          </svg>
        </Button>
      </div>
    </div>
    <div class="flex justify-center mt-8 mb-8">
      <div class="w-3/4 p-3 flex justify-between bg-purple-200 rounded-xl">
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
  </div>
</template>
<script setup lang="ts">
import Autoplay from 'embla-carousel-autoplay';
import emblaCarouselVue from 'embla-carousel-vue';
const [emblaRef, emblaApi] = emblaCarouselVue();

const cursorLoc = ref(0);
const changeCursor = (index: number): void => {
  cursorLoc.value = index;
  console.log(emblaApi.value?.rootNode());
};

watchEffect(() => {
  console.log(cursorLoc.value);
  if (emblaApi.value !== null && emblaApi.value !== undefined) {
    emblaApi.value.scrollTo(cursorLoc.value);
  }
});
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
  {
    day: 5,
    question: '내 친구 첫인상은 어땠나요?',
    answer:
      '목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;;',
    image:
      'https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s900-c-k-c0x00ffffff-no-rj',
  },
  {
    day: 6,
    question: '내 친구 취미는?',
    answer: '게임 많이 하는 것 같던데, 게임!!',
    image: 'https://cdn.gametoc.co.kr/news/photo/202308/74641_231767_545.jpg',
  },
  {
    day: 7,
    question: '친구 습관은?',
    answer: '지각하기,,,?',
  },
  {
    day: 8,
    question: '내 친구 첫인상은 어땠나요?',
    answer:
      '목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;; 목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;; 목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;;',
    image:
      'https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s900-c-k-c0x00ffffff-no-rj',
  },
  {
    day: 9,
    question: '내 친구 첫인상은 어땠나요?',
    answer:
      '목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;;',
    image:
      'https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s900-c-k-c0x00ffffff-no-rj',
  },
  {
    day: 10,
    question: '내 친구 취미는?',
    answer: '게임 많이 하는 것 같던데, 게임!!',
    image: 'https://cdn.gametoc.co.kr/news/photo/202308/74641_231767_545.jpg',
  },
  {
    day: 11,
    question: '친구 습관은?',
    answer: '지각하기,,,?',
  },
  {
    day: 12,
    question: '내 친구 첫인상은 어땠나요?',
    answer:
      '목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;; 목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;; 목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;;',
    image:
      'https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s900-c-k-c0x00ffffff-no-rj',
  },
  {
    day: 13,
    question: '내 친구 첫인상은 어땠나요?',
    answer:
      '목소리가 부드러워서 성시경인줄.. 노래방 갔을 때 그 환상이 다 깨져버렸지만;;',
    image:
      'https://yt3.googleusercontent.com/vQrdlCaT4Tx1axJtSUa1oxp2zlnRxH-oMreTwWqB-2tdNFStIOrWWw-0jwPvVCUEjm_MywltBFY=s900-c-k-c0x00ffffff-no-rj',
  },
  {
    day: 14,
    question: '내 친구 취미는?',
    answer: '게임 많이 하는 것 같던데, 게임!!',
    image: 'https://cdn.gametoc.co.kr/news/photo/202308/74641_231767_545.jpg',
  },
]);
</script>
