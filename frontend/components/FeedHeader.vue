<template>
  <div>
    {{ headerName }}
  </div>
</template>

<script setup lang="ts">
defineProps({
  headerName:{
    type:String,
    default: "모임 이름",
  }
})
</script>
