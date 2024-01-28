<template>
  <div :class="['flex', 'flex-col', 'ph-3', 'pb-3', {'bg-[#E6DAFA]': isReadtimePast}]" @click="$router.push({ path: `room/${messages.roomid }`})">
    <dd class="text-lg font-semibold">{{ props.messages.roomtitle}}</dd>
    <dt class="mb-1 text-gray-500 md:text-lg dark:text-gray-400">
      {{ props.messages.title}}
    </dt>
  </div>
</template>

<script setup lang="ts">
  interface Message {
    messageid: number;
    roomid: number;
    roomtitle: string;
    title: string;
    createdtime : string;
    readtime : string;
  }

  const props = defineProps<{
    messages: Message;
  }>();

  const isReadtimePast = new Date(props.messages.readtime as string) < new Date();

</script>
