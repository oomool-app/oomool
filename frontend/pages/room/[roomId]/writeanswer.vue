<template>
  <div class="pl-6 pr-6">
    <div class="header flex justify-between pt-6 pb-6">
      <BackButton color="#61339B"></BackButton>
      <FeedHeader
        header-name="답변 작성하기"
        class="text-[#61339B]"
      ></FeedHeader>
      <div class="w-3"></div>
    </div>
    <div class="img-container">
      <input
        id="upload-image"
        ref="imageFile"
        accept="image/*"
        hidden
        files
        type="file"
        class="h-10 px-4 py-2"
        @input="previewImage"
      />
      <div class="flex flex-col justify-center items-center mt-4">
        <AlertDialog>
          <AlertDialogTrigger>
            <svg
              v-if="isUploaded"
              class="w-6 h-6 bg-white rounded-full relative left-28 top-7"
              xmlns="http://www.w3.org/2000/svg"
              viewBox="0.6 2.4 16 16"
              fill="red"
            >
              <path
                fill-rule="evenodd"
                d="M16.417 10.283A7.917 7.917 0 1 1 8.5 2.366a7.916 7.916 0 0 1 7.917 7.917zm-6.804.01 3.032-3.033a.792.792 0 0 0-1.12-1.12L8.494 9.173 5.46 6.14a.792.792 0 0 0-1.12 1.12l3.034 3.033-3.033 3.033a.792.792 0 0 0 1.12 1.119l3.032-3.033 3.033 3.033a.792.792 0 0 0 1.12-1.12z"
                clip-rule="evenodd"
              />
            </svg>
          </AlertDialogTrigger>
          <AlertDialogContent>
            <AlertDialogHeader>
              <AlertDialogTitle
                >이미지 등록을 취소하시겠습니까?</AlertDialogTitle
              >
            </AlertDialogHeader>
            <AlertDialogFooter class="flex flex-row justify-evenly">
              <AlertDialogCancel class="w-20">아니요</AlertDialogCancel>
              <AlertDialogAction class="w-20" @click="removeImage()"
                >네</AlertDialogAction
              >
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>

        <label for="upload-image">
          <div>
            <img v-show="isUploaded" id="preview" class="w-56 mt-3" />
            <img
              v-if="!isUploaded"
              class="w-56 h-56 inline-block rounded-xl"
              src="/img/photoGhost.png"
              alt="사진 등록 이미지"
            />
          </div>
        </label>
      </div>
      <div class="flex justify-end"></div>
    </div>
    <div class="input-container flex flex-col justify-center items-center">
      <TodayQuestion
        class="mt-6 text-xl font-extrabold w-11/12"
        :question="dailyQuestion"
      ></TodayQuestion>
      <textarea
        v-model="content"
        class="w-11/12 h-40 mt-6 rounded-md outline-primary"
        placeholder=" 마니띠를 떠올리며 오늘의 질문에 답변해 주세요!"
      ></textarea>
    </div>
    <div class="input-container flex justify-center">
      <AlertDialog>
        <AlertDialogTrigger>
          <Button class="mt-6 mb-6" @click="check()">등록하기</Button>
        </AlertDialogTrigger>
        <AlertDialogContent v-if="!isEmpty">
          <AlertDialogHeader>
            <AlertDialogTitle>답변을 등록하시겠습니까?</AlertDialogTitle>
          </AlertDialogHeader>
          <AlertDialogFooter class="flex flex-row justify-evenly">
            <AlertDialogCancel class="w-20">아니요</AlertDialogCancel>
            <AlertDialogAction class="w-20" @click="writeFeedAnswer()"
              >네</AlertDialogAction
            >
          </AlertDialogFooter>
        </AlertDialogContent>
        <AlertDialogContent v-else-if="isEmpty">
          <AlertDialogHeader>
            <AlertDialogTitle>답변 내용을 입력해 주세요!</AlertDialogTitle>
          </AlertDialogHeader>
          <AlertDialogFooter class="flex flex-row justify-evenly">
            <AlertDialogAction class="w-20">OK!</AlertDialogAction>
          </AlertDialogFooter>
        </AlertDialogContent>
      </AlertDialog>
    </div>
  </div>
</template>

<script setup lang="ts">
const { $api } = useNuxtApp();
const route = useRoute();
const router = useRouter();
const userStore = useUserStore();
const roomUid = route.params.roomId as string;
const dailyQuestion = ref();
const content = ref();
const sequence = ref();
const roomQuestionId = ref();
const imageFile = ref();
const userId = ref();
// 데일리 질문 조회 및 오늘의 sequence 번호 조회하기
const getDailyQuestion = async (): Promise<void> => {
  const response = await $api.question.getDailyQuestion(roomUid);
  dailyQuestion.value = response.data.question;
  sequence.value = response.data.sequence;
};

const formData = new FormData();

// 답변 등록
const writeFeedAnswer = async (): Promise<void> => {
  const data = formData;
  formData.append('content', content.value as string);
  formData.append('author_id', userId.value as string);
  formData.append('room_question_id', roomQuestionId.value as string);
  try {
    await $api.feeds.writeFeedAnswer(data);
    await router.replace({
      path: '/room/' + roomUid + '/feed',
    });
  } catch (error) {
    console.error(error);
  }
};

// sequence 번호로 오늘의 room_question_id 조회하기
const getRoomRequestionId = async (): Promise<void> => {
  const data = { roomUid, sequence: sequence.value };
  const response = await $api.feeds.getAllFeedsByRoomUidAndSequence(data);
  roomQuestionId.value = response.data.room_question_id;
};

const isUploaded = ref(false);
// 이미지 업로드 미리보기
const previewImage = (e: any): void => {
  const input = document.getElementById(
    'upload-image',
  ) as HTMLInputElement | null;

  if (input?.files?.[0] != null) {
    const reader = new FileReader();
    reader.onload = (e) => {
      console.log(e.target);
      if (e?.target != null && typeof e.target.result === 'string') {
        const previewElement = document.getElementById(
          'preview',
        ) as HTMLImageElement;
        previewElement.src = e.target.result;
        imageFile.value = e.target.result;
        isUploaded.value = true;
      }
    };
    const file: Blob = e.target.files[0];
    formData.append('file_list', file);
    reader.readAsDataURL(input.files[0]);
    input.value = '';
  } else {
    const previewElement = document.getElementById(
      'preview',
    ) as HTMLImageElement;
    previewElement.src = '';
    isUploaded.value = false;
  }
};

const isEmpty = ref(true);
const check = (): void => {
  if (content.value.trim() !== '') {
    isEmpty.value = false;
  } else {
    isEmpty.value = true;
  }
};

// 이미지 제거
const removeImage = (): void => {
  const previewElement = document.getElementById('preview') as HTMLImageElement;
  previewElement.src = '';
  isUploaded.value = false;
};

onMounted(async () => {
  userId.value = userStore.getStoredUser()?.id;
  await getDailyQuestion();
  await getRoomRequestionId();
});
</script>
<style scoped>
input[type='file']::file-selector-button {
  background-color: #61339b;
}

.header {
  animation: fade-in 0.8s ease-in-out;
}

.img-container {
  animation: fade-in2 1.2s ease-in-out;
}

.input-container {
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

  30% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}
</style>
