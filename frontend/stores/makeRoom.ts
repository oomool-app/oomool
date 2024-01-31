import { defineStore } from 'pinia';
import type { DatePickerRangeObject } from '~/components/ui/calendar/Calendar.vue';

export const useMakeRoomStore = defineStore('make', () => {
  const name: Ref<string> = ref('');
  const type: Ref<string> = ref('');
  const number: Ref<number> = ref(3);
  const range: Ref<DatePickerRangeObject> = ref({
    start: Date(),
    end: Date(),
  });
  return { name, type, number, range };
});
