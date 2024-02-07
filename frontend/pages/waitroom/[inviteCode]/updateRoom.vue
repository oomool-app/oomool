<template>
  <div
    v-if="getWaitRoomData !== undefined"
    class="box grid grid-rows-[4rem,6rem,6rem,5rem] h-screen p-6"
  >
    <div class="grid grid-cols-3">
      <div class="flex items-center">
        <BackButton color="black"></BackButton>
      </div>
      <div class="flex items-center justify-center text-2xl font-extrabold">
        방 만들기
      </div>
      <div class="flex items-center justify-end">
        <CheckIcon></CheckIcon>
      </div>
    </div>
    <div class="flex flex-col justify-evenly">
      <label class="font-bold text-xl" for="input">방 이름</label>
      <input
        id="input"
        class="w-full border rounded-lg h-10 p-3"
        placeholder="모임 이름을 입력해 주세요."
        :value="name"
      />
    </div>
    <div class="flex flex-col justify-evenly">
      <div class="flex items-center justify-between">
        <label class="font-bold text-xl" for="type">방 유형</label>
        <div class="flex text-sm items-center">
          <InfoIcon /> 매일 제공되는 질문 생성에 반영됩니다.
        </div>
      </div>
      <MakeRoomTypeButton
        id="type"
        :game-type="type"
        @update-type="handleUpdateType"
      ></MakeRoomTypeButton>
    </div>
    <div class="flex flex-col justify-evenly">
      <div class="flex justify-between">
        <label class="font-bold text-xl" for="number">방 인원</label>
        <div class="font-bold text-xl">{{ number }}명</div>
      </div>
      <MakeRoomNumber
        id="number"
        :people-number="number"
        @update-number="handleUpdateNumber"
      ></MakeRoomNumber>
    </div>
    <div class="grid grid-rows-[2rem,auto]">
      <div class="flex items-center">
        <label for="cal" class="font-bold text-xl">진행 기간</label>
      </div>
      <div class="flex justify-center items-center">
        <div class="w-11/12">
          <Calendar v-model.range="range" class="" type="range"></Calendar>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { DatePickerRangeObject } from '~/components/ui/calendar/Calendar.vue';
import { type IGetWaitRoomResponse } from '~/repository/modules/interface/waitroom.interface';
const { $api } = useNuxtApp();
const route = useRoute();
const getWaitRoomData = ref<IGetWaitRoomResponse>();
const name = ref<string>();
const type = ref<string>();
const number = ref<number>();
const range = ref<DatePickerRangeObject>({
  start: new Date(),
  end: new Date(),
});
onMounted(async (): Promise<void> => {
  await getData();
});
function handleUpdateType(title: string): void {
  type.value = title;
}
function handleUpdateNumber(n: number): void {
  number.value = n;
}
const getData = async (): Promise<void> => {
  const input = route.params.inviteCode;
  if (typeof input === 'string') {
    getWaitRoomData.value = await $api.make.getWaitRoom(input);
    name.value = getWaitRoomData.value?.data.setting.title;
    type.value = getWaitRoomData.value?.data.setting.question_type;
    number.value = getWaitRoomData.value?.data.setting.max_member;
    range.value.start = new Date(
      getWaitRoomData.value?.data.setting.start_date,
    );
    range.value.end = new Date(getWaitRoomData.value?.data.setting.end_date);
  }
};
</script>
<style scoped>
.box {
  animation: fade-in 0.5s ease-in-out;
}
@keyframes fade-in {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}
</style>
