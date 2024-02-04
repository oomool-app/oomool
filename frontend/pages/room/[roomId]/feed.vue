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
          내 친구가 좋아할만한 음악 장르나 곡은 무엇일 것 같아요?
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
      v-if="feeds === undefined"
      class="feed-container flex flex-col items-center m-6 mt-10"
    >
      <p class="m-12 font-semibold">아직 답변해 준 친구들이 없어요..</p>
      <img class="w-5/6" src="/img/emptyGhost.png" alt="" />
    </div>
    <div v-else-if="feeds !== undefined" class="feed-container">
      <div v-for="feed in feeds" :key="feed.name">
        <div v-if="feed.name === '전은평'">
          <FeedPageMine :feeds="feed"></FeedPageMine>
        </div>
        <div v-else-if="feed.name !== '전은평'">
          <FeedPageOthers :feeds="feed"></FeedPageOthers>
        </div>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
const imgUrl =
  'https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=9046601&filePath=L2Rpc2sxL25ld2RhdGEvMjAxNC8yMS9DTFM2L2FzYWRhbFBob3RvXzI0MTRfMjAxNDA0MTY=&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004';

interface Feed {
  name: string;
  uploadImage: string;
  content: string;
}
// const feeds = ref<Feed[] | undefined>();
const feeds = ref<Feed[] | undefined>([
  {
    name: '박세정',
    uploadImage: imgUrl,
    content: '게시글1',
  },
  {
    name: '전은평',
    uploadImage: imgUrl,
    content: '게시글2',
  },
  {
    name: '김병현',
    uploadImage: '',
    content: '게시글3',
  },
  {
    name: '김현지',
    uploadImage: imgUrl,
    content: '게시글4',
  },
  {
    name: '김성수',
    uploadImage: imgUrl,
    content: '게시글5',
  },
  {
    name: '정필모',
    uploadImage: imgUrl,
    content: '게시글6',
  },
]);

const fixHeader = ref(true);

onMounted(() => {
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
