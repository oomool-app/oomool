<template>
  <div class="box grid grid-rows-[6rem,2rem,4.5rem,5rem] h-screen p-6">
    <div class="grid grid-cols-3">
      <div class="flex items-center">
        <BackButton color="black"></BackButton>
      </div>
      <div class="flex items-center justify-center text-2xl font-extrabold">
        방 만들기
      </div>
    </div>
    <div>
      <MakeRoomProgress :cur-page="'1'"></MakeRoomProgress>
    </div>
    <div class="font-bold text-xl pt-2">우리 방의 이름을 정해주세요!</div>
    <div>
      <input
        id="userName"
        :value="store.name"
        name="userName"
        type="text"
        class="w-full border border-purple-800 rounded-lg h-10 p-3"
        placeholder="방의 이름을 정해주세요!"
        autocomplete="off"
        @input="changeInput"
      />
      <div id="check" class="text-red-600 text-sm mt-2"></div>
    </div>
    <div class="flex justify-center items-end pb-6">
      <NuxtLink class="w-full rounded-full" to="type" replace>
        <Button class="w-full rounded-full text-lg" @click="check">다음</Button>
      </NuxtLink>
    </div>
  </div>
</template>

<script setup lang="ts">
const store = useMakeRoomStore();
const changeInput = (event: any): void => {
  store.name = event.target.value;
};
const check = (e: any): void => {
  const element = document.getElementById('check');
  if (element == null) {
    return;
  }
  element.innerText = '';
  if (store.name.trim() === '' || store.name.trim().length >= 10) {
    element.innerText = '방의 이름을 10자이내로 입력해주세요!';
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
