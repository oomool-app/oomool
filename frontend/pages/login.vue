<template>
  <div class="flex flex-col h-screen items-center justify-center bg-primary">
    <div class="pl-32 pr-32">
      <!--기본 유령 이미지-->
      <img src="/img/defaultGhost1.png" />
      <h1 class="text-white pl-3 pt-6">우리들의 물음표</h1>
      <img src="/img/logo.png" />
    </div>
    <!--카카오로그인 버튼-->
    <a
      id="kakao-login-button"
      class="bg-primary pt-10"
      :href="$config.public.oomoolApiUrl + '/oauth2/authorization/kakao'"
    >
      <img
        src="/img/kakao_login.png"
        alt="카카오 로그인 버튼"
        class="w-56 h-auto"
      />
    </a>
  </div>
</template>
