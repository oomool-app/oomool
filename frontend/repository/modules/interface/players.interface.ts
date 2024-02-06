export interface IGetMyManittiInput {
  roomUid: string;
  userId: number;
}

export interface IGetMyManittiResponse {
  data: {
    player: Player;
    manitti: Player;
  };
  status: string;
}

export interface IGetAllMemebersByRoomUidResponse {
  data: {
    players: Player[];
  };
  status: string;
}

export default interface Player {
  user_id: number;
  user_email: string;
  player_nickname: string;
  player_background_color: string;
  player_avatar_url: string;
}
