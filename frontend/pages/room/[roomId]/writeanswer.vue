<template>
  <div class="pl-6 pr-6">
    <div class="flex justify-between pt-6 pb-6">
      <BackButton color="#61339B"></BackButton>
      <FeedHeader
        header-name="답변 작성하기"
        class="text-[#61339B]"
      ></FeedHeader>
      <div></div>
    </div>
    <div>
      <input
        id="upload-image"
        hidden
        type="file"
        class="h-10 px-4 py-2"
        @change="previewImage"
      />
      <div class="flex justify-center mt-4">
        <label for="upload-image">
          <img id="preview" class="w-56" />
          <div v-if="!isUploaded" class="flex justify-center items-center">
            <img
              class="w-56 h-56 inline-block"
              src="/img/사진등록유령.png"
              alt="사진 등록 이미지"
            />
          </div>
        </label>
      </div>
    </div>
    <TodayQuestion
      class="mt-4 text-xl font-extrabold"
      :question="question"
    ></TodayQuestion>
    <textarea
      class="w-80 h-40 mt-4 rounded-md outline-primary"
      placeholder=" 마니띠를 떠올리며 오늘의 질문에 답변해 주세요!"
    ></textarea>
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
    console.log(isUploaded.value);
    reader.onload = (e) => {
      if (e?.target != null && typeof e.target.result === 'string') {
        const previewElement = document.getElementById(
          'preview',
        ) as HTMLImageElement;
        previewElement.src = e.target.result;
        isUploaded.value = true;
      }
    };
    reader.readAsDataURL(input.files[0]);
  } else {
    const previewElement = document.getElementById(
      'preview',
    ) as HTMLImageElement;
    previewElement.src = '';
    isUploaded.value = false;
  }
};
</script>
<style scoped>
input[type='file']::file-selector-button {
  background-color: #61339b;
}
</style>
