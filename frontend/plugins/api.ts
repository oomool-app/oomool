import { $fetch, type FetchOptions } from 'ohmyfetch';
import { defineNuxtPlugin } from '#app';
import QuestionModule from '../repository/modules/question';
import UsersModule from '~/repository/modules/users';
import RoomsModule from '~/repository/modules/rooms';
import PlayersModule from '~/repository/modules/players';
import PushNotificationModule from '~/repository/modules/pushNotifications';
import FeedsModule from '~/repository/modules/feeds';


/** ApiInstance interface provides us with good typing */
interface IApiInstance {
  question: QuestionModule;
  users: UsersModule;
  rooms: RoomsModule;
  players: PlayersModule;
  feeds: FeedsModule;
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
    players: new PlayersModule(apiFetcher),
    rooms: new RoomsModule(apiFetcher),
    pushNotifications: new PushNotificationModule(apiFetcher),
    feeds: new FeedsModule(apiFetcher),
  };

  return {
    provide: {
      api: modules,
    },
  };
});
