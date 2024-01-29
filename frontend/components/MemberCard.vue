<!--전체 멤버 조회 페이지에 사용될 컴포넌트-->
<template>
  <div>
    <Card>
      <h1 class="text-2xl font-bold">{{ props.members.nickname }}</h1>
      <CardContent>
        <div
          class="relative flex h-32 w-32 px-0 py-2.5 justify-center rounded-full border-solid border-2 border-[#6D6D6D]"
          :style="{ backgroundColor: props.members.background }"
        >
          <img :src="`${props.members.avatarurl}`" />
        </div>
      </CardContent>
    </Card>
  </div>
</template>

<script setup lang="ts">
interface Member {
  userid: number;
  nickname: string;
  background: string;
  avatarurl: string;
}

const props = defineProps<{
  members: Member;
}>();
</script>
