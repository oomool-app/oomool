<template>
  <div>
    <EndDate></EndDate>
    <DDay></DDay>
    <FeedHeader></FeedHeader>
    <AllMembers :room-id="roomId"></AllMembers>
    <p>오늘의 질문</p>
    <TodayQuestion :question="question"></TodayQuestion>
    <div v-for="feed in feeds" :key="feed.profileImage">
      <FeedPage :feeds="feed"></FeedPage>
    </div>
  </div>
</template>
<script setup lang="ts">
const roomId = ref(1);
const question = ref('오늘의 질문 입니다');
const imgUrl =
  'https://gongu.copyright.or.kr/gongu/wrt/cmmn/wrtFileImageView.do?wrtSn=9046601&filePath=L2Rpc2sxL25ld2RhdGEvMjAxNC8yMS9DTFM2L2FzYWRhbFBob3RvXzI0MTRfMjAxNDA0MTY=&thumbAt=Y&thumbSe=b_tbumb&wrtTy=10004';
const feeds = [
  {
    profileImage: '프로필주소1',
    name: '마니띠 이름1',
    uploadImage: imgUrl,
    content: '게시글1',
  },
  {
    profileImage: '프로필주소2',
    name: '마니띠 이름2',
    uploadImage: imgUrl,
    content: '게시글2',
  },
  {
    profileImage: '프로필주소3',
    name: '마니띠 이름3',
    uploadImage: '',
    content: '게시글3',
  },
  {
    profileImage: '프로필주소4',
    name: '마니띠 이름4',
    uploadImage: imgUrl,
    content: '게시글4',
  },
  {
    profileImage: '프로필주소5',
    name: '마니띠 이름5',
    uploadImage: imgUrl,
    content: '게시글5',
  },
  {
    profileImage: '프로필주소6',
    name: '마니띠 이름6',
    uploadImage: imgUrl,
    content: '게시글6',
  },
];
</script>
