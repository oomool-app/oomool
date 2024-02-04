<template>
  <div>
    <NuxtLink class="underline underline-offset-4" :to="memberPageUrl"
      >전제 멤버 조회</NuxtLink
    >
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{
  roomId: number;
}>();

const memberPageUrl = ref('/members/' + props.roomId);
</script>
