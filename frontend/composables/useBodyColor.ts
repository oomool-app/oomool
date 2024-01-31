import { ref, onMounted, onBeforeUnmount, type Ref } from 'vue';

export const useBodyColor = (targetColor: string): void => {
  const prevColor: Ref<string> = ref('');

  const changeBodyColor = (color: string): void => {
    if (typeof window !== 'undefined') {
      prevColor.value = document.body.style.backgroundColor;
      document.body.style.backgroundColor = color;
    }
  };

  const resetBodyColor = (): void => {
    if (typeof window !== 'undefined') {
      document.body.style.backgroundColor = prevColor.value;
    }
  };

  onMounted(() => {
    changeBodyColor(targetColor); // 원하는 배경색으로 설정
  });

  onBeforeUnmount(() => {
    resetBodyColor();
  });
};
