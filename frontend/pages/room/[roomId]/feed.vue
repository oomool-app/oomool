<template>
  <div>
    <div class="bg-primary p-4 pt-6 pb-6">
      <div class="flex justify-between">
        <BackButton color="white" />
        <FeedHeader
          class="text-center text-white"
          header-name="우리들의 답변"
        ></FeedHeader>
        <SettingButton color="white" />
      </div>
    </div>
    <div v-for="feed in feeds" :key="feed.name">
      <FeedPage :feeds="feed"></FeedPage>
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
</script>
