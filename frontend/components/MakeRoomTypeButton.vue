<template>
  <div id="select" class="flex border border-purple-800 rounded-lg">
    <input id="select1" type="radio" name="shop" />
    <label class="h-10 rounded-md w-1/2 rounded-e-none pt-2 pb-2" for="select1">
      친해지는 중이에요!
    </label>
    <input id="select2" type="radio" name="shop" />
    <label class="h-10 rounded-md w-1/2 rounded-s-none pt-2 pb-2" for="select2">
      우리 많이 친해요!
    </label>
  </div>
</template>

<style>
#select input[type='radio'] {
  display: none;
}

#select input[type='radio'] + label {
  cursor: pointer;
  border: 1px solid #333;
  text-align: center;
  font-weight: bold;
  background-color: #fff;
  color: #333;
}

#select input[type='radio']:checked + label {
  background-color: #61339b;
  color: #fff;
}
</style>
