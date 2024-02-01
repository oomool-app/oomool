<template>
  <div>
    <div class="flex justify-between p-6 pb-4 sticky top-0 bg-background">
      <BackButton color="#61339B"></BackButton>
      <AlertDialog>
        <AlertDialogTrigger class="text-primary text-lg"
          >결정하기</AlertDialogTrigger
        >
        <AlertDialogContent v-if="whoIsCheck !== undefined">
          <AlertDialogHeader>
            <AlertDialogTitle
              >{{ manito }}님으로 최종결정하시겠습니까?</AlertDialogTitle
            >
            <AlertDialogDescription>
              한번 결정하게 되면, 마니또 결과를 변경할 수 없습니다!
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter class="flex flex-row justify-evenly">
            <AlertDialogCancel>취소하기</AlertDialogCancel>
            <AlertDialogAction>결정하기</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
        <AlertDialogContent v-else-if="whoIsCheck === undefined">
          <AlertDialogHeader>
            <AlertDialogTitle>예상되는 마니또를 선택해주세요</AlertDialogTitle>
            <AlertDialogDescription>
              한번 결정하게 되면, 마니또 결과를 변경할 수 없습니다!
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter class="flex flex-row justify-evenly">
            <AlertDialogAction>확인</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
    <div class="pl-6 pr-6">
      <img class="img w-24 h-28 mt-14" src="/img/guessGhost.png" alt="" />
      <div class="que">
        <p class="font-extrabold text-3xl mt-4">
          내 마니또는 <span class="text-primary">누구</span>였을까요?
        </p>
        <p class="mt-1 mb-6">진실은 언제나 하나! 내 마니또를 추측해보세요.</p>
      </div>
    </div>
    <div class="users flex flex-wrap justify-center">
      <div
        v-for="user in users"
        :key="user.id"
        :v-model="whoIsCheck"
        class="m-2 rounded-lg"
        :class="{
          'bg-gradient-to-t from-[#61339B]  to-[#A8BDF9]':
            whoIsCheck === user.id,
        }"
        @click="selectManito(user.id, user.name)"
      >
        <ManitoCard
          :user="user"
          :class="{
            'text-white': whoIsCheck === user.id,
            'text-primary': whoIsCheck !== user.id,
          }"
          :see="see"
        ></ManitoCard>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
const users = ref([
  {
    id: 1,
    name: '박세정',
    background: '#FF33A1',
    profileUrl: '/img/bearGhost.png',
  },
  {
    id: 2,
    name: '김성수',
    background: '#FF5733',
    profileUrl: '/img/sangwooGhost.png',
  },
  {
    id: 3,
    name: '김현지',
    background: '#FFD700',
    profileUrl: '/img/rabbitGhost.png',
  },
  {
    id: 4,
    name: '전은평',
    background: '#33A1FF',
    profileUrl: '/img/musicGhost.png',
  },
  {
    id: 5,
    name: '김병현',
    background: '#607D8B',
    profileUrl: '/img/catGhost.png',
  },
  {
    id: 6,
    name: '정필모',
    background: '#795548',
    profileUrl: '/img/sangwooGhost.png',
  },
]);
const see = ref(true);
const manito = ref<string | undefined>('');
const whoIsCheck = ref<number | undefined>(undefined);
const selectManito = (id: number, name: string): void => {
  if (whoIsCheck.value !== id) {
    whoIsCheck.value = id;
    manito.value = name;
  } else if (whoIsCheck.value === id) {
    whoIsCheck.value = undefined;
    manito.value = '';
  }
};
</script>
<style scoped>
.img {
  animation: fadein 2.4s;
}

.que {
  animation: fadein2 2.4s;
}

.users {
  animation: fadein3 3.2s;
}
@keyframes fadein {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

@keyframes fadein2 {
  0% {
    opacity: 0;
  }

  16% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
@keyframes fadein3 {
  0% {
    opacity: 0;
  }

  30% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>
