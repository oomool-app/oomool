import { $fetch, type FetchOptions } from 'ohmyfetch';
import { defineNuxtPlugin } from '#app';
import QuestionModule from '../repository/modules/question';

/** ApiInstance interface provides us with good typing */
interface IApiInstance {
  question: QuestionModule;
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
  };

  return {
    provide: {
      api: modules,
    },
  };
});
