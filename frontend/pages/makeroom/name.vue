<template>
  <div class="flex-col h-screen">
    <div class="grid grid-cols-3">
      <div class="flex items-center">
        <BackButton color="black"></BackButton>
      </div>
      <div class="flex justify-center items-center">
        <h1 class="text-xl font-bold">방 만들기</h1>
      </div>
    </div>
    <MakeRoomProgress :cur-page="'1'"></MakeRoomProgress>
    <div class="font-bold">우리 방의 이름을 정해주세요!</div>
    <div>
      <input
        id="userName"
        v-model="store.name"
        name="userName"
        type="text"
        class="w-full border border-purple-800 rounded h-8"
        :placeholder="userName"
        autocomplete="off"
      />
    </div>
    <div class="flex justify-center items-end grow">
      <NuxtLink to="type"><Button>다음</Button></NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
const store = useMakeRoomStore();
const userName = ref<string>('계정 이름');
</script>
