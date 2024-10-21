<template>
  <div class="flex flex-col h-screen justify-center items-center bg-gray-500">
    <div class="mb-4">
      <h1 class="text-2xl text-white">
        Please Enter the verification code we just sent.<i
          class="pi pi-chevron-circle-down ml-2"
        ></i>
      </h1>
    </div>
    <div ref="container" class="flex justify-center items-center gap-4">
      <input
        v-for="n in length"
        :key="n"
        type="text"
        v-model="otpArrays[n - 1]"
        maxlength="1"
        class="border rounded-md w-10 p-2 text-center"
        @keyup="(e) => handleKeyInput(e, n - 1)"
      />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import { useCounterStore } from "../stores/counter";
import { storeToRefs } from "pinia";

const main = useCounterStore();
const { activate_Account } = main;

const otpArrays = ref(Array(6).fill(""));
const length = ref<number>(6);
const container = ref();

const handleKeyInput = (e: KeyboardEvent, n: number) => {
  const children = container.value.children;
  const keyPressed = e.key;

  if (n > 0 && (keyPressed === "Backspace" || keyPressed === "Delete")) {
    otpArrays.value[n] = "";
    setTimeout(() => {
      children[n - 1].focus();
    }, 50);
  } else if (keyPressed.match(/^[0-9]$/)) {
    otpArrays.value[n] = keyPressed;
    if (n < length.value - 1) {
      setTimeout(() => {
        children[n + 1].focus();
      }, 50);
    } else {
      if (otpArrays.value.every((value) => value !== "")) {
        getOtpValue();
      }
    }
  }
  handleInputCheck();
};

const handleInputCheck = () => {
  const children = container.value.children;

  for (let i = 0; i < length.value; i++) {
    if (otpArrays.value[i] === "") {
      children[i].classList.add("border-red-800");
    } else {
      children[i].classList.remove("border-red-800");
    }
  }
};

const getOtpValue = async () => {
  const otpValue = otpArrays.value.join("");
  await activate_Account(otpValue);
  console.log("OTP Value:", otpValue);
  otpArrays.value = Array(length.value).fill("");
};
</script>
