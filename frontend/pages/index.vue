<template>
  <div class="bg-primary">
    <h1 class="text-2xl font-bold text-left pl-4 pt-4 text-white">OooMool</h1>

    <!--알림-->
    <div class="flex justify-end text-white pr-4">
      <Nuxt-link to="/message">알림</Nuxt-link>
    </div>
    <!--방 만들기, 방 참여하기 버튼-->
    <div class="flex flex-row justify-center pt-8 pb-4 pr-2 pl-4 space-x-4 text-center">
      <Button class="bg-white text-black w-40 h-40"><Nuxt-link to="/makeroom/name">방 만들기</Nuxt-link></Button>

      <InputCodeModal></InputCodeModal>
    </div>

    <!-- 방 목록-->
    <div class="bg-white rounded-t-lg">
      <h1 class="text-xl font-bold p-4">나의 방 목록</h1>

      <div class="flex flex-col justify-center p-6 space-y-4">
        <!--방 개수대로 for문-->
        <RoomCard></RoomCard>
        <RoomCard></RoomCard>
        <RoomCard></RoomCard>
      </div>
    </div>
  </div>
</template>

<script setup>


</script>
