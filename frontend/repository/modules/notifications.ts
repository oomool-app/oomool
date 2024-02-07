import HttpFactory from '../factory';
import {
  type IGetAllNotificationsInput,
  type IGetAllNotificationsResponse,
  type ICheckUnreadNotificationInput,
  type ICheckUnreadNotificationResponse,
} from './interface/notifications.interface';

class NotificationsModule extends HttpFactory {
  private readonly RESOURCE = '/notifications';

  async getAllNotifications(
    userId: IGetAllNotificationsInput,
  ): Promise<IGetAllNotificationsResponse> {
    return await this.getCall<IGetAllNotificationsResponse>(
      'GET',
      `${this.RESOURCE}/${userId.userId}`,
    );
  }

  async checkUnreadNotifications(
    userId: ICheckUnreadNotificationInput,
  ): Promise<ICheckUnreadNotificationResponse> {
    return await this.getCall<ICheckUnreadNotificationResponse>(
      'GET',
      `${this.RESOURCE}/${userId.userId}/new`,
    );
  }
}

export default NotificationsModule;
