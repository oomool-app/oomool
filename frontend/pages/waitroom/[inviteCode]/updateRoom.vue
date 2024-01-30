<template>
  <div class="flex flex-col">
    <div class="flex justify-between items-center">
      <BackButton color="black"></BackButton>
      <div>방 설정</div>
      <div>v</div>
    </div>
    <div class="flex flex-col">
      <label for="input">방 이름</label>
      <input id="input" placeholder="모임 이름을 입력해 주세요." />
    </div>
    <div class="flex flex-col">
      <label for="type">방 유형</label>
      <MakeRoomTypeButton
        id="type"
        :game-type="gameType"
        @update-type="handleUpdateType"
      ></MakeRoomTypeButton>
    </div>
    <div class="flex flex-col">
      <label for="number">방 인원</label>
      <MakeRoomNumber id="number"></MakeRoomNumber>
    </div>
    <div class="flex flex-col justify-center items-center">
      <label for="cal" class="flex">진행 기간</label>
      <Calendar v-model.range="range" type="range"></Calendar>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { DatePickerRangeObject } from '~/components/ui/calendar/Calendar.vue';
const gameType = ref<string>('friendly');

function handleUpdateType(title: string): void {
  gameType.value = title;
}
const range = ref<DatePickerRangeObject>({
  start: Date(),
  end: Date(),
});
</script>
