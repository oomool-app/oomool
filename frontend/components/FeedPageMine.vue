<template>
  <div class="flex justify-evenly pt- pb-6 pl-4 pr-4">
    <div class="flex-col w-[77%]">
      <p class="text-right mb-1">수정하기</p>
      <div
        class="chat-box p-3 border border-purple-500 rounded-l-xl rounded-br-xl bg-[#d8c7f9]"
      >
        <img
          class="rounded-lg mb-2"
          :src="props.feeds.feed_image_dto_list[0]"
          alt=""
        />
        {{ props.feeds.content }}
      </div>
    </div>
    <img
      class="w-10 h-10 rounded-full border"
      src="/img/successGhost.png"
      alt="프로필 이미지"
    />
  </div>
</template>
<script setup lang="ts">
import type Feed from '~/repository/modules/interface/feeds.interface';
const props = defineProps<{
  feeds: Feed;
}>();
</script>
