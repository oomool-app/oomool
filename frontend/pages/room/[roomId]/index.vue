<template>
  <div id="top-container">
    <div class="bg-[#61339b] rounded-b-2xl h-32 p-6 pb-28 drop-shadow-xl">
      <div class="flex justify-between">
        <BackButton color="white" />
        <div
          class="d-day border text-primary border-white bg-white h-9 w-14 rounded-3xl text-center font-bold py-1"
        >
          D-{{ dDay }}
        </div>
      </div>
      <div class="flex justify-between mt-6">
        <FeedHeader
          class="text-white text-3xl w-3/4 whitespace-nowrap"
          :header-name="teamName"
        ></FeedHeader>
      </div>
    </div>
    <div class="bottom-container p-4 pt-6">
      <div class="my-manitti">
        <ContentHeader header-name="내 마니띠" />
        <div
          class="flex justify-evenly items-center my-manitti border-2 border-[#61339b] bg-gradient-to-r from-[#61339b] to-[#a8bdf9] h-32 mt-4 mb-8 rounded-xl"
        >
          <img :src="manittiAvatar" alt="" class="ghost h-3/4 w-auto" />
          <p
            v-if="!whoIsManitti"
            class="btn bg-primary w-40 h-10 text-white text-center py-1 text-xl rounded-full font-bold drop-shadow-2xl"
            @click="discoverManitti()"
          >
            누구게?
          </p>
          <p
            v-else-if="whoIsManitti"
            class="bg-white w-40 h-10 text-center text-primary py-1 text-xl rounded-full font-bold"
            @click="hideManitti()"
          >
            {{ manittiName }}
          </p>
        </div>
      </div>
      <div class="question-container">
        <div class="flex justify-between">
          <ContentHeader header-name="오늘의 질문" />
          <NuxtLink :to="`${roomUid}/writeanswer`" class="text-sm py-1">
            <div class="text-[#61339b] font-semibold">
              <svg
                xmlns="http://www.w3.org/2000/svg"
                fill="none"
                viewBox="0 0 24 24"
                stroke-width="1.5"
                stroke="currentColor"
                class="w-4 h-4 inline"
              >
                <path
                  stroke="#61339b"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                  d="m16.862 4.487 1.687-1.688a1.875 1.875 0 1 1 2.652 2.652L6.832 19.82a4.5 4.5 0 0 1-1.897 1.13l-2.685.8.8-2.685a4.5 4.5 0 0 1 1.13-1.897L16.863 4.487Zm0 0L19.5 7.125"
                />
              </svg>
              답변하기
            </div></NuxtLink
          >
        </div>
        <div
          class="border-2 border-primary rounded-lg p-5 mt-4 mb-10 text-center font-semibold"
        >
          {{ todayQuestion }}
        </div>
      </div>
      <div class="answer-container">
        <div class="flex justify-between">
          <ContentHeader header-name="우리들의 답변" />
          <TotalButton :link="`${roomUid}/feed`" text="전체보기" />
        </div>
        <div
          v-if="isCompleted"
          class="p-5 mt-5 mb-10 rounded-lg bg-gradient-to-r from-[#61339b] to-[#a8bdf9] border-primary border-2 text-white text-center font-semibold"
        >
          내 마니또가 답변을 등록했어요!
        </div>
        <div
          v-if="!isCompleted"
          class="p-5 mt-5 mb-10 rounded-lg bg-gradient-to-r from-[#61339b] to-[#a8bdf9] border-primary border-2 text-white text-center font-semibold"
        >
          아직 답변이 등록되지 않았어요..
        </div>
      </div>
      <div class="members-container mb-6">
        <div class="flex justify-between">
          <ContentHeader header-name="멤버 목록" />
        </div>
        <div class="flex overflow-auto">
          <div v-for="member in members" :key="member.user_id">
            <RoomProfile :member="member"></RoomProfile>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { type IGetMyManittoInput } from '~/repository/modules/interface/players.interface';
