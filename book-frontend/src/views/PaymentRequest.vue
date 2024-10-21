<template>
  <Navbar>
    <div class="h-screen flex flex-col justify-center items-center bg-gray-300">
      <div class="flex flex-col h-full w-full">
        <div class="h-1/6 flex justify-center items-center">
          <h1 class="text-center">
            <i class="pi pi-book mr-2"></i>Ordering books
          </h1>
        </div>
        <div class="h-5/6 flex justify-center">
          <form
            @submit.prevent="buyBookHandler"
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
                v-model="selectedBook.name"
                readonly
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
                v-model="selectedBook.description"
                readonly
                placeholder="title..."
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
            </div>
            <div>
              <label
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                count
              </label>
              <input
                type="number"
                v-model="payment.count"
                placeholder="count..."
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
                v-model="calculatedPrice"
                readonly
                placeholder="description..."
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              />
            </div>

            <div>
              <label
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Payment By
              </label>

              <select
                v-model="payment.paymentBy"
                class="bg-gray-50 border border-gray-300 text-gray-900 rounded-lg focus:ring-primary-600 focus:border-primary-600 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500"
              >
                <option selected>Choose a Payment</option>
                <option value="KBZ">KBZ</option>
                <option value="CB">CB</option>
                <option value="AYA">AYA</option>
              </select>
            </div>

            <div>
              <label
                class="block mb-2 text-sm font-medium text-gray-900 dark:text-white"
              >
                Payment Screen shot
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
              <i class="pi pi-shopping-cart mr-2"></i>Buy
            </button>
          </form>
        </div>
      </div>
    </div>
  </Navbar>
</template>

<script setup lang="ts">
import { ref, computed } from "vue";
import Navbar from "../components/Navbar.vue";
import { useCounterStore, type PaymentRequest } from "../stores/counter";
import { storeToRefs } from "pinia";
import { useToast } from "vue-toastification";

const main = useCounterStore();
const { selectedBook } = storeToRefs(main);
const { orderedBook } = main;

const payment = ref<PaymentRequest>({});
payment.value.count = "1";
const toast = useToast();

const onFileChange = (e: Event) => {
  const target = e.target as HTMLInputElement;
  if (target && target.files) {
    payment.value.file = target.files[0];
  }
};

const calculatedPrice = computed<string>(() => {
  const count = Number(payment.value.count) || 1;
  const price = Number(selectedBook.value.price) || 0;
  const totalPrice = count * price;
  return totalPrice.toString();
});

const buyBookHandler = async () => {
  if (payment.value.count === "" || payment.value.paymentBy === undefined) {
    toast.error("Fields cannot be empty.!");
    return;
  }
  payment.value.bookId = selectedBook.value.id;
  payment.value.price = calculatedPrice.value.toString();
  await orderedBook(payment.value);
};
</script>
