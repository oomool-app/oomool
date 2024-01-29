<template>
  <div>
    <div class="flex justify-between p-4 pt-6 pb-6">
      <BackButton color="#61339B"></BackButton>
      <FeedHeader
        header-name="답변 작성하기"
        class="text-[#61339B]"
      ></FeedHeader>
      <div></div>
    </div>

    <TodayQuestion :question="question"></TodayQuestion>
    <textarea
      class="w-80"
      placeholder="마니띠를 떠올리며 오늘의 질문에 답변해 주세요"
    ></textarea>
    <div>
      <input
        id="upload-image"
        hidden
        type="file"
        class="h-10 px-4 py-2"
        @change="previewImage"
      />
      <label for="upload-image">
        <img id="preview" class="w-80" />
        <div v-if="!isUploaded">
          <svg
            xmlns="http://www.w3.org/2000/svg"
            viewBox="0 0 24 24"
            fill="currentColor"
            class="w-80 h-80"
          >
            <path
              fill-rule="evenodd"
              d="M1.5 6a2.25 2.25 0 0 1 2.25-2.25h16.5A2.25 2.25 0 0 1 22.5 6v12a2.25 2.25 0 0 1-2.25 2.25H3.75A2.25 2.25 0 0 1 1.5 18V6ZM3 16.06V18c0 .414.336.75.75.75h16.5A.75.75 0 0 0 21 18v-1.94l-2.69-2.689a1.5 1.5 0 0 0-2.12 0l-.88.879.97.97a.75.75 0 1 1-1.06 1.06l-5.16-5.159a1.5 1.5 0 0 0-2.12 0L3 16.061Zm10.125-7.81a1.125 1.125 0 1 1 2.25 0 1.125 1.125 0 0 1-2.25 0Z"
              clip-rule="evenodd"
            />
          </svg>
        </div>
      </label>
    </div>
    <Button>답변 등록</Button>
  </div>
</template>

<script setup lang="ts">
const question = ref('오늘의 질문입니다');
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
