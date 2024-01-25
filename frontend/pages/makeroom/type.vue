<template>
  <div class="flex-col m-4">
    <BackButton></BackButton>
    <h1 class="flex justify-center text-xl m-4 font-bold">방 만들기</h1>
    <MakeRoomProgress :cur-page="'2'"></MakeRoomProgress>
    <div class="font-bold mb-4">우리들, 어떤 사이예요?</div>
    <div>매일 제공되는 질문의 유형이 결정돼요!</div>
    <MakeRoomTypeButton></MakeRoomTypeButton>
    <div>
      <NuxtLink to="number"><Button>다음</Button></NuxtLink>
    </div>
  </div>
</template>
