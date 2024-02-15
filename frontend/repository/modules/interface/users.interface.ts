export interface IGetRoomListInput {
  userId: number;
}

export interface IGetRoomListResponse {
  data: {
    room_uid: string;
    title: string;
    start_date: string;
    end_date: string;
    sequence: number;
    question: string;
    daily_question: {
      daily_date: string;
      question: string;
      sequence: number;
      level: number;
    };
    setting: {
      title: string;
      start_date: string;
      end_date: string;
      question_type: string;
      max_member: number;
    };
  };
}

export interface IGetTempRoomListInput {
  userId: number;
}

export interface IGetTempRoomListResponse {
  data: {
    temp_room: {
      invite_code: string;
      setting: {
        title: string;
        start_date: string;
        end_date: string;
        question_type: string;
        max_member: number;
      };
      master_id: number;
      created_at: string;
    };
  };
}

interface RoomList {
  room_uid: string;
  title: string;
  start_date: string;
  end_date: string;
  sequence: number;
  question: string;
  daily_question: {
    daily_date: string;
    question: string;
    sequence: number;
    level: number;
  };
  setting: {
    title: string;
    start_date: string;
    end_date: string;
    question_type: string;
    max_member: number;
  };
}
