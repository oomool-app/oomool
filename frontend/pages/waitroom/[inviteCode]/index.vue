<template>
  <div class="box flex flex-col h-screen bg-primary">
    <div class="flex flex-col p-6">
      <div class="flex justify-between">
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
      <div class="flex">
        <div class="text-white">
          {{ getWaitRoomData?.data.setting.start_date }}
        </div>
        <div class="text-white">
          {{ getWaitRoomData?.data.setting.end_date }}
        </div>
      </div>
      <div class="text-white">
        <FeedHeader
          :header-name="getWaitRoomData?.data.setting.title"
        ></FeedHeader>
      </div>
      <div class="flex p-2">
        <div
          class="flex items-center justify-center bg-background rounded-xl w-auto font-bold text-primary m-1 p-2"
        >
          <div>
            <UsersIcon></UsersIcon>
          </div>
          <div>
            {{ getWaitRoomData?.data.players.length }}/{{
              getWaitRoomData?.data.setting.max_member
            }}
          </div>
        </div>
        <div
          class="flex justify-center items-center text-primary font-bold bg-amber-400 rounded-xl m-1 p-0.5 w-auto"
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
    </div>
    <div class="flex flex-col grow bg-background rounded-t-md">
      <ScrollArea class="h-80">
        <div v-for="user in getWaitRoomData?.data.players" :key="user.user_id">
          <WaitingUser :user="user"></WaitingUser>
        </div>
        <Dialog>
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
            <DialogHeader>
              <DialogTitle>방 초대하기</DialogTitle>
              <DialogDescription>
                <div>{{ getWaitRoomData?.data.invite_code }}</div>
              </DialogDescription>
            </DialogHeader>

            <DialogFooter>
              <div class="flex">
                <Button> <ShareIcon></ShareIcon>공유하기 </Button>
                <Button class="bg-neutral-600 hover:bg-neutral-500">
                  <CopyIcon></CopyIcon>복사하기
                </Button>
              </div>
            </DialogFooter>
          </DialogContent>
        </Dialog>
      </ScrollArea>
      <div
        v-if="
          getWaitRoomData?.data.players.length ===
            getWaitRoomData?.data.setting.max_member && auth
        "
        class="flex justify-center"
      >
        <Button class="w-1/2" @click="createRoom"> 시작 </Button>
      </div>
      <div v-else class="flex justify-center">
        <Button class="w-1/2" disabled>
          <Loader2 class="w-4 h-4 animate-spin" />
          시작 대기중
        </Button>
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
const userItem = localStorage.getItem('user');
if (userItem !== null) {
  userInfo.value = JSON.parse(userItem);
}
onMounted(async (): Promise<void> => {
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
    console.error(error);
  }
  // const roomData = await $fetch('https://api-dev.oomool.site/rooms', {
  //   method: 'POST',
  //   body: JSON.stringify({
  //     invite_code: inviteCode,
  //   }),
  // });
  // const roomId = ref();
  // if (roomData !== null && roomData !== undefined) {
  //   roomId.value = roomData.data.roomUid;
  // }
  // await $fetch(`https://api-dev.oomool.site/questions/${roomId.value}`, {
  //   method: 'POST',
  // });
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
