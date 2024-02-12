import HttpFactory from '../factory';
import {
  type IGetAllFeedsByRoomUidAndSequenceInput,
  type IGetAllFeedsByRoomUidAndSequenceResponse,
  type IWriteFeedAnswerResponse,
  type IModifyFeedAnswerResponse,
  type IGetAllMyManittoFeedAnswersInput,
  type IIGetAllMyManittoFeedAnswersResponse,
} from './interface/feeds.interface';

class FeedsModule extends HttpFactory {
  private readonly RESOURCE = '/feeds';

  // 문답방 모든 피드 조회
  async getAllFeedsByRoomUidAndSequence(
    data: IGetAllFeedsByRoomUidAndSequenceInput,
  ): Promise<IGetAllFeedsByRoomUidAndSequenceResponse> {
    return await this.getCall<IGetAllFeedsByRoomUidAndSequenceResponse>(
      'GET',
      `${this.RESOURCE}/${data.roomUid}/${data.sequence}`,
    );
  }

  // 피드 답변 등록
  async writeFeedAnswer(data: FormData): Promise<IWriteFeedAnswerResponse> {
    return await this.otherCall<IWriteFeedAnswerResponse>(
      'POST',
      `${this.RESOURCE}/daily`,
      data,
    );
  }

  // 피드 답변 수정
  async modifyFeedAnswer(data: FormData): Promise<IModifyFeedAnswerResponse> {
    return await this.otherCall<IModifyFeedAnswerResponse>(
      'PATCH',
      `${this.RESOURCE}/daily`,
      data,
    );
  }

  // 내 마니또가 작성한 피드 전체 조회
  async getAllMyManittoFeedAnswers(
    data: IGetAllMyManittoFeedAnswersInput,
  ): Promise<IIGetAllMyManittoFeedAnswersResponse> {
    return await this.getCall<IIGetAllMyManittoFeedAnswersResponse>(
      'GET',
      `${this.RESOURCE}/${data.roomUid}/${data.userId}/result`,
    );
  }
}

export default FeedsModule;
