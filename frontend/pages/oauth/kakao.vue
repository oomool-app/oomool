<template>
  <div></div>
</template>

<script setup lang="ts">
const route = useRoute();
const userStore = useUserStore();
const router = useRouter();
const token = useCookie('accessToken');

onBeforeMount(async () => {
  token.value = route.query.token as string;

  userStore.setUser({
    id: +(route.query.userId as string),
    email: route.query.email as string,
    name: route.query.username as string,
  });

  await router.push('/');
});

onErrorCaptured((error) => {
  console.error('에러가 발생했습니다:', error);
  // 에러 처리 로직 추가
});
</script>
