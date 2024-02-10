import HttpFactory from '../factory';
import {
  type IMakeRoomInput,
  type IGetMakeRoomResponse,
  type IJoinWaitRoomInput,
  type IGetJoinWaitRoomResponse,
  type IGetWaitRoomResponse,
  type IGetUpdateSettingResponse,
  type IUpdateSettingInput,
  type IGetLongPollingResponse,
  type IGetDeleteWaitRoomResponse,
} from './interface/waitroom.interface';

class MakeModule extends HttpFactory {
  private readonly RESOURCE = '/temp';

  // pinia에 저장된 정보와 유저의 정보를 이용해서 대기방 생성
  async createWaitRoom(
    roomInfo: IMakeRoomInput,
  ): Promise<IGetMakeRoomResponse> {
    return await this.otherCall<IGetMakeRoomResponse>(
      'POST',
      `${this.RESOURCE}`,
      roomInfo,
    );
  }

  // 유저의 정보와 inviteCode를 이용하여 해당 대기방 입장
  async joinWaitRoom(
    userInfo: IJoinWaitRoomInput,
    inviteCode: string,
  ): Promise<IGetJoinWaitRoomResponse> {
    return await this.otherCall<IGetJoinWaitRoomResponse>(
      'POST',
      `${this.RESOURCE}/${inviteCode}/players`,
      userInfo,
    );
  }

  // 초대코드에 해당하는 대기방 정보 조회
  async getWaitRoom(inviteCode: string): Promise<IGetWaitRoomResponse> {
    return await this.getCall<IGetWaitRoomResponse>(
      'GET',
      `${this.RESOURCE}/${inviteCode}`,
    );
  }

  // 대기방의 정보 변경
  async updateRoomSetting(
    inviteCode: string,
    roomSetting: IUpdateSettingInput,
  ): Promise<IGetUpdateSettingResponse> {
    return await this.otherCall<IGetUpdateSettingResponse>(
      'PATCH',
      `${this.RESOURCE}/${inviteCode}/setting`,
      roomSetting,
    );
  }

  // Pulling
  async getStartCheck(inviteCode: string): Promise<IGetLongPollingResponse> {
    return await this.getCall<IGetLongPollingResponse>(
      'GET',
      `${this.RESOURCE}/${inviteCode}/check`,
    );
  }

  // 대기방 삭제
  async deleteWaitRoom(
    inviteCode: string,
    id: number,
  ): Promise<IGetDeleteWaitRoomResponse> {
    return await this.otherCall<IGetDeleteWaitRoomResponse>(
      'DELETE',
      `${this.RESOURCE}/${inviteCode}?id=${id}`,
      {},
    );
  }
}

export default MakeModule;
