<template>
  <div class="bg-background">
    <div class="mb-6 text-center">
      <h1 class="text-2xl font-bold">메인 페이지</h1>
      <!--알림-->
      <div class="flex justify-end">
        <Nuxt-link to="/message">알림</Nuxt-link>
      </div>
    </div>
    <!--방 만들기, 방 참여하기 버튼-->
    <div class="grid grid-cols-2 pt-2 pb-4 pr-2 pl-2 space-x-4 text-center">
      <Card class="bg-primary text-white"
        ><Nuxt-link to="/makeroom/name">방 만들기</Nuxt-link></Card
      >

      <Card class="bg-primary text-white">방 참여하기</Card>
      <!--클릭하면 방 코드 입력 모달창 component 오픈-->
    </div>

    <!-- 방 목록-->
    <div class="flex justify-start pt-4 pb-4">
      <h1 class="text-xl font-bold">방 목록</h1>
    </div>

    <!--방 개수대로 for문-->
    <!--Card component 이용-->
    <Card>
      <CardHeader>
        <CardTitle>방 이름</CardTitle>
      </CardHeader>
      <CardContent>
        <CardDescription>오늘의 질문</CardDescription>
      </CardContent>
      <CardFooter> 진행중여부 </CardFooter>
    </Card>
  </div>
</template>

<script setup></script>
