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
        <button @click="updateSetting"><CheckIcon></CheckIcon></button>
      </div>
    </div>
    <div class="flex flex-col justify-evenly">
      <label class="font-bold text-xl" for="input">방 이름</label>
      <input
        id="input"
        class="w-full border rounded-lg h-10 p-3"
        type="text"
        placeholder="방의 이름을 정해주세요!"
        :value="name"
        autocomplete="off"
        @input="changeInput"
      />
      <div id="namecheck" class="text-red-600 text-sm mt-2"></div>
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
      <div id="rangecheck" class="text-red-600 text-sm mt-2"></div>
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
import {
  type IGetWaitRoomResponse,
  type IUpdateSettingInput,
} from '~/repository/modules/interface/waitroom.interface';
const { $api } = useNuxtApp();
const route = useRoute();
const router = useRouter();
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
const changeInput = (event: any): void => {
  name.value = event.target.value;
};
const getData = async (): Promise<void> => {
  const inviteCode = route.params.inviteCode;
  if (typeof inviteCode === 'string') {
    getWaitRoomData.value = await $api.make.getWaitRoom(inviteCode);
    if (getWaitRoomData.value !== undefined) {
      name.value = getWaitRoomData.value.data.setting.title;
      type.value = getWaitRoomData.value.data.setting.question_type;
      number.value = getWaitRoomData.value.data.setting.max_member;
      range.value.start = new Date(
        getWaitRoomData.value.data.setting.start_date,
      );
      range.value.end = new Date(getWaitRoomData.value.data.setting.end_date);
    }
  }
};

const check = (e: any): boolean => {
  const nameElement = document.getElementById('namecheck');
  const rangeElement = document.getElementById('rangecheck');
  const flag = ref<boolean>(false);
  const store = useMakeRoomStore();
  if (nameElement === null || rangeElement === null) {
    return true;
  }
  const reg = /[^\w\sㄱ-힣()0-9]/;
  nameElement.innerText = '';
  rangeElement.innerText = '';
  if (name.value === undefined) {
    return true;
  }
  if (
    reg.test(name.value) ||
    name.value.trim() === '' ||
    name.value.trim().length >= 10
  ) {
    nameElement.innerText =
      '특수문자, 공백을 제외하고 10자이내로 입력해주세요!';
    flag.value = true;
  }
  const period = store.diffDate(
    store.formatDate(range.value.start),
    store.formatDate(range.value.end),
  );
  if (
    store.diffDate(
      store.formatDate(new Date()),
      store.formatDate(range.value.start),
    ) <= 0
  ) {
    rangeElement.innerText = '시작일을 다시 설정해주세요!';
    flag.value = true;
  } else if (period > 14 || period < 3) {
    rangeElement.innerText =
      '3일 이상 14일이하로 설정해주세요! 현재 : (' + period + '일)';
    flag.value = true;
  }
  if (flag.value) {
    return true;
  }
  return false;
};

const updateSetting = async (e: any): Promise<void> => {
  const makeStore = useMakeRoomStore();
  const inviteCode = route.params.inviteCode;
  if (check(e)) {
    e.preventDefault();
    return;
  }
  if (typeof inviteCode === 'string') {
    const roomSetting = ref<IUpdateSettingInput>();
    if (
      name.value !== undefined &&
      type.value !== undefined &&
      number.value !== undefined &&
      range.value !== undefined
    ) {
      roomSetting.value = {
        title: name.value,
        start_date: makeStore.formatDate(range.value.start),
        end_date: makeStore.formatDate(range.value.end),
        question_type: type.value,
        max_member: number.value,
      };
      await $api.make.updateRoomSetting(inviteCode, roomSetting.value);
      await router.push(`/waitroom/${inviteCode}`);
    }
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
