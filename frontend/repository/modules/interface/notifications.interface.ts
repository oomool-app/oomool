export interface IGetAllNotificationsInput {
  userId: number;
}

export default interface NotificationMessage {
  room_uid: string;
  type: string;
  title: string;
  body: string;
  created_at: string;
}

export interface IGetAllNotificationsResponse {
  data: {
    unread?: NotificationMessage[];
    read?: NotificationMessage[];
  };
}

export interface ICheckUnreadNotificationInput {
  userId: number;
}

export interface ICheckUnreadNotificationResponse {
  data: boolean;
}
