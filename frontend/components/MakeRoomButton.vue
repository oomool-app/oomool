<template>
  <Nuxt-link to="/makeroom/name">
    <Button
      class="bg-white w-40 h-40 flex flex-col items-end relative shadow-lg"
    >
      <div class="mb-auto w-24 h-auto pt-2 drop-shadow-2xl">
        <img src="/img/방만들기유령3.png" />
      </div>
      <div class="text-[#454545] absolute left-3 bottom-11">
        새로운 방을 원한다면?
      </div>
      <div
        class="font-extrabold mt-auto absolute left-3.5 bottom-3 text-2xl text-primary"
      >
        방 만들기
      </div>
      <svg
        xmlns="http://www.w3.org/2000/svg"
        width="30"
        height="30"
        viewBox="0 0 25 24"
        fill="none"
        class="absolute right-2 bottom-3.5"
      >
        <path
          d="M12.5 5V19"
          stroke="#61339B"
          stroke-width="3"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
        <path
          d="M5.5 12H19.5"
          stroke="#61339B"
          stroke-width="3"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
      </svg>
    </Button>
  </Nuxt-link>
</template>
