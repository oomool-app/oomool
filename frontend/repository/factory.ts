import { type $Fetch } from 'ohmyfetch';

class HttpFactory {
  private readonly $fetch: $Fetch;

  constructor(fetcher: $Fetch) {
    this.$fetch = fetcher;
  }

  /**
   * method - GET, POST, PUT
   * URL
   **/
  async getCall<T>(method: string, url: string, extras = {}): Promise<T> {
    const $res: T = await this.$fetch(url, { method, ...extras });
    return $res;
  }

  async otherCall<T>(
    method: string,
    url: string,
    data: FormData | object,
    extras = {},
  ): Promise<T> {
    const $res: T = await this.$fetch(url, {
      method,
      body: data,
      ...extras,
    });
    return $res;
  }
}

export default HttpFactory;
