<template>
  <div class="flex flex-col h-screen bg-primary">
    <div class="flex flex-col">
      <div class="flex justify-between">
        <BackButton color="white"></BackButton>
        <Popover>
          <PopoverTrigger class="text-white">
            <SettingButton color="white"></SettingButton>
          </PopoverTrigger>
          <PopoverContent>
            <ul>
              <NuxtLink :to="`${route.params.inviteCode}/updateRoom`"
                >방설정하기</NuxtLink
              >
              <NuxtLink to="/">퇴장하기</NuxtLink>
            </ul>
          </PopoverContent>
        </Popover>
      </div>
      <div class="flex">
        <DDay class="text-white"></DDay>
        <EndDate class="text-white"></EndDate>
      </div>
      <FeedHeader class="text-white"></FeedHeader>
      <FeedHeader class="text-white"></FeedHeader>
      <FeedHeader class="text-white"></FeedHeader>
      <FeedHeader class="text-white"></FeedHeader>
      <div class="flex p-2">
        <div
          class="flex justify-center bg-background rounded-xl w-1/4 font-bold text-primary m-1"
        >
          <div>
            <UsersIcon></UsersIcon>
          </div>
          <div>{{ Users.length }}/30</div>
        </div>
        <div class="bg-amber-400 rounded-xl m-1 w-1/6">
          <div class="flex justify-center items-center text-primary font-bold">
            대기중
          </div>
        </div>
      </div>
    </div>
    <div class="flex flex-col grow bg-background rounded-t-md">
      <ScrollArea class="h-80">
        <div v-for="user in Users" :key="user.user_id">
          <WaitingUser :user="user"></WaitingUser>
        </div>
        <Dialog>
          <DialogTrigger>
            <div>+ 더 많은 친구 초대하기</div>
          </DialogTrigger>
          <DialogContent
            class="border-none bg-white rounded-t-xl relative h-56 bottom-28"
          >
            <DialogHeader>
              <DialogTitle>방 초대하기</DialogTitle>
              <DialogDescription>
                <div>1Bx7Yf8d</div>
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
      <div class="flex justify-center">
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
const route = useRoute();
interface User {
  user_id: number;
  user_email: string;
  player_nickname: string;
  player_background_color: string;
  player_avatar_url: string;
}

const Users = ref<User[]>([
  {
    user_id: 1,
    user_email: 'aaaa@gmail.com',
    player_nickname: 'AAAA',
    player_background_color: 'red',
    player_avatar_url: '/img/방만들기유령.png',
  },
  {
    user_id: 2,
    user_email: 'bbbb@gmail.com',
    player_nickname: 'BBBB',
    player_background_color: 'red',
    player_avatar_url: '/img/방만들기유령.png',
  },
  {
    user_id: 3,
    user_email: 'cccc@gmail.com',
    player_nickname: 'CCCC',
    player_background_color: 'red',
    player_avatar_url: '/img/방만들기유령.png',
  },
  {
    user_id: 4,
    user_email: 'dddd@gmail.com',
    player_nickname: 'DDDD',
    player_background_color: 'red',
    player_avatar_url: '/img/방만들기유령.png',
  },
  {
    user_id: 5,
    user_email: 'dddd@gmail.com',
    player_nickname: 'DDDD',
    player_background_color: 'red',
    player_avatar_url: '/img/방만들기유령.png',
  },
  {
    user_id: 6,
    user_email: 'dddd@gmail.com',
    player_nickname: 'DDDD',
    player_background_color: 'red',
    player_avatar_url: '/img/방만들기유령.png',
  },
  {
    user_id: 7,
    user_email: 'dddd@gmail.com',
    player_nickname: 'DDDD',
    player_background_color: 'red',
    player_avatar_url: '/img/방만들기유령.png',
  },
]);
</script>
