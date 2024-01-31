<template>
  <div
    id="profile"
    class="w-24 h-32 border rounded-xl m-2 mt-6 pt-2 pb-2 flex flex-col justify-around items-center"
  >
    <div class="border rounded-full w-16 h-16">
      <img src="/img/정답유령.png" alt="프로필 이미지" />
    </div>
    <div>{{ props.member.name }}</div>
  </div>
</template>

<script setup lang="ts">
interface members {
  name: string;
}
const props = defineProps<{
  member: members;
}>();
</script>
<style>
#profile {
  border: solid 2px #61339b;
}
</style>
