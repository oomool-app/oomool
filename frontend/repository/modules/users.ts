import HttpFactory from '../factory';
import {
  type IGetRoomListInput,
  type IGetRoomListResponse,
  type IGetTempRoomListInput,
  type IGetTempRoomListResponse,
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

  // 유저아이디로 대기방 목록 가져오기
  async getTempRoomList(
    userId: IGetTempRoomListInput,
  ): Promise<IGetTempRoomListResponse> {
    return await this.getCall<IGetTempRoomListResponse>(
      'GET',
      `${this.RESOURCE}/${userId.userId}/temps`,
    );
  }
}

export default UsersModule;
