<template>
  <div>
    <Dialog>
      <DialogTrigger
        as-child
        class="bg-white text-black w-40 h-40 hover:bg-white"
      >
        <Button
          class="bg-white w-40 h-40 flex flex-col items-end relative shadow-lg"
        >
          <!-- items-end 클래스 추가 -->
          <div class="mb-auto w-20 h-auto drop-shadow-xl">
            <img src="/img/enterGhost.png" />
          </div>
          <div class="text-[#454545] absolute left-3 bottom-11">
            초대 코드가 있나요?
          </div>
          <div
            class="font-extrabold mt-auto absolute left-3.5 bottom-3 text-2xl text-primary"
          >
            입장하기
          </div>
          <svg
            xmlns="http://www.w3.org/2000/svg"
            width="24"
            height="24"
            viewBox="0 0 24 24"
            fill="none"
            class="absolute right-2 bottom-3.5"
          >
            <path
              d="M5 12H19"
              stroke="#61339B"
              stroke-width="3"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <path
              d="M12 5L19 12L12 19"
              stroke="#61339B"
              stroke-width="3"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </svg>
        </Button>
      </DialogTrigger>
      <DialogContent class="border-white h-60 flex flex-col justify-center">
        <DialogHeader>
          <DialogTitle class="pb-3">초대코드 입력하기</DialogTitle>
          <DialogDescription>
            <input
              id="inviteCodeInput"
              v-model="inviteCode"
              type="text"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="초대코드를 입력해주세요!"
            />
          </DialogDescription>
        </DialogHeader>
        <Button @click="inputCode"> 방 입장하기 </Button>
      </DialogContent>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
const router = useRouter();
const inviteCode = ref('');

// 방 입장하기 누르면 실행될 함수
const inputCode = async (): Promise<void> => {
  if (inviteCode.value === '') {
    alert('초대 코드를 입력해주세요.');
  } else {
    await router.push({ path: `waitroom/${inviteCode.value}/updateProfile` });
  }
};
</script>
