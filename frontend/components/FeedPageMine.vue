<template>
  <div class="flex justify-evenly pt- pb-6 pl-4 pr-4">
    <div class="flex-col w-[77%]">
      <NuxtLink :to="`/room/${props.roomUid}/modifyanswer`">
        <p class="text-sm text-right text-primary mr-1">수정하기</p>
      </NuxtLink>
      <div
        class="chat-box p-3 border border-purple-500 rounded-l-xl rounded-br-xl bg-[#d8c7f9]"
      >
        <img
          v-if="
            props.feeds.feed_image_dto_list !== undefined &&
            props.feeds.feed_image_dto_list[0] !== undefined
          "
          class="rounded-lg mb-4 w-full h-auto"
          alt=""
          :src="imgUrl"
        />
        {{ props.feeds.content }}
      </div>
    </div>
    <div
      class="rounded-full w-12 h-12 border flex justify-center border-black items-center"
      :style="{ 'background-color': props.feeds.manitti_dto.avatar_color }"
    >
      <img
        class="w-7 h-auto"
        :src="$props.feeds.manitti_dto.url"
        alt="프로필 이미지"
      />
    </div>
  </div>
</template>
<script setup lang="ts">
import type Feed from '~/repository/modules/interface/feeds.interface';
const props = defineProps<{
  feeds: Feed;
  roomUid: string;
}>();
const imgUrl = ref();
if (
  props.feeds.feed_image_dto_list !== undefined &&
  props.feeds.feed_image_dto_list.length > 0
) {
  imgUrl.value = props.feeds.feed_image_dto_list[0].url;
}
</script>
