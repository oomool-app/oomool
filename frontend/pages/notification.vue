<template>
  <div class="mb-6 bg-background">
    <div
      class="mb-6 text-center flex flex-col justify-center pt-24 pb-4 font-bold"
    >
      <img src="/img/logo2.png" class="px-28 pb-10" />
      <h1 class="text-2xl font-bold text-primary">우물 이용을 위해</h1>
      <h1 class="text-2xl font-bold text-primary">
        이용 권한을 허용해 주세요.
      </h1>
    </div>

    <h1 class="text-lg pb-5 pl-12">선택 접근 권한</h1>
    <div class="pl-8 pb-10 flex flex-row">
      <div class="self-center pl-3 pr-3">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="28"
          height="28"
          viewBox="0 0 28 28"
          fill="none"
        >
          <path
            d="M21 9.33337C21 7.47686 20.2625 5.69638 18.9497 4.38363C17.637 3.07087 15.8565 2.33337 14 2.33337C12.1435 2.33337 10.363 3.07087 9.05025 4.38363C7.7375 5.69638 7 7.47686 7 9.33337C7 17.5 3.5 19.8334 3.5 19.8334H24.5C24.5 19.8334 21 17.5 21 9.33337Z"
            stroke="black"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M16.0181 24.5C15.813 24.8536 15.5186 25.1471 15.1644 25.3511C14.8102 25.5551 14.4086 25.6625 13.9998 25.6625C13.591 25.6625 13.1894 25.5551 12.8352 25.3511C12.481 25.1471 12.1866 24.8536 11.9814 24.5"
            stroke="black"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </div>
      <div class="col-span-6">
        <div class="text-lg font-semibold">알림</div>
        <div class="mb-1 text-gray-500 text-sm w-64 dark:text-gray-400">
          새로운 질문이 생성되고, 내 마니또가 나에 대한 답변을 작성했을 때
          알림을 전송해요!
        </div>
      </div>
    </div>

    <div class="flex justify-center">
      <Button class="px-10" @click="requestNotificationPermission">확인</Button>
    </div>
  </div>
</template>

<script setup lang="ts">
const router = useRouter();
const requestNotificationPermission = async (): Promise<void> => {
  Notification.requestPermission()
    .then(async (permission) => {
      if (permission === 'granted') {
        // 메인페이지로 이동
        await router.push('/');
        return;
      }
      // 메인페이지로 이동
      await router.push('/');
    })
    .catch((error) => {
      console.error(
        'Error occurred while asking for notification permission:',
        error,
      );
    });
};
</script>
