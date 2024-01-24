<template>
  <div>
    <NuxtLink :to="memberPageUrl">전제 멤버 조회{{ roomId }}</NuxtLink>
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{
  roomId: number,
}>()

const memberPageUrl = ref("/members/"+props.roomId);
</script>
