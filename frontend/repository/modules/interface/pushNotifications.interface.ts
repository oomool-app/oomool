export interface ISaveTokenInput {
  user_id: number;
  token: string;
}

export interface ISaveTokenResponse {
  data: string;
  status: string;
}

export interface IRemoveFcmTokenInput {
  fcmToken: string;
}

export interface IRemoveFcmTokenResponse {
  data: string;
  status: string;
}
