import { $fetch, type FetchOptions } from 'ohmyfetch';
import { defineNuxtPlugin } from '#app';
import QuestionModule from '../repository/modules/question';
import UsersModule from '~/repository/modules/users';
import PushNotificationModule from '~/repository/modules/pushNotifications';

/** ApiInstance interface provides us with good typing */
interface IApiInstance {
  question: QuestionModule;
  users: UsersModule;
  pushNotifications: PushNotificationModule;
}

export default defineNuxtPlugin((nuxtApp) => {
  const fetchOptions: FetchOptions = {
    baseURL: 'https://api-dev.oomool.site/',
    // process.env.NUXT_PUBLIC_OOMOOL_API_URL,
    // nuxtApp.config.base_url
    // https://api-dev.oomool.site/
  };

  /** create a new instance of $fetcher with custom option */
  const apiFetcher = $fetch.create(fetchOptions);

  /** an object containing all repositories we need to expose */
  const modules: IApiInstance = {
    question: new QuestionModule(apiFetcher),
    users: new UsersModule(apiFetcher),
    pushNotifications: new PushNotificationModule(apiFetcher),
  };

  return {
    provide: {
      api: modules,
    },
  };
});
