<template>
  <div class="flex flex-col items-center">
    <input
      v-model="members"
      :style="{ background: inputStyle }"
      min="3"
      max="30"
      type="range"
    />
  </div>
</template>

<script setup lang="ts">
const props = defineProps({
  peopleNumber: {
    type: Number,
    default: 3,
  },
});
const members: Ref<number> = ref(props.peopleNumber);
const emit = defineEmits<(e: 'update-number', number: number) => void>();
watch(
  () => members.value,
  () => {
    emit('update-number', Number(members.value));
  },
);
const inputStyle = computed(() => {
  return `linear-gradient(to right, #4e297c 0%, #4e297c ${(members.value - 3) * (100 / 27)}%, #d5d4d3 ${(members.value - 3) * (100 / 27)}%, #d5d4d3 100%)`;
});
</script>

<style scoped>
input {
  appearance: none;
  width: 100%;
  height: 0.75rem;
  background: #d5d4d3;
  cursor: pointer;
  border-radius: 1rem;
}

input::-webkit-slider-thumb {
  appearance: none;
  width: 1.25rem;
  height: 1.25rem;
  background: #fff;
  border: 1px solid #4e297c;
  border-radius: 50%;
  cursor: pointer;
}
</style>
