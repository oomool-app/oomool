<template>
  <div class="w-full flex flex-col">
    <div
      class="w-auto h-24 p-4 flex flex-col justify-center items-center rounded-lt-xl bg-gradient-to-b from-[#61339B] to-[#c392ff] rounded-t-xl"
    >
      <div
        class="w-16 h-5 font-bold text-primary bg-white rounded-full text-center"
      >
        DAY {{ props.index + 1 }}
      </div>
      <p class="font-semibold text-white mt-2">
        Q. {{ props.result.question_dto.question }}
      </p>
    </div>
    <div
      class="border-b-4 border-x-4 bg-purple-50 p-4 flex flex-col items-center border-[#c392ff] rounded-b-xl"
    >
      <div
        v-if="
          $props.result.content === null || props.result.content === undefined
        "
      >
        <p class="text-center mb-4 font-semibold">
          마니또가 답변하는 것을 잊었나봐요..
        </p>
        <img
          class="w-full h-auto rounded-lg"
          src="/img/emptyGhost.png"
          alt=""
        />
      </div>
      <div
        v-if="
          props.result.content !== null &&
          (props.result.feed_image_dto_list === null ||
            props.result.feed_image_dto_list?.length === 0)
        "
        class="flex flex-col items-center"
      >
        <p class="text-center mb-4 font-semibold">
          A. {{ props.result.content }}
        </p>
        <img
          class="w-3/4 h-auto rounded-lg align-middle"
          src="/img/heartGhost.png"
          alt=""
        />
      </div>
      <div
        v-else-if="
          props.result.content !== null &&
          props.result.feed_image_dto_list !== null &&
          props.result.feed_image_dto_list !== undefined
        "
      >
        <p class="text-center mb-4 font-semibold">
          A. {{ props.result.content }}
        </p>
        <NuxtImg class="w-full h-auto rounded-lg" :src="url" loading="lazy" />
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { AllFeeds } from '~/repository/modules/interface/feeds.interface';
const props = defineProps<{
  result: AllFeeds;
  index: number;
}>();
const url = ref();
if (
  props.result.feed_image_dto_list !== undefined &&
  props.result.feed_image_dto_list?.length > 0
) {
  url.value = props.result.feed_image_dto_list[0].url;
}
</script>
