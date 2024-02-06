import HttpFactory from '../factory';
import { type IGetRoomDetailResponse } from './interface/rooms.interface';

class RoomsModule extends HttpFactory {
  private readonly RESOURCE = '/rooms';

  // 문답방 상세 조회
  async getRoomDetail(roomUid: string): Promise<IGetRoomDetailResponse> {
    return await this.getCall<IGetRoomDetailResponse>(
      'GET',
      `${this.RESOURCE}/${roomUid}`,
    );
  }
}

export default RoomsModule;
