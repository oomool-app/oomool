<template>
  <div id="header" class="header text-xl font-black">
    {{ headerName }}
  </div>
</template>

<script setup lang="ts">
defineProps({
  headerName: {
    type: String,
    default: '모임 이름',
  },
});
</script>
<style scope>
#header {
  color: #61339b;
}
</style>
