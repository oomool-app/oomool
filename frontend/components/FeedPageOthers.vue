<template>
  <div class="flex justify-evenly pt-6 pb-6 pl-4 pr-4">
    <div
      class="rounded-full w-12 h-12 border flex justify-center items-center"
      :style="{ 'background-color': props.feeds.manitti_dto.avatar_color }"
    >
      <img
        class="w-7 h-auto"
        :src="feeds.manitti_dto.url"
        alt="프로필 이미지"
      />
    </div>
    <div class="flex-col w-[77%]">
      <div class="flex justify-between pr-1">
        <p class="font-semibold inline-block mb-1">
          <span class="text-primary font-extrabold">{{
            props.feeds.manitti_dto.nickname
          }}</span
          >의 마니또
        </p>
        <p class="text-sm mt-1">{{ createdHour }}:{{ createdMin }}</p>
      </div>
      <div
        class="p-3 border border-gray-400 rounded-r-xl rounded-bl-xl bg-[#F1EBFC]"
      >
        <img
          v-if="
            props.feeds.feed_image_dto_list !== undefined &&
            props.feeds.feed_image_dto_list[0] !== undefined
          "
          class="w-full h-auto rounded-lg mb-2"
          :src="imgUrl"
          alt=""
        />
        {{ props.feeds.content }}
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import type Feed from '~/repository/modules/interface/feeds.interface';
const props = defineProps<{
  feeds: Feed;
}>();
const createdHour = String(
  new Date(props.feeds.created_at).getHours(),
).padStart(2, '0');
const createdMin = String(
  new Date(props.feeds.created_at).getMinutes(),
).padStart(2, '0');

const imgUrl = ref();
if (
  props.feeds.feed_image_dto_list !== undefined &&
  props.feeds.feed_image_dto_list.length > 0
) {
  imgUrl.value = props.feeds.feed_image_dto_list[0].url;
}
</script>
