<template>
  <div>hi<!--로그인 코드 처리 빈 화면--></div>
</template>

<script setup lang="ts">
const route = useRoute();
const config = useRuntimeConfig();

const handleKakaoCallback = async (): Promise<void> => {
  const code = route.query.code;

  if (code != null) {
    // 추출된 코드를 API로 전송
    const apiEndpoint = `${config.public.oomoolApiUrl}/oauth/kakao?code=${code as string}`;

    const response = await fetch(apiEndpoint, {
      method: 'GET',
    });

    if (response.ok) {
      const responseData = await response.json();
      console.log('API 응답:', responseData);
    } else {
      console.error('API 요청 실패');
    }
  } else {
    console.error('코드가 없습니다.');
  }
};

onMounted(async () => {
  await handleKakaoCallback();
});

onErrorCaptured((error) => {
  console.error('에러가 발생했습니다:', error);
  // 에러 처리 로직 추가
});
</script>
