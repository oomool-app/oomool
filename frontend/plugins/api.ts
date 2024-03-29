import { $fetch, type FetchOptions } from 'ohmyfetch';
import { defineNuxtPlugin } from '#app';
import QuestionModule from '../repository/modules/question';
import UsersModule from '~/repository/modules/users';
import RoomsModule from '~/repository/modules/rooms';
import PlayersModule from '~/repository/modules/players';
import PushNotificationModule from '~/repository/modules/pushNotifications';
import FeedsModule from '~/repository/modules/feeds';
import MakeModule from '~/repository/modules/waitroom';
import NotificationsModule from '~/repository/modules/notifications';

/** ApiInstance interface provides us with good typing */
interface IApiInstance {
  question: QuestionModule;
  users: UsersModule;
  rooms: RoomsModule;
  players: PlayersModule;
  feeds: FeedsModule;
  pushNotifications: PushNotificationModule;
  make: MakeModule;
  notifications: NotificationsModule;
}

export default defineNuxtPlugin((nuxtApp) => {
  const config = useRuntimeConfig();

  const fetchOptions: FetchOptions = {
    baseURL: config.public.oomoolApiUrl,
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
    make: new MakeModule(apiFetcher),
    notifications: new NotificationsModule(apiFetcher),
  };

  return {
    provide: {
      api: modules,
    },
  };
});
