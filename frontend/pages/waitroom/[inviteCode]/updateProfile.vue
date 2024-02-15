<template>
  <div class="box flex flex-col items-center bg-background justify-center">
    <div class="mb-6 text-center">
      <h1 class="text-2xl pt-8 font-bold">프로필 설정하기</h1>

      <!--프로필 이미지 미리보기-->
      <div class="flex pb-10 pt-10 justify-center items-start gap-2.5">
        <div
          class="relative flex h-32 w-32 px-1 py-3 justify-center rounded-full border-solid border-2 border-[#6D6D6D]"
          :style="{ backgroundColor: randomColor }"
        >
          <img :src="randomAvatar" alt="프로필 아바타" class="h-24 w-auto" />
          <RandomButton
            class="absolute right-0 bottom-0"
            @click="changeRandom"
          ></RandomButton>
        </div>
      </div>

      <!--입력 창-->
      <div class="pl-5 pb-20">
        <h1 class="pt-15 pb-1 text-xl font-extrabold flex justify-start">
          사용자 이름을 입력해주세요
        </h1>
        <h1 class="text-s pb-8 font-normal text-gray-500 flex justify-start">
          다른 친구들이 구분할 수 있는 이름을 사용해주세요.
        </h1>
        <input
          id="username"
          v-model="nickname"
          type="text"
          class="flex bg-[#F9FAFB] rounded-md border-2 border-[#6D6D6D] items-center w-80 h-12 justify-center text-s pl-2"
          placeholder="kakao nickname"
          required
        />
        <h1 class="pt-1 pb-5 text-xs font-bold flex justify-start text-primary">
          공백을 포함한 6글자 이내의 영문, 한글, 숫자
        </h1>
      </div>
    </div>
    <!--설정하기 버튼-->
    <Button class="mx-auto" @click="setting">프로필 설정하기</Button>
  </div>
</template>

<script setup lang="ts">
import { type IJoinWaitRoomInput } from '~/repository/modules/interface/waitroom.interface';

const router = useRouter();
const route = useRoute();
// const router = useRouter();

const colors: string[] = [
  '#FFD8CC',
  '#CCFFD8',
  '#D8CCFF',
  '#FFD8F2',
  '#D8F2FF',
  '#FFF3CC',
  '#CCE3FF',
  '#FFEAC4',
  '#C4B5A5',
  '#F8C4D6',
  '#D8A5CC',
  '#B2E8EB',
  '#A4B5C3',
];

const avatarurls: string[] = [
  '/img/bearGhost.png',
  '/img/catGhost.png',
  '/img/defaultGhost1.png',
  '/img/defaultGhost2.png',
  '/img/sangwooGhost.png',
  '/img/rabbitGhost.png',
  '/img/musicGhost.png',
  '/img/alienGhost.png',
  '/img/glassesGhost.png',
];

// 인덱스 랜덤 생성
const randomColorIndex: number = Math.floor(Math.random() * colors.length);
const randomAvatarIndex: number = Math.floor(Math.random() * avatarurls.length);

// 최초로 주어지는 랜덤 프로필
const randomColor = ref(colors[randomColorIndex]);
const randomAvatar = ref(avatarurls[randomAvatarIndex]);

// 랜덤 버튼 누르면 실행되는 함수들
const changeRandom = (): void => {
  randomColor.value = getRandomColor();
  randomAvatar.value = getRandomAvatar();
};

// 랜덤 색 반환
function getRandomColor(): string {
  const randomColorIndex: number = Math.floor(Math.random() * colors.length);
  return colors[randomColorIndex];
}

// 랜덤 아바타 반환
function getRandomAvatar(): string {
  const randomAvatarIndex: number = Math.floor(
    Math.random() * avatarurls.length,
  );
  return avatarurls[randomAvatarIndex];
}

const userInfo = ref();
const nickname = ref();
const userStore = useUserStore();
const { $api } = useNuxtApp();

const waitroom = ref();

// 초대코드로 대기방 정보 가져오기
const getWaitroomInfo = async (): Promise<void> => {
  try {
    const inviteCode = route.params.inviteCode;
    if (typeof inviteCode === 'string') {
      const response = await $api.make.getWaitRoom(inviteCode);
      waitroom.value = response.data;
    }
  } catch (error) {
    console.error('Error while fetching waitroom:', error);
  }
};

// 방 참여하기
const join = async (): Promise<void> => {
  try {
    await getWaitroomInfo();
    if (waitroom.value.players.length === waitroom.value.setting.max_member) {
      alert('게임 참여 인원이 초과되었습니다.');
      await router.push('/');
      return;
    }
    // 링크에서 갖고 있는 초대코드
    const input = ref<IJoinWaitRoomInput>();
    const inviteCode = route.params.inviteCode;

    input.value = {
      user_id: userInfo.value.id,
      user_email: userInfo.value.email,
      player_nickname: nickname.value,
      player_background_color: randomColor.value,
      player_avatar_url: randomAvatar.value,
    };

    if (typeof inviteCode === 'string') {
      await $api.make.joinWaitRoom(input.value, inviteCode);
      await router.replace(`/waitroom/${inviteCode}`);
    }
  } catch (error: any) {
    alert('잘못된 접근!!');
  }
};

// 닉네임 중복 검사
const checkNicknameUnique = (): boolean => {
  if (waitroom.value.players == null || waitroom.value.players === undefined) {
    return true;
  } else {
    const existingUserNicknames: string[] = (waitroom.value?.players ?? []).map(
      (player: any) => player.player_nickname,
    );

    const isNicknameUnique: boolean = !existingUserNicknames.includes(
      String(nickname.value ?? ''),
    );

    if (!isNicknameUnique) {
      alert('이미 존재하는 닉네임입니다. 다른 닉네임을 입력해주세요.');
      return false;
    }
  }
  return true;
};

// 닉네임 유효성 검사
const checkNicknameValidity = (): boolean => {
  const isNicknameValid: boolean = /[a-zA-Z0-9가-힣]{1,6}$/.test(
    nickname.value as string,
  );

  if (nickname.value === '') {
    alert('닉네임을 입력해주세요');
    return false;
  }

  if (!isNicknameValid) {
    alert(
      '유효하지 않은 닉네임입니다. 6글자 이내의 영문, 한글, 숫자만 입력 가능합니다.',
    );
    return false;
  }
  return true;
};

const nowPageKey = ref<string>('');
// 이전 페이지 URL 저장
const savePreviousPageUrl = (): void => {
  sessionStorage.setItem(nowPageKey.value, route.fullPath);
};

// 로그인 체크
const checkLogin = async (): Promise<void> => {
  try {
    const storedUser = userStore.getStoredUser();

    if (storedUser == null) {
      // 이전 페이지 URL 저장
      savePreviousPageUrl();
      await router.push({ path: '/login' });
    }
  } catch (error) {
    console.error('Error while fetching room list:', error);
  }
};

const setting = async (): Promise<void> => {
  if (!checkNicknameValidity()) {
    return;
  }

  if (!checkNicknameUnique()) {
    return;
  }

  userInfo.value = userStore.getStoredUser();
  await join();
};

onBeforeMount(async () => {
  await checkLogin();
  await getWaitroomInfo();
});
</script>
<style scoped>
.box {
  animation: fade-in 0.5s ease-in-out;
}
@keyframes fade-in {
  from {
    opacity: 0;
  }

  to {
    opacity: 1;
  }
}
</style>