const { $api } = useNuxtApp();
const route = useRoute();
const userStore = useUserStore();
const roomUid = route.params.roomId as string;
const teamName = ref();
const members = ref();
const endDate = ref<Date | undefined>();
const dDay = ref();
const todayQuestion = ref();
const manittiName = ref();
const manittiAvatar = ref();
// 문답방 전체 정보 받아오기
const getRoomDetail = async (): Promise<void> => {
  try {
    const response = await $api.rooms.getRoomDetail(roomUid);
    members.value = response.data.players;
    teamName.value = response.data.setting.title;
    endDate.value = response.data.setting.end_date;
  } catch (error) {
    console.log(error);
  }
};

// D-day 계산
const getDDay = async (): Promise<void> => {
  try {
    const today: any = new Date();
    if (endDate.value !== undefined) {
      const endDateObj = new Date(endDate.value);
      endDateObj.setHours(0);
      const timeDiff = endDateObj.getTime() - today.getTime();
      const remainingDays = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
      dDay.value = remainingDays;
    }
  } catch (error) {
    console.log(error);
  }
  if (dDay.value === 0) {
    dDay.value = 'Day';
  }
};

// 오늘의 질문 조회
const getDailyQuestion = async (): Promise<void> => {
  try {
    const response = await $api.question.getDailyQuestion(roomUid);
    todayQuestion.value = response.data.question;
  } catch (error) {
    console.log(error);
  }
};

// 내 마니띠 조회
const getMyManitti = async (): Promise<void> => {
  try {
    const userId = userStore.getStoredUser()?.id;
    if (userId !== null && userId !== undefined) {
      const response = await $api.players.getMyManitti({ roomUid, userId });
      manittiName.value = response.data.manitti.player_nickname;
      manittiAvatar.value = response.data.manitti.player_avatar_url;
    }
  } catch (error) {
    console.error(error);
  }
};

const sequence = ref();
// 피드 질문 시퀀스 조회
const getSequenceNumber = async (): Promise<void> => {
  const response = await $api.question.getDailyQuestion(roomUid);
  sequence.value = response.data.sequence;
};

// 내 마니또 조회
const manittoId = ref();
const getMyManitto = async (): Promise<void> => {
  const userId = userStore.getStoredUser()?.id;
  if (userId !== null) {
    const data: IGetMyManittoInput = { roomUid, userId };
    const response = await $api.players.getMyManitto(data);
    manittoId.value = response.data.manitto.user_id;
  }
};

const isCompleted = ref(false);
// 피드 답변 전체 조회
const getFeedAnswer = async (): Promise<void> => {
  const data = { roomUid, sequence: sequence.value };
  const response = await $api.feeds.getAllFeedsByRoomUidAndSequence(data);
  const allAnswer = response.data.room_feed_dto_list;
  for (let i = 0; i < allAnswer.length; i++) {
    if (allAnswer[i].user_id === manittoId.value) {
      isCompleted.value = true;
      return;
    }
  }
};

onMounted(async () => {
  await getRoomDetail();
  await getDDay();
  await getDailyQuestion();
  await getMyManitti();
  await getSequenceNumber();
  await getMyManitto();
  await getFeedAnswer();
});

const whoIsManitti = ref(false);
const discoverManitti = (): void => {
  whoIsManitti.value = true;
};
const hideManitti = (): void => {
  whoIsManitti.value = false;
};
</script>
<style scope>
#top-container {
  animation: fade-in 0.5s ease-in-out;
}

.btn:active {
  background-color: #501b90;
}

.my-manitti {
  animation: fade-in 0.8s ease-in-out;
}

.question-container {
  animation: fade-in2 0.8s ease-in-out;
}

.answer-container {
  animation: fade-in3 0.8s ease-in-out;
}

.members-container {
  animation: fade-in4 0.8s ease-in-out;
}

.btn {
  animation: button 1s infinite;
}
@keyframes button {
  0% {
    transform: scale(1);
  }

  50% {
    transform: scale(1.05);
    background-color: #7b42c1;
  }

  100% {
    transform: scale(1);
  }
}

@keyframes fade-in {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}

@keyframes fade-in2 {
  0% {
    opacity: 0;
  }

  20% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
@keyframes fade-in3 {
  0% {
    opacity: 0;
  }

  40% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
@keyframes fade-in4 {
  0% {
    opacity: 0;
  }

  60% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>
