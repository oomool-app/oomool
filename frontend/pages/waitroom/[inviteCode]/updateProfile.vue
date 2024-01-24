<template>
  <div class="bg-background">
    <div class="mb-6 text-center">
      <h1 class="text-2xl font-bold">프로필 업데이트</h1>
      <img
        class="h-32 w-32 rounded-full border-4 border-white dark:border-gray-800 mx-auto my-4"
        src="https://randomuser.me/api/portraits/women/21.jpg"
        alt=""
      />

      <!--랜덤 아이콘-->
      <h1 class="pt-15 pb-5 text-xl font-bold flex justify-start">
        사용자 이름을 입력해주세요
      </h1>
      <input
        id="username"
        type="text"
        class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
        placeholder="userkakaonickname"
        required
      />
      <h1 class="pb-5 text-sm font-bold flex justify-start text-primary">
        그룹 내 멤버들이 식별 가능한 닉네임을 입력해주세요
      </h1>
    </div>

    <!-- Next Button Section -->
    <Button> 프로필 수정하기 </Button>
  </div>
</template>
