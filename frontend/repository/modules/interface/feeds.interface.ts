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

export default interface Feed {
  user_id: number;
  content: string;
  created_at: Date;
  feed_image_dto_list: Image[];
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
