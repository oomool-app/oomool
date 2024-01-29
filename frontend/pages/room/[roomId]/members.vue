<template>
  <div>
    <div class="mb-6 text-center flex justify-between">
      <BackButton color="black" />
      <h1 class="text-2xl font-bold">전체 멤버 조회</h1>
    </div>
    <div v-for="member in members" :key="member.userid" class="grid gap-4 m-3 grid-cols-2">
      <MemberCard :members="member"></MemberCard>
    </div>


  </div>
</template>

<script setup lang="ts">
  const members= [
    {
      userid:1,
      nickname:'현지',
      background : '#FF33A1',
      avatarurl : '/img/곰유령.png' ,
    },
    {
      userid:2,
      nickname:'세정',
      background : '#FF5733',
      avatarurl : '/img/토끼유령.png' ,
    },
    {
      userid:3,
      nickname:'은평',
      background : '#FFD700',
      avatarurl : '/img/고양이유령.png' ,
    },
    {
      userid:4,
      nickname:'필모',
      background : '#33A1FF',
      avatarurl : '/img/기본유령2.png' ,
    },
    {
      userid:5,
      nickname:'병현',
      background : '#607D8B',
      avatarurl : '/img/헤드셋유령.png' ,
    },
    {
      userid:6,
      nickname:'성수',
      background : '#795548',
      avatarurl : '/img/정답 유령.png' ,
    },
  ]
</script>
