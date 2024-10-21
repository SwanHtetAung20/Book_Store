<template>
  <Navbar>
    <div class="h-screen flex flex-col justify-center items-center bg-gray-300">
      <div class="flex flex-col h-full w-full">
        <div class="h-1/6 flex justify-center items-center">
          <h1 class="text-center">
            <i class="pi pi-book mr-2"></i>Book Registration
          </h1>
        </div>
        <div class="h-5/6 flex justify-center">
          <form
            @submit.prevent="handlerBookRegistration"
            class="space-y-4 md:space-y-6 w-3/6"
          >
            <div>
              <label
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Book Name
              </label>
              <input
                type="text"
                v-model="bookRequest.name"
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
                placeholder="book name..."
              />
            </div>
            <div>
              <label
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Title
              </label>
              <textarea
                type="text"
                v-model="bookRequest.title"
                placeholder="title..."
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
            </div>
            <div>
              <label
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Description
              </label>
              <input
                type="text"
                v-model="bookRequest.description"
                placeholder="description..."
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
            </div>

            <div>
              <label
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Author
              </label>
              <input
                type="text"
                v-model="bookRequest.author"
                placeholder="Author..."
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
            </div>

            <div>
              <label
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Price
              </label>
              <input
                type="text"
                v-model="bookRequest.price"
                placeholder="Author..."
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
            </div>

            <div>
              <label
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Cover Photo
              </label>
              <input
                type="file"
                @change="onFileChange"
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
            </div>
            <button
              type="submit"
              class="w-full text-white bg-primary-600 hover:bg-primary-700 focus:ring-4 focus:outline-none focus:ring-primary-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center dark:bg-primary-600 dark:hover:bg-primary-700 dark:focus:ring-primary-800"
            >
              <i class="pi pi-plus-circle mr-2"></i>Add
            </button>
          </form>
        </div>
      </div>
    </div>
  </Navbar>
</template>

<script setup lang="ts">
import Navbar from "../components/Navbar.vue";
import { useCounterStore, type BookRequest } from "../stores/counter";
import { useToast } from "vue-toastification";
import { ref } from "vue";
import { storeToRefs } from "pinia";
const bookRequest = ref<BookRequest>({});
const toast = useToast();
const main = useCounterStore();
const { book_add } = main;

const onFileChange = (e: Event) => {
  const target = e.target as HTMLInputElement;
  if (target && target.files) {
    bookRequest.value.photo = target.files[0];
  }
};

const handlerBookRegistration = async () => {
  if (
    bookRequest.value.name === "" ||
    bookRequest.value.title === "" ||
    bookRequest.value.description === "" ||
    bookRequest.value.author === "" ||
    bookRequest.value.photo === undefined ||
    bookRequest.value.price === ""
  ) {
    toast.error("Fields cannot be empty.!");
    return;
  }
  await book_add(bookRequest.value);
};
</script>
