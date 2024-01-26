<template>
  <div class="flex flex-col items-center">
    <div class="mb-6 text-center mx-0 my-auto">
      <h1 class="text-2xl font-bold">카카오 로그인</h1>

      <div>
        <a id="kakao-login-button" @click="handleKakaoLogin">
          <img
            class="pt-5 pl-10 pr-10"
            src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
            alt="카카오 로그인 버튼"
          />
        </a>
        <div>
          {{ route.query?.code }}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const route = useRoute();
const handleKakaoLogin = (): void => {
  // 카카오 로그인 URL로 리다이렉트
  const kakaoLoginURL = `https://kauth.kakao.com/oauth/authorize?client_id=d63ba3628c4ea2cff1e3eb9f0955f47f&redirect_uri=${process.env.OOMOOL_SITE_URL}/oauth/kakao&response_type=code`;
  window.location.href = kakaoLoginURL;
};
</script>
