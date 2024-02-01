<template>
  <div class="flex-col m-4">
    <BackButton color="black"></BackButton>
    <h1 class="flex justify-center text-xl m-4 font-bold">방 만들기</h1>
    <MakeRoomProgress :cur-page="'4'"></MakeRoomProgress>
    <div class="font-bold mb-4">언제부터 언제까지 진행되나요?</div>
    <div>최대 14일까지 설정할 수 있어요.</div>
    <div id="check" class="text-red-600"></div>
    <div class="flex justify-center items-center">
      <Calendar v-model.range="store.range" type="range"> </Calendar>
    </div>
    <div>
      <NuxtLink to="/waitroom/1"
        ><Button @click="check">만들기</Button></NuxtLink
      >
    </div>
  </div>
</template>

<script setup lang="ts">
const store = useMakeRoomStore();
const userInfo: any = localStorage.getItem('user');
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

  const { data } = await useFetch('https://api-dev.oomool.site/temp', {
    method: 'POST',
    body: JSON.stringify({
      setting: {
        title: store.name,
        start_date: store.formatDate(store.range.start),
        end_date: store.formatDate(store.range.end),
        question_type: 'BF',
        max_member: store.number,
      },
      master: {
        user_id: userInfo.user_id,
        user_email: userInfo.user_email,
        player_nickname: userInfo.user_nickname,
        player_background_color: userInfo.player_background_color,
        player_avatar_url: userInfo.player_avatar_url,
      },
    }),
  });
};
</script>
