import HttpFactory from '../factory';
import {
  type IGetRoomDetailResponse,
  type IGetRoomUidResponse,
  type ICreateRoomUidInput,
} from './interface/rooms.interface';
class RoomsModule extends HttpFactory {
  private readonly RESOURCE = '/rooms';
  // 문답방 상세 조회
  async getRoomDetail(roomUid: string): Promise<IGetRoomDetailResponse> {
    return await this.getCall<IGetRoomDetailResponse>(
      'GET',
      `${this.RESOURCE}/${roomUid}`,
    );
  }

  // 문답방 생성
  async createRoom(
    inviteCode: ICreateRoomUidInput,
  ): Promise<IGetRoomUidResponse> {
    return await this.otherCall<IGetRoomUidResponse>(
      'POST',
      `${this.RESOURCE}`,
      inviteCode,
    );
  }
}

export default RoomsModule;
