<template>
  <div
    class="max-w-sm bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700"
  >
    <a href="#">
      <img class="rounded-t-lg" :src="book.photo" alt="Card Image" />
    </a>
    <div class="p-5">
      <a href="#">
        <h5
          class="mb-2 text-2xl font-bold tracking-tight text-gray-900 dark:text-white"
        >
          {{ book.title }}

          ${{ book.price }}
        </h5>
      </a>
      <p class="mb-3 font-normal text-gray-700 dark:text-gray-400">
        {{ book.description }}
      </p>
      <div class="flex justify-between">
        <a
          @click="purchaseHandler(book)"
          class="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800"
        >
          <i class="pi pi-shopping-cart mr-2"></i>Purchase
        </a>
        <a
          v-show="isAdmin()"
          @click="editHandler(book)"
          class="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-green-700 rounded-lg hover:bg-green-800 focus:ring-4 focus:outline-none focus:ring-green-300 dark:bg-green-600 dark:hover:bg-green-700 dark:focus:ring-green-800"
        >
          <i class="pi pi-pen-to-square mr-2"></i>Edit
        </a>

        <a
          v-show="isAdmin()"
          @click="deleteHandler(book.id)"
          class="inline-flex items-center px-3 py-2 text-sm font-medium text-center text-white bg-red-700 rounded-lg hover:bg-red-800 focus:ring-4 focus:outline-none focus:ring-red-300 dark:bg-red-600 dark:hover:bg-red-700 dark:focus:ring-red-800"
        >
          <i class="pi pi-trash mr-2"></i>Delete
        </a>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { type Book, useCounterStore, type BookRequest } from "@/stores/counter";
import { storeToRefs } from "pinia";
import { useRouter } from "vue-router";

const main = useCounterStore();

const { selectedBook } = storeToRefs(main);
const { deleteBook, isAdmin } = main;
const router = useRouter();

defineProps<{
  book: {
    type: Book;
    default: {};
  };
}>();

const deleteHandler = async (id: string) => {
  const confirm = window.confirm("Are you sure? You want to delete.!");
  if (confirm) {
    await deleteBook(id);
  }
};

const editHandler = async (obj: Book) => {
  selectedBook.value = obj;
  router.push("/book-edit");
};

const purchaseHandler = async (obj: Book) => {
  selectedBook.value = obj;
  router.push("/buy-book");
};
</script>
