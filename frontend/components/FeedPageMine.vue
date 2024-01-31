<template>
  <div class="flex justify-evenly pt- pb-6 pl-4 pr-4">
    <div class="flex-col w-[77%]">
      <p class="text-right mb-1">수정하기</p>
      <div
        class="chat-box p-3 border rounded-l-xl rounded-br-xl border-primary bg-[#F1EBFC]"
      >
        <img class="rounded-lg mb-2" :src="props.feeds.uploadImage" alt="" />
        {{ props.feeds.content }}
      </div>
    </div>
    <img
      class="w-10 h-10 rounded-full border"
      src="/img/정답유령.png"
      alt="프로필 이미지"
    />
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
