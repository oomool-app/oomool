<template>
  <div>
    <div class="top-container rounded-b-2xl h-32 p-4 pl-5 pr-5">
      <div class="flex justify-between">
        <BackButton color="white" />
        <SettingButton color="white" />
      </div>
      <div class="flex justify-between mt-8">
        <FeedHeader class="text-white" :header-name="teamName"></FeedHeader>
        <DDay
          class="d-day border border-white bg-white h-10 w-14 rounded-full text-center font-bold py-1"
        ></DDay>
      </div>
    </div>
    <div class="bottom-container p-4 pt-6">
      <div class="question-container">
        <div class="flex justify-between">
          <ContentHeader header-name="오늘의 질문" />
          <NuxtLink :to="`${roomId}/writeanswer`" class="text-sm py-1">
            <div id="go-to-answer" class="font-semibold">
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
        <div id="question" class="border rounded-lg p-5 mt-4 mb-10 text-center">
          내 친구가 좋아할만한 음악 장르나 곡은 무엇인 것 같아요?
        </div>
      </div>
      <div class="answer-container">
        <div class="flex justify-between">
          <ContentHeader header-name="우리들의 답변" />
          <TotalButton :link="`${roomId}/feed`" text="전체보기" />
        </div>
        <div
          id="answer"
          class="p-5 mt-5 mb-10 rounded-lg text-white text-center"
        >
          내 마니또가 답변을 등록했어요!
        </div>
      </div>
      <div class="members-container">
        <div class="flex justify-between">
          <ContentHeader header-name="멤버 목록" />
          <TotalButton :link="`${roomId}/members`" text="전체보기" />
        </div>
        <div class="flex">
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
const members = ref([
  { name: '박세정' },
  { name: '전은평' },
  { name: '김병현' },
]);
const roomId = ref(1);
</script>
<style scope>
.top-container {
  background-color: #61339b;
}

.d-day {
  color: #61339b;
}

#question {
  border: 2px solid #61339b;
}

#go-to-answer {
  color: #61339b;
}

#answer {
  background: linear-gradient(to right, #61339b, #a8bdf9);
}
</style>
