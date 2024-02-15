<template>
  <div>
    <div
      v-if="isDownloaded"
      class="w-full h-screen fixed flex flex-col justify-center items-center z-10 bg-gray-50 opacity-85"
    >
      <img src="/img/load.gif" alt="" />
      <p class="mt-2 font-bold text-primary">이미지 저장 중입니다.</p>
    </div>
    <div class="header flex justify-between p-6 pb-2">
      <BackButton color="#61339b"></BackButton>
      <NuxtLink to="/">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          viewBox="0 0 24 24"
          fill="#61339b"
          stroke="#61339b"
          stroke-width="1"
          class="w-7 h-7 mr-2"
        >
          <path
            d="M11.47 3.841a.75.75 0 0 1 1.06 0l8.69 8.69a.75.75 0 1 0 1.06-1.061l-8.689-8.69a2.25 2.25 0 0 0-3.182 0l-8.69 8.69a.75.75 0 1 0 1.061 1.06l8.69-8.689Z"
          />
          <path
            d="m12 5.432 8.159 8.159c.03.03.06.058.091.086v6.198c0 1.035-.84 1.875-1.875 1.875H15a.75.75 0 0 1-.75-.75v-4.5a.75.75 0 0 0-.75-.75h-3a.75.75 0 0 0-.75.75V21a.75.75 0 0 1-.75.75H5.625a1.875 1.875 0 0 1-1.875-1.875v-6.198a2.29 2.29 0 0 0 .091-.086L12 5.432Z"
          />
        </svg>
      </NuxtLink>
    </div>

    <div>
      <div class="answer-container flex flex-col justify-start">
        <ContentHeader
          class="p-4 pl-12"
          header-name="마니또가 한 생각"
        ></ContentHeader>
        <div class="flex flex-col items-center justify-center">
          <Carousel
            :autoplay="4000"
            :pause-autoplay-on-hover="true"
            class="mb-4 w-full"
          >
            <Slide v-for="(item, index) in result" :key="item.feed_id">
              <div class="w-3/4 carousel__item">
                <ResultFinalCard
                  :result="item"
                  :index="index"
                ></ResultFinalCard>
              </div>
            </Slide>
            <template #addons>
              <Pagination />
              <Navigation />
            </template>
          </Carousel>
          <AlertDialog>
            <AlertDialogTrigger as-child>
              <Button class="w-1/2 m-4 rounded-xl"
                >이미지로 저장하기 &nbsp;&nbsp;

                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke-width="2"
                  stroke="currentColor"
                  class="w-5 h-5"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    d="M3 16.5v2.25A2.25 2.25 0 0 0 5.25 21h13.5A2.25 2.25 0 0 0 21 18.75V16.5M16.5 12 12 16.5m0 0L7.5 12m4.5 4.5V3"
                  />
                </svg>
              </Button>
            </AlertDialogTrigger>

            <AlertDialogContent>
              <AlertDialogTitle class="font-bold text-primary text-xl"
                >저장미리보기</AlertDialogTitle
              >
              <ScrollArea
                class="h-80 w-full rounded-md border flex-col flex items-center"
              >
                <AlertDialogHeader class="flex-col flex items-center">
                  <AlertDialogDescription class="pt-10">
                    <div
                      id="image"
                      class="w-full flex justify-center flex-col items-center"
                    >
                      <div
                        v-for="(item, index) in result"
                        :key="item.feed_id"
                        class="flex flex-col items-center"
                      >
                        <div
                          class="w-3/4 carousel__item overflow-x-hidden overflow-y-auto"
                        >
                          <ResultSaveCard
                            class="h-1/6"
                            :result="item"
                            :index="index"
                          ></ResultSaveCard>
                        </div>
                      </div>
                    </div>
                  </AlertDialogDescription>
                </AlertDialogHeader>
              </ScrollArea>
              <AlertDialogFooter class="flex">
                <AlertDialogCancel>취소</AlertDialogCancel>
                <AlertDialogAction @click="saveImage">저장</AlertDialogAction>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialog>
        </div>
      </div>
    </div>
    <div class="footer-container flex justify-center mt-8 mb-8">
      <div class="w-3/4 p-3 flex justify-between bg-purple-200 rounded-xl">
        <div class="w-full pr-2 flex flex-col">
          <div class="text-gray-400 pb-4">
            <p class="text-sm font-bold">{{ roomName }}</p>
            <p style="font-size: 0.65rem">{{ startDate }} ~ {{ endDate }}</p>
          </div>
          <div class="w-full flex flex-col items-end">
            <p class="font-semibold">당신의 마니또</p>
            <p class="text-xl font-bold">{{ manittoName }}</p>
          </div>
        </div>
        <img class="w-20 h-auto" :src="manittoAvatar" alt="" />
      </div>
    </div>
  </div>
