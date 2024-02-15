import { defineStore } from 'pinia';
import type { DatePickerRangeObject } from '~/components/ui/calendar/Calendar.vue';

export const useMakeRoomStore = defineStore('make', () => {
  const name: Ref<string> = ref('');
  const type: Ref<string> = ref('');
  const number: Ref<number> = ref(3);
  const range: Ref<DatePickerRangeObject> = ref({
    start: new Date(),
    end: new Date(),
  });
  function formatDate(date: any): string {
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0');
    const day = date.getDate().toString().padStart(2, '0');

    return `${year}-${month}-${day}`;
  }

  const diffDate = (start: string, end: string): number => {
    const startDate = new Date(start);
    const endDate = new Date(end);
    const diff = endDate.getTime() - startDate.getTime();
    return diff / (1000 * 60 * 60 * 24) + 1;
  };

  function resetSetting(): void {
    name.value = '';
    type.value = '';
    number.value = 3;
    range.value = {
      start: new Date(),
      end: new Date(),
    };
  }

  return { name, type, number, range, formatDate, diffDate, resetSetting };
});
