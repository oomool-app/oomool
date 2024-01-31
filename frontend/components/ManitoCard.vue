<template>
  <div
    class="flex flex-col border-2 rounded-lg border-primary w-36 h-52 justify-center items-center"
  >
    <svg
      v-if="see"
      xmlns="http://www.w3.org/2000/svg"
      fill="white"
      viewBox="0 0 24 24"
      stroke-width="1.5"
      stroke="#61339B"
      class="w-6 h-6 mb-4"
    >
      <path
        stroke-linecap="round"
        stroke-linejoin="round"
        d="M9 12.75 11.25 15 15 9.75M21 12a9 9 0 1 1-18 0 9 9 0 0 1 18 0Z"
      />
    </svg>

    <div
      class="w-20 h-20 mb-4 rounded-full flex justify-center items-center"
      :style="{ backgroundColor: props.user.background }"
    >
      <img
        class="w-12 h-16"
        :src="`${props.user.profileUrl}`"
        alt="프로필 이미지"
      />
    </div>
    <span class="name inline-block font-semibold">{{ props.user.name }}</span>
  </div>
</template>

<script setup lang="ts">
interface User {
  id: number;
  name: string;
  profileUrl: string;
  background: string;
}

const props = defineProps<{
  user: User;
  see: boolean;
}>();
</script>
