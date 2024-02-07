export interface IGetMakeRoomResponse {
  data: {
    created_at: string;
    invite_code: string;
    master_id: string;
    setting: {
      title: string;
      start_date: string;
      end_date: string;
      question_type: string;
      max_member: number;
    };
  };
  status: string;
}

export interface IMakeRoomInput {
  setting: {
    title: string;
    start_date: string;
    end_date: string;
    question_type: string;
    max_member: number;
  };
  master: {
    id: number;
    email: string;
    username: string;
  };
}

export interface IJoinWaitRoomInput {
  user_id: number;
  user_email: string;
  player_nickname: string;
  player_background_color: string;
  player_avatar_url: string;
}

export interface IGetJoinWaitRoomResponse {
  data: {
    players: [
      {
        user_id: number;
        user_email: string;
        player_nickname: string;
        player_background_color: string;
        player_avatar_url: string;
      },
    ];
  };
  status: string;
}

export interface IGetWaitRoomResponse {
  data: {
    invite_code: string;
    master_id: number;
    setting: {
      title: string;
      start_date: string;
      end_date: string;
      question_type: string;
      max_member: number;
    };
    players: [
      {
        user_id: number;
        user_email: string;
        player_nickname: string;
        player_background_color: string;
        player_avatar_url: string;
      },
    ];
    ban_list: string;
    created_at: string;
  };
  status: string;
}

export interface IGetUpdateSettingResponse {
  data: {
    setting: {
      title: string;
      start_date: string;
      end_date: string;
      question_type: string;
      max_member: number;
    };
  };
  status: string;
}

export interface IUpdateSettingInput {
  title: string;
  start_date: string;
  end_date: string;
  question_type: string;
  max_member: number;
}
