<template>
  <div class="flex justify-center">
    <progress
      id="create-progress"
      :value="props.curPage"
      min="0"
      max="4"
    ></progress>
  </div>
</template>
<script setup lang="ts">
const props = defineProps<{
  curPage?: string;
}>();
</script>
<style>
#create-progress {
  appearance: none;
  width: 100%;
}

#create-progress::-webkit-progress-bar {
  background: #e7e7e7;
  border-radius: 10px;
}

#create-progress::-webkit-progress-value {
  border-radius: 10px;
  background: linear-gradient(to right, #4e297c, #4e297c);
}
</style>
