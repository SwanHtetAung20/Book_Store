<script setup lang="ts">
import { RouterLink } from "vue-router";
import { useCounterStore } from "@/stores/counter";
const main = useCounterStore();
const { isAdmin, logout } = main;
</script>

<template>
  <div class="bg-gray-100 font-family-karla flex">
    <aside class="relative bg-blue-600 h-screen w-64 hidden sm:block shadow-xl">
      <div class="p-6" v-show="isAdmin()">
        <RouterLink
          to="/home"
          class="text-white text-3xl font-semibold uppercase hover:text-gray-300"
          >Admin</RouterLink
        >
        <RouterLink to="/add-book">
          <button
            class="w-full bg-white cta-btn font-semibold py-2 mt-5 rounded-br-lg rounded-bl-lg rounded-tr-lg shadow-lg hover:shadow-xl hover:bg-gray-300 flex items-center justify-center"
          >
            <i class="pi pi-book mr-3"></i> Add Book
          </button>
        </RouterLink>
      </div>
      <nav class="text-white text-base font-semibold pt-3">
        <RouterLink
          to="/home"
          class="flex items-center active-nav-link text-white py-4 pl-6 nav-item"
        >
          <i class="pi pi-cart-arrow-down mr-3"></i>
          Books
        </RouterLink>
        <RouterLink
          v-show="isAdmin()"
          to="/payment-request"
          class="flex items-center text-white opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
        >
          <i class="pi pi-shop mr-3"></i>
          Payment Request
        </RouterLink>
        <a
          href="#"
          @click="logout()"
          class="flex items-center text-white opacity-75 hover:opacity-100 py-4 pl-6 nav-item"
        >
          <i class="pi pi-sign-out mr-3"></i>
          Logout
        </a>
      </nav>
    </aside>

    <div class="w-full flex flex-col h-screen overflow-y-hidden">
      <!-- <header class="w-full items-center bg-white py-2 px-6 hidden sm:flex">
        <div class="w-1/2"></div>
        <div x-data="{ isOpen: false }" class="relative w-1/2 flex justify-end">
          <button
            class="realtive z-10 w-12 h-12 rounded-full overflow-hidden border-4 border-gray-400 hover:border-gray-300 focus:border-gray-300 focus:outline-none"
          >
            <img src="https://source.unsplash.com/uJ8LNVCBjFQ/400x400" />
          </button>
          <button
            x-show="isOpen"
            class="h-full w-full fixed inset-0 cursor-default"
          ></button>
          <div
            x-show="isOpen"
            class="absolute w-32 bg-white rounded-lg shadow-lg py-2 mt-16"
          >
            <a href="#" class="block px-4 py-2 account-link hover:text-white"
              >Account</a
            >
            <a href="#" class="block px-4 py-2 account-link hover:text-white"
              >Support</a
            >
            <a href="#" class="block px-4 py-2 account-link hover:text-white"
              >Sign Out</a
            >
          </div>
        </div>
      </header> -->

      <div class="w-full overflow-x-hidden border-t flex flex-col">
        <main class="w-full flex-grow p-6">
          <slot />
        </main>

        <footer class="w-full bg-white text-right p-4">
          Books shopping App By
          <a
            target="_blank"
            href="https://github.com/Swanhtetaung20"
            class="underline"
            >Sha</a
          >.
        </footer>
      </div>
    </div>
  </div>
</template>
