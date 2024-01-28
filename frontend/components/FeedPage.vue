<template>
  <div class="container pt-8 pb-8 pl-4 pr-4">
    <!-- 프로필 사진, 이름, 수정하기 버튼  -->
    <div class="header-container flex">
      <div class="font-semibold mr-3">
        <span class="text-primary">{{ props.feeds.name }}</span
        >의 마니또
      </div>
      <span>20분 전</span>
    </div>
    <br />
    <!-- 게시글 -->
    <div>{{ props.feeds.content }}</div>
    <br />
    <!-- 이미지 -->
    <div v-if="props.feeds.uploadImage">
      <img :src="props.feeds.uploadImage" alt="피드 이미지" />
    </div>
  </div>
</template>
<script setup lang="ts">
interface Feed {
  name: string;
  uploadImage: string;
  content: string;
}
const props = defineProps<{
  feeds: Feed;
}>();
</script>

<style scoped>
.container {
  border-bottom: 1px solid #b0b0b0;
}
</style>
