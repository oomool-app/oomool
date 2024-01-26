<template>
  <div class="flew flex-col bg-primary w-screen h-screen m-0 p-0">
    <div>
      <div class="flex justify-around">
        <BackButton class="text-white"></BackButton>
        <Popover>
          <PopoverTrigger class="text-white"> ... </PopoverTrigger>
          <PopoverContent>
            <ul>
              <li>방설정하기</li>
              <li>퇴장하기</li>
            </ul>
          </PopoverContent>
        </Popover>
      </div>
      <div>
        <DDay class="text-white"></DDay>
        <EndDate class="text-white"></EndDate>
      </div>
      <FeedHeader class="text-white"></FeedHeader>
    </div>
    <div class="bg-background rounded-t-lg flew grow">
      <ScrollArea class="rounded-md p-4">
        <div>
          사진 이름
          <Popover>
            <PopoverTrigger>
              <InfoIcon class="text-purple-800" />
            </PopoverTrigger>
            <PopoverContent> 이메일 정보 </PopoverContent>
          </Popover>
        </div>
        <div>+ 더 많은 친구 초대하기</div>
      </ScrollArea>
      <Button disabled>
        <Loader2 class="w-4 h-4 animate-spin" />
        시작 대기중
      </Button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { Loader2 } from 'lucide-vue-next';
</script>
