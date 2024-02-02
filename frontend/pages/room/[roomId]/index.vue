<template>
  <div id="top-container">
    <div
      class="bg-[#61339b] rounded-b-2xl h-32 p-4 pl-5 pr-5 pb-8 drop-shadow-xl"
    >
      <div class="flex justify-between">
        <BackButton color="white" />
      </div>
      <div class="flex justify-between mt-8">
        <FeedHeader
          class="text-white text-3xl"
          :header-name="teamName"
        ></FeedHeader>
        <DDay
          class="d-day border text-primary border-white bg-white h-9 w-14 rounded-full text-center font-bold py-1"
        ></DDay>
      </div>
    </div>
    <div class="bottom-container p-4 pt-6">
      <div class="my-manitti">
        <ContentHeader header-name="내 마니띠" />
        <div
          class="flex justify-evenly items-center my-manitti border-2 border-[#61339b] bg-gradient-to-r from-[#61339b] to-[#a8bdf9] h-32 mt-4 mb-8 rounded-xl"
        >
          <img src="/img/rabbitGhost.png" alt="" class="ghost h-3/4 w-auto" />
          <p
            v-if="!whoIsManitti"
            class="btn bg-primary w-40 h-10 text-white text-center py-1 text-xl rounded-full font-bold drop-shadow-2xl"
            @click="discoverManitti()"
          >
            누구게?
          </p>
          <p
            v-else-if="whoIsManitti"
            class="btn bg-white w-40 h-10 text-center text-primary py-1 text-xl rounded-full font-bold"
            @click="hideManitti()"
          >
            김현지
          </p>
        </div>
      </div>
      <div class="question-container">
        <div class="flex justify-between">
          <ContentHeader header-name="오늘의 질문" />
          <NuxtLink :to="`${roomId}/writeanswer`" class="text-sm py-1">
            <div class="text-[#61339b] font-semibold">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-4 h-4 inline"
              >
                <path
                  stroke="#61339b"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L6.832 19.82a4.5 4.5 0 0 1-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 0 1 1.13-1.897L16.863 4.487Zm0 0L19.5 7.125"
                />
              </svg>
              답변하기
            </div></NuxtLink
          >
        </div>
        <div
          class="border-2 border-primary rounded-lg p-5 mt-4 mb-10 text-center font-semibold"
        >
          내 친구가 좋아할만한 음악 장르나 곡은 무엇인 것 같아요?
        </div>
      </div>
      <div class="answer-container">
        <div class="flex justify-between">
          <ContentHeader header-name="우리들의 답변" />
          <TotalButton :link="`${roomId}/feed`" text="전체보기" />
        </div>
        <div
          class="p-5 mt-5 mb-10 rounded-lg bg-gradient-to-r from-[#61339b] to-[#a8bdf9] border-primary border-2 text-white text-center font-semibold"
        >
          내 마니또가 답변을 등록했어요!
        </div>
      </div>
      <div class="members-container mb-6">
        <div class="flex justify-between">
          <ContentHeader header-name="멤버 목록" />
          <TotalButton :link="`${roomId}/members`" text="전체보기" />
        </div>
        <div class="flex overflow-auto">
          <div v-for="member in members" :key="member.name">
            <RoomProfile :member="member"></RoomProfile>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const teamName = '김성수와 아이들';
const whoIsManitti = ref(false);
const discoverManitti = (): void => {
  whoIsManitti.value = true;
};
const hideManitti = (): void => {
  whoIsManitti.value = false;
};
const members = ref([
  { name: '박세정' },
  { name: '전은평' },
  { name: '김병현' },
  { name: '정필묘' },
  { name: '김현지' },
  { name: '김성수' },
]);
const roomId = ref(1);
</script>
<style scope>
#top-container {
  animation: fade-in 0.8s ease-in-out;
}

.my-manitti {
  animation: fade-in 1.2s ease-in-out;
}

.question-container {
  animation: fade-in 1.6s ease-in-out;
}

.answer-container {
  animation: fade-in 2s ease-in-out;
}

.members-container {
  animation: fade-in 2.4s ease-in-out;
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
