<template>
  <hr />
  <div class="pt-8 ml-6 mr-6 mb-6">
    <!-- 프로필 사진, 이름, 수정하기 버튼  -->
    <div class="header-container">
      <div>{{ props.feeds.name }}의 마니또</div>
      <div>수정하기 버튼</div>
    </div>
    <!-- 이미지 -->
    <div v-if="props.feeds.uploadImage">
      <img :src="props.feeds.uploadImage" alt="피드 이미지" />
    </div>
    <!-- 게시글 -->
    <div>{{ props.feeds.content }}</div>
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
.header-container {
  display: flex;
}
</style>
