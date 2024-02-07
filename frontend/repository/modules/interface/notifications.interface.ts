export interface IGetAllNotificationsInput {
  userId: number;
}

export interface IGetAllNotificationsResponse {
  data: {
    unread: {
      room_uid: string;
      type: string;
      title: string;
      body: string;
      created_at: string;
    };
    read: {
      room_uid: string;
      type: string;
      title: string;
      body: string;
      created_at: string;
    };
  };
}

export interface ICheckUnreadNotificationInput {
  userId: number;
}

export interface ICheckUnreadNotificationResponse {
  data: boolean;
}
