<template>
  <div
    :class="[
      'flex',
      'flex-row',
      'items-stretch',
      'justify-between',
      'ph-3',
      'pb-3',
      'pl-3',
      'relative',
    ]"
    @click="$router.push({ path: `room/${messages.room_uid}` })"
  >
    <!--아이콘-->
    <div class="flex-none self-center pl-3 pr-6">
      <div v-if="props.messages.type == 'system'">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
        >
          <path
            d="M18 8C18 6.4087 17.3679 4.88258 16.2426 3.75736C15.1174 2.63214 13.5913 2 12 2C10.4087 2 8.88258 2.63214 7.75736 3.75736C6.63214 4.88258 6 6.4087 6 8C6 15 3 17 3 17H21C21 17 18 15 18 8Z"
            fill="#61339B"
            stroke="#61339B"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M13.73 21C13.5542 21.3031 13.3019 21.5547 12.9982 21.7295C12.6946 21.9044 12.3504 21.9965 12 21.9965C11.6496 21.9965 11.3054 21.9044 11.0018 21.7295C10.6982 21.5547 10.4458 21.3031 10.27 21"
            fill="#61339B"
          />
          <path
            d="M13.73 21C13.5542 21.3031 13.3019 21.5547 12.9982 21.7295C12.6946 21.9044 12.3504 21.9965 12 21.9965C11.6496 21.9965 11.3054 21.9044 11.0018 21.7295C10.6982 21.5547 10.4458 21.3031 10.27 21"
            stroke="#61339B"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </div>
      <div v-else-if="props.messages.type == 'write_feed'">
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
        >
          <path
            d="M11 4H4C3.46957 4 2.96086 4.21071 2.58579 4.58579C2.21071 4.96086 2 5.46957 2 6V20C2 20.5304 2.21071 21.0391 2.58579 21.4142C2.96086 21.7893 3.46957 22 4 22H18C18.5304 22 19.0391 21.7893 19.4142 21.4142C19.7893 21.0391 20 20.5304 20 20V13"
            stroke="#61339B"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M18.5 2.49998C18.8978 2.10216 19.4374 1.87866 20 1.87866C20.5626 1.87866 21.1022 2.10216 21.5 2.49998C21.8978 2.89781 22.1213 3.43737 22.1213 3.99998C22.1213 4.56259 21.8978 5.10216 21.5 5.49998L12 15L8 16L9 12L18.5 2.49998Z"
            stroke="#61339B"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </div>
    </div>

    <div class="flex-1">
      <div class="text-lg font-semibold">{{ props.messages.title }}</div>
      <div class="mb-1 text-gray-500 text-sm w-56 dark:text-gray-400 pt-1">
        {{ props.messages.body }}
      </div>
    </div>
    <div class="text-sm pr-3">
      <div v-if="dayFromCreate >= 1">{{ dayFromCreate }}일 전</div>
      <div v-else-if="hourFromCreate >= 1">{{ hourFromCreate }}시간 전</div>
      <div v-else-if="minFromCreate >= 1">{{ minFromCreate }}분 전</div>
      <div v-else>방금</div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type NotificationMessage from '~/repository/modules/interface/notifications.interface';
const props = defineProps<{
  messages: NotificationMessage;
}>();

// 알림이 생성되고 시간이 얼마나 지났는지
const timeFromCreate =
  new Date().getTime() - new Date(props.messages.created_at).getTime();
const minFromCreate = Math.floor(timeFromCreate / 1000 / 60);
const dayFromCreate = Math.floor(timeFromCreate / (1000 * 60 * 60 * 24));
const hourFromCreate = Math.floor(timeFromCreate / (1000 * 60 * 60));
</script>
