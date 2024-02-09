<template>
  <div class="flex flex-col h-screen items-center justify-center bg-primary">
    <div class="pl-32 pr-32">
      <!--기본 유령 이미지-->
      <img src="/img/defaultGhost1.png" />
      <h1 class="text-white pl-3 pt-3">우리들의 물음표</h1>
      <img src="/img/logo.png" />
    </div>
    <!--카카오로그인 버튼-->
    <a
      id="kakao-login-button"
      class="bg-primary pt-16 pl-10 pr-10"
      :href="$config.public.oomoolApiUrl + '/oauth2/authorization/kakao'"
    >
      <img
        src="//k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
        alt="카카오 로그인 버튼"
      />
    </a>
    <fieldset
      class="form-group grid grid-cols-3 flex-wrap justify-center gap-4 pt-5"
    >
      <button
        class="bg-white p-3 rounded-xl text-black font-bold"
        @click="handleLogin('kimsungsu@naver.com')"
      >
        로그인1
      </button>
      <button
        class="bg-white p-3 rounded-xl text-black font-bold"
        @click="handleLogin('jeongpilmo@naver.com')"
      >
        로그인2
      </button>
      <button
        class="bg-white p-3 rounded-xl text-black font-bold"
        @click="handleLogin('parksejeong@naver.com ')"
      >
        로그인3
      </button>
      <button
        class="bg-white p-3 rounded-xl text-black font-bold"
        @click="handleLogin('kimhyunji@naver.com')"
      >
        로그인4
      </button>
      <button
        class="bg-white p-3 rounded-xl text-black font-bold"
        @click="handleLogin('jeoneunpyeong@naver.com')"
      >
        로그인5
      </button>
      <button
        class="bg-white p-3 rounded-xl text-black font-bold"
        @click="handleLogin('kimbyeonghyung@naver.com')"
      >
        로그인6
      </button>
    </fieldset>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { useUserStore } from '~/stores/userStore';
const userStore = useUserStore();
const router = useRouter();
const config = useRuntimeConfig();

const handleLogin = async (email: string): Promise<void> => {
  console.log(email);
  const apiEndpoint = `${config.public.oomoolApiUrl}/users/login?email=${email}`;

  const response = await fetch(apiEndpoint, {
    method: 'GET',
  });

  if (response.ok) {
    const responseData = await response.json();
    console.log('API 응답:', responseData);

    userStore.setUser({
      id: responseData.id,
      email: responseData.email,
      name: responseData.username,
    });
    await router.push('/');
  } else {
    console.error('API 요청 실패');
  }
};
</script>
