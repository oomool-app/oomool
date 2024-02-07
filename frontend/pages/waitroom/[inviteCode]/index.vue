<template>
  <div
    class="box h-screen grid grid-rows-[4rem,3rem,3rem,3.5rem,auto] bg-primary"
  >
    <div class="flex justify-between items-center px-6 pt-6">
      <BackButton color="white"></BackButton>
      <Popover>
        <PopoverTrigger class="text-white">
          <SettingButton color="white"></SettingButton>
        </PopoverTrigger>
        <PopoverContent>
          <ul>
            <li>
              <NuxtLink :to="`${route.params.inviteCode}/updateRoom`"
                >방설정하기</NuxtLink
              >
            </li>
            <li>
              <NuxtLink to="/">퇴장하기</NuxtLink>
            </li>
          </ul>
        </PopoverContent>
      </Popover>
    </div>
    <div class="flex items-center text-sm text-white px-6">
      {{ getWaitRoomData?.data.setting.start_date }} ~
      {{ getWaitRoomData?.data.setting.end_date }}
    </div>
    <div class="flex items-center text-white px-6">
      <FeedHeader
        :header-name="getWaitRoomData?.data.setting.title"
      ></FeedHeader>
    </div>
    <div class="grid grid-cols-[5rem,4.5rem] gap-3 text-sm px-6 my-4">
      <div
        class="flex justify-center items-center bg-background rounded-xl font-bold text-primary"
      >
        <UsersIcon></UsersIcon>
        <div>
          &nbsp;{{ getWaitRoomData?.data.players.length }} /
          {{ getWaitRoomData?.data.setting.max_member }}
        </div>
      </div>
      <div
        class="flex justify-center items-center text-primary font-bold bg-amber-400 rounded-xl"
      >
        <div
          v-if="
            getWaitRoomData?.data.players.length !==
            getWaitRoomData?.data.setting.max_member
          "
        >
          대기 중
        </div>
        <div v-else>시작 대기</div>
      </div>
    </div>
    <div
      class="flex items-center grid grid-rows-[auto,3rem] bg-background rounded-t-xl p-6"
    >
      <div
        class="overflow-auto pt-1 pb-3"
        :style="{ height: scrollHeight + 'px' }"
      >
        <div
          v-for="user in getWaitRoomData?.data.players"
          :key="user.user_id"
          class="grid grid-cols-[auto,3rem] border-b-2"
        >
          <WaitingUser
            :user="user"
            :master="getWaitRoomData?.data.master_id"
          ></WaitingUser>
          <div class="flex justify-center items-center">x</div>
        </div>
        <Dialog class="flex justify-center items-center text-lg py-2">
          <DialogTrigger>
            <div
              v-if="
                getWaitRoomData?.data.players.length !==
                getWaitRoomData?.data.setting.max_member
              "
            >
              + 더 많은 친구 초대하기
            </div>
          </DialogTrigger>
          <DialogContent
            class="border-none bg-white rounded-t-xl relative h-56 bottom-28"
          >
            <div class="grid grid-rows-[6.5rem,auto]">
              <DialogHeader class="flex flex-col justify-evenly">
                <DialogTitle
                  class="flex justify-center items-center text-2xl font-bold text-primary"
                >
                  방 초대하기
                </DialogTitle>
                <DialogDescription
                  class="flex justify-center items-center text-2xl font-bold"
                >
                  {{ getWaitRoomData?.data.invite_code }}
                </DialogDescription>
              </DialogHeader>
              <div class="flex justify-center items-center">
                <div class="flex justify-center w-1/2 h-2/3">
                  <Button
                    class="rounded-full w-3/4 h-full"
                    @click="shareWaitRoom"
                  >
                    <ShareIcon></ShareIcon>공유하기
                  </Button>
                </div>
                <div class="flex justify-center w-1/2 h-2/3">
                  <Button
                    class="rounded-full w-3/4 h-full bg-neutral-600 hover:bg-neutral-500"
                    @click="copyInviteCode"
                  >
                    <CopyIcon></CopyIcon>복사하기
                  </Button>
                </div>
              </div>
            </div>
          </DialogContent>
        </Dialog>
      </div>
      <div>
        <div
          v-if="
            getWaitRoomData?.data.players.length ===
              getWaitRoomData?.data.setting.max_member && auth
          "
          class="h-full w-full rounded-full text-lg"
        >
          <Button
            class="h-full w-full rounded-full text-lg"
            @click="createRoom"
          >
            시작
          </Button>
        </div>
        <div v-else class="h-full">
          <Button class="h-full w-full rounded-full text-lg" disabled>
            <Loader2 class="animate-spin" />
            시작 대기중
          </Button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Loader2 } from 'lucide-vue-next';
import { type IGetWaitRoomResponse } from '~/repository/modules/interface/waitroom.interface';
import {
  type ICreateRoomUidInput,
  type IGetRoomUidResponse,
} from '~/repository/modules/interface/rooms.interface';
import { type IRegistQuestionToRoomInput } from '~/repository/modules/interface/question.interface';
const { $api } = useNuxtApp();
const route = useRoute();
const router = useRouter();
const auth = ref<boolean>(false);
const getWaitRoomData = ref<IGetWaitRoomResponse>();
const userInfo = ref();
const scrollHeight = ref<number>(0);
onMounted(async (): Promise<void> => {
  const userStore = useUserStore();
  userInfo.value = userStore.getStoredUser();
  scrollHeight.value = window.innerHeight * 0.5;
  await getData();
});

const getData = async (): Promise<void> => {
  const input = route.params.inviteCode;
  if (typeof input === 'string') {
    getWaitRoomData.value = await $api.make.getWaitRoom(input);
    if (getWaitRoomData.value.data.master_id === userInfo.value.id) {
      auth.value = true;
    }
  }
};

const copyInviteCode = async (): Promise<void> => {
  try {
    const input = route.params.inviteCode;
    if (typeof input === 'string') {
      await navigator.clipboard.writeText(input);
      alert('초대코드가 복사되었습니다.');
    }
  } catch (err) {}
};

const shareWaitRoom = async (): Promise<void> => {
  try {
    const inviteCode = route.params.inviteCode;
    if (typeof inviteCode === 'string') {
      await navigator.share({
        title: '우물에 초대합니다!',
        text: `${getWaitRoomData.value?.data.setting.title}에서 당신을 초대합니다.`,
        url: `https://dev.oomool.site/waitroom/${inviteCode}/updateProfile`,
      });
    }
  } catch (err) {}
};

const createRoom = async (): Promise<void> => {
  try {
    const input = ref<ICreateRoomUidInput>();
    const inviteCode = route.params.inviteCode;
    if (typeof inviteCode === 'string') {
      input.value = {
        invite_code: inviteCode,
      };
    }
    const roomData = ref<IGetRoomUidResponse>();
    const temp = ref<string>();
    const roomId = ref<IRegistQuestionToRoomInput>();
    if (input.value !== undefined) {
      roomData.value = await $api.rooms.createRoom(input.value);
      temp.value = roomData.value.data.roomUid;
      roomId.value = {
        roomUid: temp.value,
      };
      await $api.question.registQuestionToRoom(roomId.value);
    }
    await router.push('/');
  } catch (error) {
    alert('잘못된 접근');
  }
};
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
