<template>
  <div class="w-full flex flex-col">
    <div
      class="w-auto h-32 flex flex-col justify-center items-center rounded-lt-xl bg-gradient-to-b from-[#61339B] to-[#c392ff] rounded-t-xl"
    >
      <div
        class="w-20 h-9 font-bold text-primary text-lg bg-white rounded-full text-center py-1"
      >
        DAY {{ props.result.sequence }}
      </div>
      <p class="text-lg font-semibold text-white mt-2">
        Q. {{ props.result.question }}
      </p>
    </div>
    <div
      class="h-96 overflow-auto border-b-4 border-x-4 bg-purple-50 p-4 flex flex-col items-center border-[#c392ff] rounded-b-xl"
    >
      <!-- <p class="text-center mt-4 mb-4 text-sm">A. {{ props.result.answer }}</p> -->
      <!-- <img
        v-if="$props.result.image"
        class="w-full h-auto rounded-lg"
        :src="`${props.result.image}`"
        alt=""
      />
      <img
        v-if="!$props.result.image"
        class="pt-8 w-3/4 h-auto rounded-lg"
        src="/img/heartGhost.png"
        alt=""
      /> -->
    </div>
  </div>
</template>

<script setup lang="ts">
import { type RoomQuestionList } from '../repository/modules/interface/question.interface';
// interface Result {
//   day: number;
//   question: string;
//   answer: string;
//   image?: string;
// }
const props = defineProps<{
  result: RoomQuestionList;
}>();
</script>
