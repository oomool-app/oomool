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

export default interface Feed {
  author_id: number;
  content: string;
  created_at: Date;
  feed_image_dto_list: string[];
  manitti_dto: {
    nickname: string;
    avatar_color: string;
    url: string;
  };
}
