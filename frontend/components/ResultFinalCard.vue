<template>
  <div class="w-full flex flex-col">
    <div
      class="w-auto h-48 p-4 flex flex-col justify-center items-center rounded-lt-xl bg-gradient-to-b from-[#61339B] to-[#c392ff] rounded-t-xl"
    >
      <div
        class="w-20 h-9 font-bold text-primary text-lg bg-white rounded-full text-center py-1"
      >
        DAY {{ props.index + 1 }}
      </div>
      <p class="text-lg font-semibold text-white mt-2">
        Q. {{ props.result.question_dto.question }}
      </p>
    </div>
    <div
      class="h-96 overflow-auto border-b-4 border-x-4 bg-purple-50 p-4 flex flex-col items-center border-[#c392ff] rounded-b-xl"
    >
      <div
        v-if="
          $props.result.content === null || props.result.content === undefined
        "
      >
        <p class="text-center mt-4 mb-4 font-semibold">
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
        <p class="text-center mt-4 mb-4 font-semibold">
          A. {{ props.result.content }}
        </p>
        <img
          class="pt-8 w-3/4 h-auto rounded-lg align-middle"
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
        <p class="text-center mt-4 mb-4 font-semibold">
          A. {{ props.result.content }}
        </p>
        <img class="w-full h-auto rounded-lg" :src="`${url}`" alt="" />
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
if (props.result.feed_image_dto_list !== undefined) {
  url.value = props.result.feed_image_dto_list;
}
</script>
