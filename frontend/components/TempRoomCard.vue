<template>
  <div>
    <Card
      class="w-100% h-auto bg-gradient-to-t from-[#A8BDF9] to-primary border-0 rounded-xl drop-shadow-xl transition-transform transform text-ellipsis overflow-hidden"
    >
      <div
        class="flex flex-col"
        @click="$router.push({ path: `waitroom/${temprooms.invite_code}` })"
      >
        <CardHeader class="px-3.5 pt-3.5 pb-4">
          <div
            class="flex flex-col bg-white h-24 shadow-inner rounded-xl items-center relative px-8"
          >
            <h1 class="z-10 pt-9 font-extrabold text-gray-600">
              대기방입니다.
            </h1>
            <img
              src="/img/defaultGhost2.png"
              class="w-12 h-auto absolute bottom-2 right-2 opacity-30 z-0"
            />
          </div>
        </CardHeader>
        <CardContent class="pl-3 pt-0 pr-0 pb-7 flex flex-row relative">
          <div class="text-white text-xl not-italic h-3">
            {{ props.temprooms?.setting?.title }}
          </div>
        </CardContent>
      </div>
    </Card>
  </div>
</template>

<script setup lang="ts">
interface TempRoom {
  invite_code: string;
  setting: {
    title: string;
    start_date: string;
    end_date: string;
    question_type: string;
    max_member: number;
  };
  master_id: number;
}

const props = defineProps<{
  temprooms: TempRoom;
}>();
</script>
