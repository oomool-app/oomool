<template>
  <div>
    <Dialog>
      <DialogTrigger as-child class="bg-white text-black w-40 h-40">
        <Button class="bg-white w-40 h-40 flex flex-col items-end">
          <!-- items-end 클래스 추가 -->
          <div class="mb-auto w-20 h-20"><img src="/img/입장 유령.png" /></div>
          <div class="text-black font-bold items-start mt-auto">
            방 참여하기
          </div>
        </Button>
      </DialogTrigger>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>초대코드 입력하기</DialogTitle>
          <DialogDescription>
            <input
              id="inviteCodeInput"
              v-model="inviteCode"
              type="text"
              class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              placeholder="초대코드를 입력해주세요!"
            />
          </DialogDescription>
        </DialogHeader>
        <Button
          @click="
            $router.push({ path: `waitroom/${inviteCode}/updateProfile` })
          "
        >
          방 입장하기
        </Button>
      </DialogContent>
    </Dialog>
  </div>
</template>

<script setup lang="ts">
const inviteCode = ref('');
</script>
