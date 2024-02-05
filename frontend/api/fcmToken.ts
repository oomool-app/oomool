import ApiService from './ApiService';
import type { ServerResponse } from './interface/server';

interface TokenData {
  user_id: number;
  token: string;
}

interface UserData {
  user_id: number;
}

interface UserTokens {
  tokens: TokenData[];
}

export const FETCH_FCM_TOKEN = new (class extends ApiService {
  getBaseUrl(): string {
    return useRuntimeConfig().public.oomoolApiUrl;
  }

  async saveToken(data: TokenData): Promise<ServerResponse<string>> {
    const res = await this.POST<string>(
      `${this.getBaseUrl()}/push-notifications/token`,
      data,
    );

    return res;
  }

  async deleteToken(data: TokenData): Promise<ServerResponse<string>> {
    const res = await this.DELETE<string>(
      `${this.getBaseUrl()}/push-notifications/token`,
      data,
    );

    return res;
  }

  async getTokensByUserId(data: UserData): Promise<ServerResponse<UserTokens>> {
    const res = await this.GET<UserTokens>(
      `${this.getBaseUrl()}/push-notifications/token/${data.user_id}`,
    );

    return res;
  }
})();
