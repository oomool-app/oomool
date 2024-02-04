<template>
  <div>D-{{ dDay }}</div>
</template>

<script setup lang="ts">
const dDay = ref(0);

// Date 객체 생성
const today: any = new Date();
// 종료일 가정
const endDate: any = new Date(2024, 1, 16);
// D-day 계산을 위해 24시간 더하기
endDate.setHours(endDate.getHours() + 24);
// D-day 계산
const dayDiff: number = endDate - today;
dDay.value = Math.floor(dayDiff / (1000 * 60 * 60 * 24));
</script>
