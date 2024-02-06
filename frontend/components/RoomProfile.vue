<template>
  <div
    id="profile"
    class="w-24 h-32 border rounded-xl m-2 mt-6 pt-2 pb-2 flex flex-col justify-around items-center"
  >
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
    <div class="font-semibold">{{ props.member.player_nickname }}</div>
  </div>
</template>

<script setup lang="ts">
import type Player from '../repository/modules/interface/players.interface';
const props = defineProps<{
  member: Player;
}>();
</script>
<style>
#profile {
  border: solid 2px #61339b;
}
</style>
