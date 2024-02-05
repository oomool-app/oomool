import HttpFactory from '../factory';
import {
  type IGetAllQuestionsByRoomUidInput,
  type IGetAllQuestionsByRoomUidResponse,
  type IGetDailyQuestionInput,
  type IGetDailyQuestionResponse,
  type IRegistQuestionToRoomInput,
  type IRegistQuestionToRoomResponse,
} from './interface/question.interface';

class QuestionModule extends HttpFactory {
  private readonly RESOURCE = '/questions';

  // 데일리 질문 조회
  async getDailyQuestion(
    roomUid: IGetDailyQuestionInput,
  ): Promise<IGetDailyQuestionResponse> {
    return await this.getCall<IGetDailyQuestionResponse>(
      'GET',
      `${this.RESOURCE}/${roomUid.roomUid}/daily`,
    );
  }

  // 방 생성시 해당 방의 질문 등록
  async registQuestionToRoom(
    roomUid: IRegistQuestionToRoomInput,
  ): Promise<IRegistQuestionToRoomResponse> {
    return await this.otherCall<IRegistQuestionToRoomResponse>(
      'POST',
      `${this.RESOURCE}/${roomUid.roomUid}`,
      roomUid,
    );
  }

  // 문답방 전체 질문 조회
  async getAllQuestionsByRoomUid(
    roomUid: IGetAllQuestionsByRoomUidInput,
  ): Promise<IGetAllQuestionsByRoomUidResponse> {
    return await this.getCall<IGetAllQuestionsByRoomUidResponse>(
      'GET',
      `${this.RESOURCE}/${roomUid.roomUid}`,
    );
  }
}

export default QuestionModule;
