<template>
  <div class="grid grid-cols-[3rem,auto] gap-3 m-2">
    <div class="flex justify-center items-center">
      <div
        class="flex justify-center items-center border rounded-full w-12 h-12"
        :style="{ backgroundColor: props.user.player_background_color }"
      >
        <img
          class="w-9 h-9"
          :src="`${props.user.player_avatar_url}`"
          alt="프로필 이미지"
        />
      </div>
    </div>
    <div class="flex items-center text-xl font-extrabold">
      <div class="mr-1">
        {{ props.user.player_nickname }}
      </div>
      <Popover class="flex justify-center items-center text-gray-500">
        <PopoverTrigger>
          <InfoIcon />
        </PopoverTrigger>
        <PopoverContent>
          <div class="flex">
            <div class="flex justify-center items-center">
              <img src="/img/kakao.png" class="h-4 mr-2" alt="master" />
            </div>
            <div class="flex justify-center items-center">
              {{ props.user.user_email }}
            </div>
          </div>
        </PopoverContent>
      </Popover>
    </div>
  </div>
</template>

<script setup lang="ts">
interface User {
  user_id: number;
  user_email: string;
  player_nickname: string;
  player_background_color: string;
  player_avatar_url: string;
}

const props = defineProps<{
  user: User;
  master?: number;
}>();
</script>
