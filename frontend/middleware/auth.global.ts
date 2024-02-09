export default defineNuxtRouteMiddleware(async (to, from) => {
  const accessToken = useCookie('accessToken');

  if (
    accessToken.value == null &&
    to.path !== '/login' &&
    !to.path.startsWith('/oauth/')
  ) {
    return await navigateTo('/login');
  }
});
