<template>
  <div class="box flex flex-col items-center bg-background justify-center">
    <div class="mb-6 text-center">
      <h1 class="text-2xl pt-8 font-bold">프로필 설정하기</h1>

      <!--프로필 이미지 미리보기-->
      <div class="flex pb-10 pt-10 justify-center items-start gap-2.5">
        <div
          class="relative flex h-32 w-32 px-0 py-2.5 justify-center rounded-full border-solid border-2 border-[#6D6D6D]"
          :style="{ backgroundColor: randomColor }"
        >
          <img :src="randomAvatar" alt="프로필 아바타" />
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
          type="text"
          class="flex bg-[#F9FAFB] rounded-md border-2 border-[#6D6D6D] items-center w-80 h-12 justify-center text-s pl-2"
          placeholder="kakao nickname"
          required
        />
        <h1 class="pt-1 pb-5 text-xs font-bold flex justify-start text-primary">
          중복, 공백을 포함한 10글자 이내의 영문, 한글, 숫자
        </h1>
      </div>
    </div>
    <!--설정하기 버튼-->
    <Button
      class="mx-auto"
      @click="$router.push({ path: `/waitroom/${inviteCode}` })"
      >프로필 설정하기</Button
    >
  </div>
</template>

<script setup lang="ts">
const route = useRoute();

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

// 링크에서 갖고 있는 초대코드 받아와서 다시 라우터링크에서 사용
const inviteCode = route.params.inviteCode;
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
