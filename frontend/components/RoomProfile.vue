<template>
  <div
    class="w-24 h-32 border-2 border-primary rounded-xl m-2 mt-6 pt-2 pb-2 flex flex-col justify-around items-center"
  >
    <div v-if="props.member.user_id === userId" class="text-primary font-bold">
      ME!
    </div>
    <div v-else-if="props.member.user_id !== userId" class="w-5 h-5"></div>

    <div
      class="border rounded-full w-16 h-16 flex justify-center items-center"
      :style="{ 'background-color': member.player_background_color }"
    >
      <img
        class="w-10 h-auto"
        :src="props.member.player_avatar_url"
        alt="프로필 이미지"
      />
    </div>
    <div class="font-semibold">
      {{ props.member.player_nickname }}
    </div>
  </div>
</template>

<script setup lang="ts">
import type Player from '../repository/modules/interface/players.interface';
const props = defineProps<{
  member: Player;
}>();
const userId = useUserStore().getStoredUser()?.id;
</script>
