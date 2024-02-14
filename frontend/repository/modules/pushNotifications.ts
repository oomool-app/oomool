import HttpFactory from '../factory';
import {
  type ISaveTokenInput,
  type ISaveTokenResponse,
  type IRemoveFcmTokenInput,
  type IRemoveFcmTokenResponse,
} from './interface/pushNotifications.interface';

class PushNotificationModule extends HttpFactory {
  private readonly RESOURCE = '/push-notifications';

  async saveToken(tokens: ISaveTokenInput): Promise<ISaveTokenResponse> {
    return await this.otherCall<ISaveTokenResponse>(
      'POST',
      `${this.RESOURCE}/token`,
      tokens,
    );
  }

  async removeFcmToken(
    token: IRemoveFcmTokenInput,
  ): Promise<IRemoveFcmTokenResponse> {
    return await this.otherCall<IRemoveFcmTokenResponse>(
      'DELETE',
      `${this.RESOURCE}/token/${token.fcmToken}`,
      {},
    );
  }
}

export default PushNotificationModule;
