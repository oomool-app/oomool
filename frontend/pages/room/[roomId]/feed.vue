<template>
  <div>
    <div class="header bg-primary p-6 sticky top-0 z-10">
      <div class="flex justify-between">
        <BackButton color="white" />
        <FeedHeader
          class="text-center text-white"
          header-name="우리들의 답변"
        ></FeedHeader>
        <div class="ml-3"></div>
      </div>
    </div>
    <div
      class="question-container mb-8 flex justify-center h-72 rounded-b-3xl bg-[#F1EBFC] drop-shadow-xl z-0"
    >
      <div
        v-show="fixHeader"
        class="flex flex-col justify-evenly items-center text-center w-[88%]"
      >
        <div>
          <img class="w-28 h-32" src="/img/questionGhost.png" alt="질문 유령" />
        </div>
        <div
          class="w-24 h-9 py-1 font-extrabold border-2 border-primary bg-white rounded-xl"
        >
          오늘의 질문
        </div>
        <div class="text-lg font-extrabold text-center">
          {{ question }}
        </div>
      </div>
    </div>
    <div
      v-if="!fixHeader"
      class="fixed top-20 flex justify-around text-center p-4 rounded-b-2xl drop-shadow-2xl bg-[#F1EBFC]"
    >
      <img
        class="w-12 h-14 mr-2"
        src="/img/questionGhost.png"
        alt="질문 유령"
      />
      <p class="question font-bold inline-block py-1">
        "내 친구가 좋아할만한 음악 장르나 곡은 무엇일 것 같아요?"
      </p>
    </div>
    <div
      v-if="feeds?.length === 0"
      class="feed-container flex flex-col items-center m-6 mt-10"
    >
      <p class="m-12 font-semibold">아직 답변해 준 친구들이 없어요..</p>
      <img class="w-5/6" src="/img/emptyGhost.png" alt="빈피드" />
    </div>
    <div v-else-if="feeds !== undefined" class="feed-container">
      <div v-for="feed in feeds" :key="feed.user_id">
        <div v-if="feed.user_id === userId">
          <FeedPageMine :feeds="feed"></FeedPageMine>
        </div>
        <div v-else-if="feed.user_id !== userId">
          <FeedPageOthers :feeds="feed"></FeedPageOthers>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import type Feed from '../../../repository/modules/interface/feeds.interface';
const { $api } = useNuxtApp();
const route = useRoute();
const userStore = useUserStore();
const userId = ref();
const roomQuestionId = ref();
const roomUid = route.params.roomId as string;
const question = ref();
let sequence: any;
// 오늘의 질문 조회
const getDailyQuestion = async (): Promise<void> => {
  try {
    const response = await $api.question.getDailyQuestion(roomUid);
    question.value = response.data.question;
    sequence = response.data.sequence;
  } catch (error) {
    console.error(error);
  }
};

// 답변들
const feeds = ref<Feed[] | undefined>();

// 오늘의 질문에 대한 모든 피드 조회
const getAllFeedsByRoomUidAndSequence = async (): Promise<void> => {
  try {
    userId.value = userStore.getStoredUser()?.id;
    if (sequence !== undefined) {
      const response = await $api.feeds.getAllFeedsByRoomUidAndSequence({
        roomUid,
        sequence,
      });
      console.log(response);
      feeds.value = response.data.room_feed_dto_list;
      roomQuestionId.value = response.data.room_question_id;
    }
  } catch (error) {
    console.error(error);
  }
};
const fixHeader = ref(true);

onMounted(async () => {
  await getDailyQuestion();
  await getAllFeedsByRoomUidAndSequence();
  window.addEventListener('scroll', function () {
    if (this.window.scrollY > 200) {
      fixHeader.value = false;
    } else if (this.window.screenY <= 200) {
      fixHeader.value = true;
    }
  });
});
</script>
<style scoped>
.header {
  animation: fade-in 0.8s ease-in-out;
}

.question-container {
  animation: fade-in2 1.2s ease-in-out;
}

.feed-container {
  animation: fade-in3 1.2s ease-in-out;
}
@keyframes fade-in {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

@keyframes fade-in2 {
  0% {
    opacity: 0;
  }

  20% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
@keyframes fade-in3 {
  0% {
    opacity: 0;
  }

  30% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>
