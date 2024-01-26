<template>
  <div> hi<!--로그인 코드 처리 빈 화면--></div>
</template>

<script setup lang="ts">
import { useRoute } from 'vue-router';

const handleKakaoCallback = async (): Promise<void> => {
  const route = useRoute();
  const code = route.query.code;
  console.log(code);

  if (code != null) {
    // 추출된 코드를 API로 전송
    const apiEndpoint = `http://dev.oomool.site/oauth/kakao?code=${code as string}`;

    const response = await fetch(apiEndpoint, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({ code }),
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
