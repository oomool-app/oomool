import HttpFactory from '../factory';
import {
  type IGetRoomListInput,
  type IGetRoomListResponse,
} from './interface/users.interface';

class UsersModule extends HttpFactory {
  private readonly RESOURCE = '/users';

  // 유저아이디로 방 목록 가져오기
  async getRoomList(userId: IGetRoomListInput): Promise<IGetRoomListResponse> {
    return await this.getCall<IGetRoomListResponse>(
      'GET',
      `${this.RESOURCE}/${userId.userId}/games`,
    );
  }
}

export default UsersModule;
