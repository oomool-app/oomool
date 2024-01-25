<template>
  <div>
    {{ props.question }}
  </div>
</template>

<script setup lang="ts">
const props = defineProps<{
  question: string;
}>();
</script>
