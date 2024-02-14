<template>
  <div></div>
</template>

<script setup lang="ts">
const nowPageKey = ref<string>('');
const route = useRoute();
const userStore = useUserStore();
const router = useRouter();
const token = useCookie('accessToken', {
  maxAge: 60 * 30,
});

onBeforeMount(async () => {
  token.value = route.query.token as string;

  userStore.setUser({
    id: +(route.query.userId as string),
    email: route.query.email as string,
    name: route.query.username as string,
  });

  const previousPage = sessionStorage.getItem(nowPageKey.value);
  if (previousPage !== null) {
    await router.push(previousPage);
    sessionStorage.removeItem(nowPageKey.value);
  } else {
    await router.push('/');
  }
});

onErrorCaptured((error) => {
  console.error('에러가 발생했습니다:', error);
  // 에러 처리 로직 추가
});
</script>
