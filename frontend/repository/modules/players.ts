import HttpFactory from '../factory';
import {
  type IGetMyManittiInput,
  type IGetMyManittiResponse,
  type IGetAllMemebersByRoomUidResponse,
} from './interface/players.interface';

class PlayersModule extends HttpFactory {
  private readonly RESOURCE = '/players';

  // 내 마니띠 조회
  async getMyManitti(data: IGetMyManittiInput): Promise<IGetMyManittiResponse> {
    return await this.getCall<IGetMyManittiResponse>(
      'GET',
      `${this.RESOURCE}/${data.roomUid}/${data.userId}`,
    );
  }

  // 문답방내 전체 플레이어 조회
  async getAllMembersByRoomUid(
    roomUid: string,
  ): Promise<IGetAllMemebersByRoomUidResponse> {
    return await this.getCall<IGetAllMemebersByRoomUidResponse>(
      'GET',
      `${this.RESOURCE}/${roomUid}`,
    );
  }
}

export default PlayersModule;
