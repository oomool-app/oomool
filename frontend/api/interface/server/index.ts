import type { MultiWatchSources } from '@vueuse/core';

export interface FetchOptions {
  method: 'GET' | 'POST' | 'PUT' | 'DELETE' | 'PATCH';
  body?: object;
  params?: object;
  query?: object;
  headers?: HeadersInit;
  watch?: MultiWatchSources;
}
export interface ServerResponse<T> {
  status: string;
  data: T;
}
