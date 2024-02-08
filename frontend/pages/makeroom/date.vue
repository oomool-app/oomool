<template>
  <div class="box grid grid-rows-[6rem,2rem,4.5rem,5rem] h-screen p-6">
    <div class="grid grid-cols-3">
      <div class="flex items-center">
        <BackButton color="black"></BackButton>
      </div>
      <div class="flex items-center justify-center text-2xl font-extrabold">
        방 만들기
      </div>
    </div>
    <div>
      <MakeRoomProgress :cur-page="'4'"></MakeRoomProgress>
    </div>
    <div>
      <div class="font-bold text-xl pt-2">언제부터 언제까지 진행되나요?</div>
      <div class="text-sm">3일부터 최대 14일까지 설정할 수 있어요.</div>
      <div id="check" class="text-red-600 text-sm"></div>
    </div>
    <div class="flex justify-center">
      <div class="w-11/12">
        <Calendar v-model.range="store.range" type="range"> </Calendar>
      </div>
    </div>
    <div class="flex justify-center items-end pb-6">
      <Button class="w-full rounded-full text-lg" @click="check">만들기</Button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { type IMakeRoomInput } from '~/repository/modules/interface/waitroom.interface';
const { $api } = useNuxtApp();
const store = useMakeRoomStore();
const router = useRouter();
const userInfo = ref();
const check = async (event: any): Promise<void> => {
  const element = document.getElementById('check');
  if (element == null) {
    return;
  }
  element.innerText = '';
  const period = store.diffDate(
    store.formatDate(store.range.start),
    store.formatDate(store.range.end),
  );
  if (
    store.diffDate(
      store.formatDate(new Date()),
      store.formatDate(store.range.start),
    ) <= 0
  ) {
    element.innerText = '시작일을 다시 설정해주세요!';
    event.preventDefault();
    return;
  } else if (period > 14 || period < 3) {
    element.innerText =
      '3일 이상 14일이하로 설정해주세요! 현재 : (' + period + '일)';
    event.preventDefault();
    return;
  }
  await move();
};
const move = async (): Promise<void> => {
  try {
    const input = ref<IMakeRoomInput>();
    const userStore = useUserStore();
    userInfo.value = userStore.getStoredUser();
    input.value = {
      setting: {
        title: store.name.trim(),
        start_date: store.formatDate(store.range.start),
        end_date: store.formatDate(store.range.end),
        question_type: store.type,
        max_member: store.number,
      },
      master: {
        id: userInfo.value.id,
        email: userInfo.value.email,
        username: userInfo.value.name,
      },
    };
    const data = await $api.make.createWaitRoom(input.value);
    await router.push(`/waitroom/${data.data.invite_code}/updateProfile`);
  } catch (error: any) {
    alert('잘못된 접근입니다. 다시 시도해주세요!');
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
