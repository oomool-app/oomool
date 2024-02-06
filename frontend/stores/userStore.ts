import { defineStore } from 'pinia';

interface User {
  id: number;
  email: string;
  name: string;
}

export const useUserStore = defineStore({
  id: 'user',
  state: () => ({
    user: null as null | User,
  }),
  actions: {
    // 사용자 정보를 설정하고 세션 스토리지에 저장
    setUser(user: User): void {
      this.user = user;
      if (typeof sessionStorage !== 'undefined') {
        sessionStorage.setItem('user', JSON.stringify(user));
      }
    },
    // 세션 스토리지에서 사용자 정보 가져오기
    getStoredUser(): User | null {
      const storedUser = sessionStorage.getItem('user');
      if (storedUser !== null) {
        // 세션 스토리지에 사용자 정보가 있으면 파싱하여 반환
        return JSON.parse(storedUser);
      } else {
        // 세션 스토리지에 사용자 정보가 없으면 null 반환
        return null;
      }
    },
  },
});