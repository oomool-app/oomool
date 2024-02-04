import {
  type CommonStringResponse,
  type FirebaseTokenResponse,
} from '@/types/response';

interface FirebaseTokenRepository {
  saveToken: (userId: number, token: string) => Promise<CommonStringResponse>;
  getTokensByUserId: (userId: number) => Promise<FirebaseTokenResponse>;
}

class firebaseTokenRepository implements FirebaseTokenRepository {
  constructor(private readonly $axios: any) {}

  async saveToken(
    userId: number,
    token: string,
  ): Promise<CommonStringResponse> {
    return this.$axios.$post('/firebase/token', {
      user_id: userId,
      token,
    });
  }

  async getTokensByUserId(userId: number): Promise<FirebaseTokenResponse> {
    return this.$axios.$get(`/firebase/token/${userId}`);
  }
}

export default firebaseTokenRepository;
