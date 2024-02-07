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
            class="flex bg-white text-[#6D6D6D] font-bold px-3 py-10 w-70 h-24 shadow-inner rounded-xl justify-center items-center"
          >
            대기 방입니다.
          </div>
        </CardHeader>
        <CardContent class="px-3 pt-0">
          <div class="text-white text-xl not-italic h-3">
            {{ props.temprooms.setting.title }}
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
