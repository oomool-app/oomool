export interface IGetDailyQuestionInput {
  roomUid: string;
}

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
  roomUid: string;
}

export interface IRegistQuestionToRoomResponse {
  data: string;
  status: string;
}

export interface IGetAllQuestionsByRoomUidInput {
  roomUid: string;
}

export interface IGetAllQuestionsByRoomUidResponse {
  data: {
    roomUid: string;
    room_question_list: RoomQuestionList[];
    status: string;
  };
}

interface RoomQuestionList {
  daily_date: Date;
  question: string;
  sequence: number;
  level: number;
}
