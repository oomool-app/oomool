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
              >{{ selectedManitto }}님으로
              최종결정하시겠습니까?</AlertDialogTitle
            >
            <AlertDialogDescription>
              한번 결정하게 되면, 마니또 결과를 변경할 수 없습니다!
            </AlertDialogDescription>
          </AlertDialogHeader>
          <AlertDialogFooter class="flex flex-row justify-evenly">
            <AlertDialogCancel>취소하기</AlertDialogCancel>
            <AlertDialogAction @click="goToFinal()">결정하기</AlertDialogAction>
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
      <img class="img w-24 h-auto mt-14" src="/img/guessGhost.png" alt="" />
      <div class="que">
        <p class="font-extrabold text-3xl mt-4">
          내 마니또는 <span class="text-primary">누구</span>였을까요?
        </p>
        <p class="mt-1 mb-6">진실은 언제나 하나! 내 마니또를 추측해보세요.</p>
      </div>
    </div>
    <div class="users flex flex-wrap justify-center">
      <div
        v-for="member in members"
        :key="member.user_id"
        :v-model="whoIsCheck"
        class="m-2 rounded-lg"
        :class="{
          'bg-gradient-to-t from-[#61339B]  to-[#A8BDF9]':
            whoIsCheck === member.user_id,
        }"
        @click="selectManito(member.user_id, member.player_nickname)"
      >
        <ManitoCard
          v-if="member.user_id !== userId"
          :member="member"
          :class="{
            'text-white': whoIsCheck === member.user_id,
            'text-primary': whoIsCheck !== member.user_id,
          }"
          :see="see"
        ></ManitoCard>
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const { $api } = useNuxtApp();
const members = ref();
const manittoId = ref();
const roomUid = route.params.roomId as string;
const userId = ref();
// 전체 유저 정보 조회
const getAllMembersByRoomUid = async (): Promise<void> => {
  const response = await $api.rooms.getRoomDetail(roomUid);
  members.value = response.data.players;
};

// 내 마니또 조회
const getMyManitto = async (): Promise<void> => {
  try {
    const userId = userStore.getStoredUser()?.id;
    if (userId !== undefined && userId !== null) {
      const response = await $api.players.getMyManitto({ roomUid, userId });
      manittoId.value = response.data.manitto.user_id;
    }
  } catch (error) {
    console.error(error);
  }
};

// 결정하기 버튼 클릭 시 예측 정보 저장하기
const goToFinal = async (): Promise<void> => {
  const req = { guess: false };
  const userId = userStore.getStoredUser()?.id;
  try {
    if (whoIsCheck.value === manittoId.value) {
      req.guess = true;
    }
    if (userId !== undefined && userId !== null) {
      const response = await $api.players.saveResultExpectedManitto(
        roomUid,
        userId,
        req,
      );
    }
  } catch (error) {
    console.error(error);
  }
  await router.replace({ path: `/final/${roomUid}` });
};

onMounted(async () => {
  userId.value = userStore.getStoredUser()?.id;
  await getAllMembersByRoomUid();
  await getMyManitto();
});

const see = ref(true);
const selectedManitto = ref<string | undefined>('');
const whoIsCheck = ref<number | undefined>(undefined);
const selectManito = (id: number, name: string): void => {
  if (whoIsCheck.value !== id) {
    whoIsCheck.value = id;
    selectedManitto.value = name;
  } else if (whoIsCheck.value === id) {
    whoIsCheck.value = undefined;
    selectedManitto.value = '';
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
