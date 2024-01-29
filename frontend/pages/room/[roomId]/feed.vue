<template>
  <div>
    <div class="bg-primary p-4 pt-6 pb-6 sticky top-0">
      <div class="flex justify-between">
        <BackButton color="white" />
        <FeedHeader
          class="text-center text-white"
          header-name="우리들의 답변"
        ></FeedHeader>
        <SettingButton color="white" />
      </div>
    </div>
    <div class="flex justify-center h-72 bg-background">
      <div
        v-show="fixHeader"
        class="flex flex-col justify-evenly items-center text-center w-[88%]"
      >
        <div>
          <img
            class="w-28 h-32"
            src="../../../assets/images/질문 유령.png"
            alt="질문 유령"
          />
        </div>
        <div
          class="w-24 h-8 py-1 font-extrabold border-2 border-primary rounded-lg"
        >
          오늘의 질문
        </div>
        <div class="text-lg font-extrabold">
          내 친구가 좋아할만한 음악 장르나 곡은 무엇일 것 같아요?
        </div>
      </div>
    </div>
    <div
      v-if="!fixHeader"
      class="sticky top-20 flex justify-around p-4 rounded-b-xl bg-[#F1EBFC]"
    >
      <img
        class="w-12 h-14 mr-2"
        src="../../../assets/images/질문 유령.png"
        alt="질문 유령"
      />
      <p class="question font-semibold inline-block py-1">
        "내 친구가 좋아할만한 음악 장르나 곡은 무엇일 것 같아요?"
      </p>
    </div>
    <div v-for="feed in feeds" :key="feed.name">
      <div v-if="feed.name === '전은평'">
        <FeedPageMine :feeds="feed"></FeedPageMine>
      </div>
      <div v-else-if="feed.name !== '전은평'">
        <FeedPageOthers :feeds="feed"></FeedPageOthers>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
const imgUrl =
  'https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=9046601&filePath=L2Rpc2sxL25ld2RhdGEvMjAxNC8yMS9DTFM2L2FzYWRhbFBob3RvXzI0MTRfMjAxNDA0MTY=&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004';
const feeds = [
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
];

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
