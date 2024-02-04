<template>
  <div class="box flex-col h-screen p-6">
    <div class="flex justify-between">
      <div class="flex items-center">
        <BackButton color="black"></BackButton>
      </div>
      <h1 class="text-xl font-bold">방 만들기</h1>
      <div class="mr-6"></div>
    </div>
    <MakeRoomProgress :cur-page="'1'"></MakeRoomProgress>
    <div class="font-bold">우리 방의 이름을 정해주세요!</div>
    <div v-if="nameCheck" class="text-red-600">방의 이름을 정해주세요!</div>
    <div>
      <input
        id="userName"
        :value="store.name"
        name="userName"
        type="text"
        class="w-full border border-purple-800 rounded h-8"
        placeholder="방 이름"
        autocomplete="off"
        @input="changeInput"
      />
    </div>
    <div class="flex justify-center items-end grow">
      <NuxtLink to="type"><Button @click="check">다음</Button></NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
const store = useMakeRoomStore();
const changeInput = (event: any): void => {
  store.name = event.target.value;
};
const nameCheck = ref<boolean>(false);
const check = (e: any): void => {
  if (store.name === '') {
    nameCheck.value = true;
    e.preventDefault();
  }
};
</script>
<style scoped>
.box {
  animation: fade-in 0.5s ease-in-out;
}
@keyframes fade-in {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}
</style>
