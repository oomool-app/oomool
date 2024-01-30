<template>
  <div class="flex flex-col items-center bg-primary ">
    <div class="pt-36 text-center">
      <!--기본 유령 이미지-->
      <img
        class = "pl-32 pr-32"
        src="/img/기본유령.png" />
      <h1 class="text-white text-xl pt-5">우리들의 물음표</h1>
      <h1 class="text-white text-5xl pb-5">우물</h1>
    </div>
    <!--카카오로그인 버튼-->
    <div id="kakao-login-button" @click="handleKakaoLogin" class="bg-primary pt-5 pl-10 pr-10 pb-56">
        <img
          src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
          alt="카카오 로그인 버튼"
        />
    </div>
  </div>
</template>

<script setup lang="ts">
  const config = useRuntimeConfig();

  const handleKakaoLogin = (): void => {
    // 카카오 로그인 URL로 리다이렉트
    const kakaoLoginURL = `https://kauth.kakao.com/oauth/authorize?client_id=d63ba3628c4ea2cff1e3eb9f0955f47f&redirect_uri=${config.public.oomoolSiteUrl}/oauth/kakao&response_type=code`;
    window.location.href = kakaoLoginURL;
  };
</script>
