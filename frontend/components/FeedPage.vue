<template>
  <div class="container">
    <!-- 프로필 사진, 이름, 수정하기 버튼  -->
    <div class="header-container">
      <div
        class="flex-auto flex-col justify-end rounded-full bg-red-500 size-8 border border-solid border-orange-950"
      >
        <img
          class="inline-block"
          src="/assets/images/정답 유령.png"
          alt="프로필 이미지"
        />
        <!-- {{ props.feeds.profileImage }} -->
      </div>
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
  profileImage: string;
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
  width: 22rem;
  margin-bottom: 1.5rem;
}

.header-container {
  display: flex;
}
</style>
