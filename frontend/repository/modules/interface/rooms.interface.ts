import type Player from '../interface/players.interface';

export interface IGetRoomDetailResponse {
  data: {
    created_at: Date;
    players: Player[];
    room_uid: string;
    setting: {
      title: string;
      start_date: Date;
      end_date: Date;
      question_type: string;
      max_member: number;
    };
  };
  status: string;
}

export interface IGetRoomUidResponse {
  data: {
    roomUid: string;
  };
  status: string;
}

export interface ICreateRoomUidInput {
  invite_code: string;
}
