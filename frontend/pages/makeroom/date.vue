<template>
  <div class="box flex-col p-6">
    <div class="flex justify-between">
      <BackButton color="black"></BackButton>
      <h1 class="text-xl font-bold">방 만들기</h1>
      <div class="mr-6"></div>
    </div>
    <MakeRoomProgress :cur-page="'4'"></MakeRoomProgress>
    <div class="font-bold mb-4">언제부터 언제까지 진행되나요?</div>
    <div>최대 14일까지 설정할 수 있어요.</div>
    <div id="check" class="text-red-600"></div>
    <div class="flex justify-center items-center">
      <Calendar v-model.range="store.range" type="range"> </Calendar>
    </div>
    <div>
      <Button @click="check">만들기</Button>
    </div>
  </div>
</template>

<script setup lang="ts">
interface ApiResponse {
  data: {
    invite_code: string;
    created_at: string;
    master_id: string;
    setting: {
      title: string;
      start_date: string;
      end_date: string;
      question_type: string;
      max_member: number;
    };
  };
}
const store = useMakeRoomStore();
const router = useRouter();
const userItem = localStorage.getItem('user');
const userInfo = ref();
if (userItem !== null) {
  userInfo.value = JSON.parse(userItem);
}
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
    const data = ref<ApiResponse | null>(null);
    data.value = await $fetch<ApiResponse>('https://api-dev.oomool.site/temp', {
      method: 'POST',
      body: JSON.stringify({
        setting: {
          title: store.name,
          start_date: store.formatDate(store.range.start),
          end_date: store.formatDate(store.range.end),
          question_type: store.type,
          max_member: store.number,
        },
        master: {
          email: userInfo.value.email,
          username: userInfo.value.name,
        },
      }),
    });
    if (data.value !== null) {
      await router.push(
        `/waitroom/${data.value.data.invite_code}/updateProfile`,
      );
    }
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
