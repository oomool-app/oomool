<template>
  <div class="flex-col m-4">
    <BackButton></BackButton>
    <h1 class="flex justify-center text-xl m-4 font-bold">방 만들기</h1>
    <MakeRoomProgress :cur-page="'1'"></MakeRoomProgress>
    <div class="font-bold mb-4">우리 방의 이름을 정해주세요!</div>
    <div>
      <input
        class="w-full border border-purple-800 rounded h-8"
        placeholder="김병현"
      />
    </div>
    <div>
      <NuxtLink to="type"><Button>다음</Button></NuxtLink>
    </div>
  </div>
</template>
