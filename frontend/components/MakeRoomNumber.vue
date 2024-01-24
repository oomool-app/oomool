<template>
  <div class="con">
    <div id="member-count">{{ members }}</div>
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
const members = ref(3);

const inputStyle = computed(() => {
  return `linear-gradient(to right, #4e297c 0%, #4e297c ${(members.value - 3) * (100 / 27)}%, #d5d4d3 ${(members.value - 3) * (100 / 27)}%, #d5d4d3 100%)`;
});
</script>

<style scoped>
input {
  appearance: none;
  width: 80%;
  height: 0.4rem;
  background: #d5d4d3;
  cursor: pointer;
  border-radius: 1rem;
}

input::-webkit-slider-thumb {
  appearance: none;
  width: 0.7rem;
  height: 0.7rem;
  background: #fff;
  border: 1px solid #4e297c;
  border-radius: 50%;
  cursor: pointer;
}

#member-count {
  color: #4e297c;
  font-size: 2rem;
  font-weight: bold;
}

.con {
  display: flex;
  flex-direction: column;
  align-items: center;
}
</style>
