<template>
  <div>D-{{ dDay }}</div>
</template>

<script setup>
const dDay = ref(0);

// Date 객체 생성
const today = new Date();
// 종료일 가정
const endDate = new Date(2024, 0, 26);
// D-day 계산을 위해 24시간 더하기
endDate.setHours(endDate.getHours() + 24);
// D-day 계산
const dayDiff = endDate - today;
dDay.value = Math.floor(dayDiff / (1000 * 60 * 60 * 24));
</script>

<style scoped></style>
