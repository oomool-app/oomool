export interface IGetDailyQuestionResponse {
  data: {
    daily_date: Date;
    question: string;
    sequence: number;
    level: number;
  };
  status: string;
}

export interface IRegistQuestionToRoomInput {
  room_uid: string;
}

export interface IRegistQuestionToRoomResponse {
  data: string;
  status: string;
}

export interface IGetAllQuestionsByRoomUidResponse {
  data: {
    roomUid: string;
    room_question_list: RoomQuestionList[];
    status: string;
  };
}

export interface RoomQuestionList {
  daily_date: Date;
  question: string;
  sequence: number;
  level: number;
}
