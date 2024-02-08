import HttpFactory from '../factory';
import {
  type IGetAllFeedsByRoomUidAndSequenceInput,
  type IGetAllFeedsByRoomUidAndSequenceResponse,
  type IWriteFeedAnswerResponse,
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
}

export default FeedsModule;
