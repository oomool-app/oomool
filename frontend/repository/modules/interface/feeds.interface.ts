export interface IGetAllFeedsByRoomUidAndSequenceInput {
  roomUid: string;
  sequence: number;
}

export interface IGetAllFeedsByRoomUidAndSequenceResponse {
  data: {
    room_question_id: number;
    date: Date;
    room_feed_dto_list: Feed[];
    question_dto: {
      question: string;
      level: number;
    };
  };
  status: string;
}

export interface IWriteFeedAnswerResponse {
  data: {
    feed_id: number;
    content: string;
    feed_image_dto_list: Image[];
  };
  status: string;
}

export interface IModifyFeedAnswerResponse {
  data: string;
  status: string;
}

export interface IGetAllMyManittoFeedAnswersInput {
  roomUid: string;
  userId: number;
}

export interface IIGetAllMyManittoFeedAnswersResponse {
  data: {
    manitto_dto: {
      nickname: string;
      avatar_color: string;
      url: string;
    };
    result_manitto_list: AllFeeds[];
  };
  status: string;
}

export interface AllFeeds {
  room_question_id: number;
  user_id: number;
  content?: string;
  feed_id: number;
  created_at?: Date;
  feed_image_dto_list?: Image[];
  question_dto: {
    question: string;
    level: number;
  };
}
export default interface Feed {
  user_id: number;
  feed_id: number;
  content: string;
  created_at: Date;
  feed_image_dto_list?: Image[];
  manitti_dto: {
    nickname: string;
    avatar_color: string;
    url: string;
  };
}

interface Image {
  original_name: string;
  file_name: string;
  folder_name: string;
  url: string;
}
