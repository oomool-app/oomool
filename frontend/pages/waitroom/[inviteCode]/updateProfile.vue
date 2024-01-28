<template>
  <div class="flex flex-col items-center bg-background justify-center ">
    <div class="mb-6 text-center">
      <h1 class="text-2xl pt-5 font-bold">프로필 설정하기</h1>

      <div class="flex pb-10 pt-10 justify-center items-start gap-2.5">
        <div class="relative flex h-32 w-32 px-0 py-2.5 justify-center rounded-full border-solid border-2 border-[#6D6D6D]" :style="{ backgroundColor: randomColor }">
          <img src="/assets/images/정답 유령.png" alt="프로필 이미지" />
          <RandomButton class="absolute right-0 bottom-0" @click="changeRandomButtonColor"></RandomButton>
        </div>
      </div>

      <div class="pl-5">
        <h1 class="pt-15 pb-1 text-xl font-bold flex justify-start">
          사용자 이름을 입력해주세요
        </h1>
        <h1 class="text-sm pb-8 font-bold text-gray-500 flex justify-start">
          다른 친구들이 구분할 수 있는 이름을 사용해주세요.
        </h1>
        <input
          id="username"
          type="text"
          class="flex bg-[#F9FAFB] rounded-md border-2 border-solid border-[#6D6D6D] items-center w-80 h-16 justify-center"
          placeholder="userkakaonickname"
          required
        />
        <h1 class="pt-1 pb-5 text-xs font-bold flex justify-start text-primary">
          그룹 내 멤버들이 식별 가능한 닉네임을 입력해주세요
        </h1>
      </div>
    </div>

    <Button class="mx-auto" @click="back">프로필 수정하기</Button>
  </div>
</template>

<script setup lang="ts">
const back = (): void => {
  window.history.go(-1);
};

const colors: string[] = [
  "#FF5733", "#33FF57", "#5733FF", "#FF33A1", "#33A1FF",
  "#FFD700", "#4CAF50", "#2196F3", "#FFC107", "#795548",
  "#E91E63", "#FF9800", "#9C27B0", "#00BCD4", "#607D8B"
];

const randomColor = ref(colors[0]);

const changeRandomButtonColor = (): void => {
  randomColor.value = getRandomColor();
};

function getRandomColor(): string {
  const randomIndex: number = Math.floor(Math.random() * colors.length);
  return colors[randomIndex];
}


</script>
