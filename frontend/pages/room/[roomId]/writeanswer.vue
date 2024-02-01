<template>
  <div class="pl-6 pr-6">
    <div class="flex justify-between pt-6 pb-6">
      <BackButton color="#61339B"></BackButton>
      <FeedHeader
        header-name="답변 작성하기"
        class="text-[#61339B]"
      ></FeedHeader>
      <div class="w-3"></div>
    </div>
    <div>
      <input
        id="upload-image"
        accept="image/*"
        hidden
        type="file"
        class="h-10 px-4 py-2"
        @input="previewImage()"
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
    <div class="flex flex-col justify-center items-center">
      <TodayQuestion
        class="mt-4 text-xl font-extrabold"
        :question="question"
      ></TodayQuestion>
      <textarea
        v-model="answer"
        class="w-11/12 h-40 mt-4 rounded-md outline-primary"
        placeholder=" 마니띠를 떠올리며 오늘의 질문에 답변해 주세요!"
      ></textarea>
    </div>
    <div class="flex justify-center">
      <AlertDialog>
        <AlertDialogTrigger>
          <Button class="mt-6">등록하기</Button>
        </AlertDialogTrigger>
        <AlertDialogContent v-if="answer !== ''">
          <AlertDialogHeader>
            <AlertDialogTitle>답변을 등록하시겠습니까?</AlertDialogTitle>
          </AlertDialogHeader>
          <AlertDialogFooter class="flex flex-row justify-evenly">
            <AlertDialogCancel class="w-20">아니요</AlertDialogCancel>
            <AlertDialogAction class="w-20" @click="registAnswer()"
              >네</AlertDialogAction
            >
          </AlertDialogFooter>
        </AlertDialogContent>
        <AlertDialogContent v-else-if="answer === ''">
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
const question = ref('내 친구가 좋아할만한 음악이나 장르가 어떻게 되나요?');
const isUploaded = ref(false);
const previewImage = (): void => {
  const input = document.getElementById(
    'upload-image',
  ) as HTMLInputElement | null;
  if (input?.files?.[0] != null) {
    const reader = new FileReader();
    reader.onload = (e) => {
      if (e?.target != null && typeof e.target.result === 'string') {
        const previewElement = document.getElementById(
          'preview',
        ) as HTMLImageElement;
        previewElement.src = e.target.result;
        console.log(e.target);
        isUploaded.value = true;
      }
    };
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
const removeImage = (): void => {
  const previewElement = document.getElementById('preview') as HTMLImageElement;
  previewElement.src = '';
  isUploaded.value = false;
};

const answer = ref('');
const registAnswer = (): void => {};
</script>
<style scoped>
input[type='file']::file-selector-button {
  background-color: #61339b;
}
</style>
