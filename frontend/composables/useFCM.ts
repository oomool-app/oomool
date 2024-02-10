// composable/useFCM.ts
import { useNuxtApp } from '#app';
import { getToken, type Messaging } from 'firebase/messaging';

interface UseFCM {
  token: Ref<string>;
  fetchToken: () => Promise<string>;
}

const useFCM = (): UseFCM => {
  const config = useRuntimeConfig();
  const token = ref<string>('');
  const { $fcm }: { $fcm: Messaging } = useNuxtApp();

  const fetchToken = async (): Promise<string> => {
    const currentToken = await getToken($fcm, {
      vapidKey: config.public.vapidKey,
    });

    token.value = currentToken;

    return currentToken;
  };

  return { token, fetchToken };
};

export default useFCM;
