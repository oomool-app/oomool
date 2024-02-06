import HttpFactory from '../factory';
import {
  type ISaveTokenInput,
  type ISaveTokenResponse,
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
}

export default PushNotificationModule;
