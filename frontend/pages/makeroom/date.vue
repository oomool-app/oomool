<template>
  <div class="flex-col m-4">
    <BackButton></BackButton>
    <h1 class="flex justify-center text-xl m-4 font-bold">방 만들기</h1>
    <MakeRoomProgress :cur-page="'4'"></MakeRoomProgress>
    <div class="font-bold mb-4">언제부터 언제까지 진행되나요?</div>
    <div>최대 14일까지 설정할 수 있어요.</div>
    <div class="flex justify-center items-center">
      <Calendar v-model.range="range" type="range"> </Calendar>
    </div>
    {{ range.start }}
    {{ range.end }}
    <div>
      <NuxtLink to="/waitroom/1"><Button>만들기</Button></NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { DatePickerRangeObject } from '~/components/ui/calendar/Calendar.vue';

const range = ref<DatePickerRangeObject>({
  start: Date(),
  end: Date(),
});
</script>
