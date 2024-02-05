import { type FetchOptions, type ServerResponse } from './interface/server';

export default class ApiService {
  private static async fetch<T>(
    url: string,
    fetchOptions: FetchOptions,
  ): Promise<ServerResponse<T>> {
    const optionsInit = {
      ...fetchOptions,
      initialCache: false,
      headers: { ...fetchOptions.headers },
    };

    const getToken = useCookie('token');

    if (getToken.value != null) {
      optionsInit.headers = {
        ...optionsInit.headers,
        Authorization: `Bearer ${getToken.value}`,
      };
    }

    return await new Promise((resolve, reject) => {
      useFetch(url, {
        onRequest({ options }) {
          Object.assign(options, optionsInit);
        },
        onRequestError({ error }) {
          console.log('error', error);
          reject(error);
        },
        async onResponse({ request, response, options }) {
          const { status, _data } = response;

          if (status === 200 || status === 201) {
            console.log('response', status);
            resolve(_data as ServerResponse<T>);
          }
        },
        onResponseError({ response }) {
          // eslint-disable-next-line @typescript-eslint/naming-convention
          const { status, _data } = response;

          console.log('responseError. status: ', status);
          reject(_data);
        },
        watch: fetchOptions.watch,
      }).catch((error) => {
        console.log('error: ', error);
        reject(error);
      });
    });
  }

  // GET 메서드
  protected async GET<T>(
    url: string,
    params?: any,
    watchData?: any,
  ): Promise<ServerResponse<T>> {
    const data = await ApiService.fetch<T>(url, {
      method: 'GET',
      params,
      watch: [watchData],
    });

    return data;
  }

  // POST 메서드
  protected async POST<T>(
    url: string,
    body?: any,
    watchData?: any,
  ): Promise<ServerResponse<T>> {
    const headers: HeadersInit = {
      'Content-Type': 'application/json',
    };
    const data = await ApiService.fetch<T>(url, {
      method: 'POST',
      headers,
      body,
      watch: [watchData],
    });

    return data;
  }

  // PUT 메서드
  protected async PUT<T>(
    url: string,
    body?: any,
    watchData?: any,
  ): Promise<ServerResponse<T>> {
    const headers: HeadersInit = {
      'Content-Type': 'application/json',
    };
    const data = await ApiService.fetch<T>(url, {
      method: 'PUT',
      headers,
      body,
      watch: [watchData],
    });

    return data;
  }

  // PATCH 메서드
  protected async PATCH<T>(
    url: string,
    body?: any,
    watchData?: any,
  ): Promise<ServerResponse<T>> {
    const headers: HeadersInit = {
      'Content-Type': 'application/json',
    };
    const data = await ApiService.fetch<T>(url, {
      method: 'PATCH',
      headers,
      body,
      watch: [watchData],
    });

    return data;
  }

  // DELETE 메서드
  protected async DELETE<T>(
    url: string,
    body?: any,
  ): Promise<ServerResponse<T>> {
    const headers: HeadersInit = {
      'Content-Type': 'application/json',
    };
    const data = await ApiService.fetch<T>(url, {
      method: 'DELETE',
      headers,
      body,
    });

    return data;
  }

  // FormData
  protected async PostFormData<T>(
    url: string,
    formData: FormData,
  ): Promise<ServerResponse<T>> {
    const headers: HeadersInit = {
      'Content-Type': 'multipart/form-data',
    };
    const data = await ApiService.fetch<T>(url, {
      method: 'POST',
      headers,
      body: formData,
    });

    return data;
  }
}
