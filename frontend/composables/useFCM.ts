// composable/useFCM.ts
import { useNuxtApp } from '#app';
import { getToken, type Messaging } from 'firebase/messaging';

interface UseFCM {
  token: Ref<string | null>;
  fetchToken: () => Promise<void>;
}

const useFCM = (): UseFCM => {
  const token = ref<string | null>(null);
  const { $fcm }: { $fcm: Messaging } = useNuxtApp();

  const fetchToken = async (): Promise<void> => {
    await getToken($fcm)
      .then((currentToken) => {
        token.value = currentToken;
      })
      .catch((err) => {
        console.error('An error occurred while retrieving token. ', err);
      });
  };

  onMounted(fetchToken);

  return { token, fetchToken };
};

export default useFCM;
