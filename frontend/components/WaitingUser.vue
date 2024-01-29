<template>
  <div class="flex justify-start items-center m-2">
    <div class="flex justify-center items-center border rounded-full w-8 h-8">
      <img src="/img/정답 유령.png" alt="프로필 이미지" />
    </div>
    <div class="m-1">
      {{ props.user.player_nickname }}
    </div>
    <Popover>
      <PopoverTrigger>
        <InfoIcon class="text-purple-800" />
      </PopoverTrigger>
      <PopoverContent>
        <div>
          <p>{{ props.user.user_id }}</p>
          <p>{{ props.user.user_email }}</p>
        </div>
      </PopoverContent>
    </Popover>
  </div>
</template>

<script setup lang="ts">
interface User {
  user_id: string;
  user_email: string;
  player_nickname: string;
  player_background_color: string;
  player_avatar_url: string;
}

const props = defineProps<{
  user: User;
}>();
</script>
