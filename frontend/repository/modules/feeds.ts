import HttpFactory from '../factory';
import {
  type IGetAllFeedsByRoomUidAndSequenceInput,
  type IGetAllFeedsByRoomUidAndSequenceResponse,
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
}

export default FeedsModule;
