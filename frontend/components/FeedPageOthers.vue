<template>
  <div class="flex justify-evenly pt-6 pb-6 pl-4 pr-4">
    <img
      class="w-10 h-10 rounded-full border"
      src="../assets/images/정답 유령.png"
      alt="프로필 이미지"
    />

    <div class="flex-col w-[77%]">
      <div class="flex justify-between">
        <p class="font-semibold inline-block mb-1">
          <span class="text-primary font-extrabold">{{ props.feeds.name }}</span
          >의 마니또
        </p>
        <p>20분 전</p>
      </div>
      <div
        class="p-3 border rounded-r-xl rounded-bl-xl border-primary bg-[#F1EBFC]"
      >
        <img class="rounded-lg mb-2" :src="props.feeds.uploadImage" alt="" />
        {{ props.feeds.content }}
      </div>
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
