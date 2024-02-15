import HttpFactory from '../factory';
import {
  type IGetMyManittiInput,
  type IGetMyManittiResponse,
  type IGetAllMemebersByRoomUidResponse,
  type IGetMyManittoInput,
  type IGetMyManittoResponse,
  type ISaveResultExpectedManittoInput,
  type ISaveResultExpectedManittoResponse,
} from './interface/players.interface';

class PlayersModule extends HttpFactory {
  private readonly RESOURCE = '/players';

  // 내 마니띠 조회
  async getMyManitti(data: IGetMyManittiInput): Promise<IGetMyManittiResponse> {
    return await this.getCall<IGetMyManittiResponse>(
      'GET',
      `${this.RESOURCE}/${data.roomUid}/${data.userId}/manitti`,
    );
  }

  // 내 마니또 조회
  async getMyManitto(data: IGetMyManittoInput): Promise<IGetMyManittoResponse> {
    return await this.getCall<IGetMyManittoResponse>(
      'GET',
      `${this.RESOURCE}/${data.roomUid}/${data.userId}/manitto`,
    );
  }

  // 마니또 예측 결과 저장
  async saveResultExpectedManitto(
    roomUid: string,
    userId: number,
    req: ISaveResultExpectedManittoInput,
  ): Promise<ISaveResultExpectedManittoResponse> {
    return await this.otherCall<ISaveResultExpectedManittoResponse>(
      'POST',
      `${this.RESOURCE}/${roomUid}/${userId}/guess`,
      req,
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
