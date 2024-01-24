<template>
  <div class="container">
    <!-- 프로필 사진, 이름, 수정하기 버튼  -->
    <div class="header-container">
      <div>{{ props.feeds.profileImage }}</div>
      <div>{{props.feeds.name}}의 마니또</div>
      <div>수정하기 버튼</div>
    </div>
    <!-- 이미지 -->
    <div></div>
    <!-- 게시글 -->
    <div>{{ props.feeds.content }}</div>
  </div>
</template>
<script setup lang="ts">
interface Feed {
  profileImage:string,
  id:number,
  name: string
  content: string,
}
const props = defineProps<{
  feeds:Feed,
}>()
</script>

<style scoped>
.container{
  width:22rem;
  margin-bottom: 1.5rem;
}

.header-container{
  display:flex;
}
</style>