</template>
<script setup lang="ts">
import 'vue3-carousel/dist/carousel.css';
import { Carousel, Navigation, Pagination, Slide } from 'vue3-carousel';
import * as htmlToImage from 'html-to-image';
import saveAs from 'file-saver';
const { $api } = useNuxtApp();
const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const roomUid = route.params.roomId as string;
const roomName = ref();
const startDate = ref();
const endDate = ref();
// const questions = ref<RoomQuestionList[]>();
const manittoName = ref();
const manittoAvatar = ref();

// 방 정보 상세 조회
const getRoomDetail = async (): Promise<void> => {
  try {
    const response = await $api.rooms.getRoomDetail(roomUid);
    roomName.value = response.data.setting.title;
    startDate.value = response.data.setting.start_date;
    endDate.value = response.data.setting.end_date;
  } catch (error) {
    console.error(error);
  }
};

// 내 마니또 조회
const getMyManitto = async (): Promise<void> => {
  try {
    const userId = userStore.getStoredUser()?.id;
    if (userId !== null && userId !== undefined) {
      const response = await $api.players.getMyManitto({ roomUid, userId });
      manittoName.value = response.data.manitto.player_nickname;
      manittoAvatar.value = response.data.manitto.player_avatar_url;
    }
  } catch (error) {
    console.error(error);
  }
};

const result = ref();
// 내 마니또 전체 답변 조회
const getAllMyManittoFeedAnswers = async (): Promise<void> => {
  try {
    const userId = userStore.getStoredUser()?.id;
    if (userId !== null && userId !== undefined) {
      const response = await $api.feeds.getAllMyManittoFeedAnswers({
        roomUid,
        userId,
      });
      result.value = response.data.result_manitto_list;
    }
  } catch (error) {
    console.error(error);
  }
};

const isDownloaded = ref(false);

// 답변 이미지로 저장
const saveImage = async (): Promise<void> => {
  isDownloaded.value = true;
  const el = document.getElementById('image');
  const element = el as unknown as HTMLElement;
  await htmlToImage
    .toBlob(element, {
      quality: 0.5,
      backgroundColor: 'white',
      skipFonts: true,
    })
    .then(function (blob) {
      setTimeout(() => {
        saveAs(blob, `내 마니또 ${manittoName.value}의 답변.png`);
        isDownloaded.value = false;
        router.go(0);
      }, 1000);
    });
};
onMounted(async () => {
  await getRoomDetail();
  await getMyManitto();
  await getAllMyManittoFeedAnswers();
});
</script>
<style scoped>
.carousel-item {
  min-height: 200px;
  width: 100%;
  background-color: var(--vc-clr-primary);
  color: var(--vc-clr-white);
  font-size: 20px;
  border-radius: 8px;
  display: flex;
  justify-content: center;
  align-items: center;
}

.slide-img {
  max-height: 740px;
  max-width: 100%;
}

.header {
  animation: fade-in 0.8s ease-in-out;
}

.answer-container {
  animation: fade-in2 1.2s ease-in-out;
}

.footer-container {
  animation: fade-in3 1.2s ease-in-out;
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

  16% {
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

  30% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>
