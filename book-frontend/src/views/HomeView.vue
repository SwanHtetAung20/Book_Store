<template>
  <div>
    <Navbar>
      <div class="grid grid-cols-1 md:grid-cols-3 gap-2">
        <Cards v-for="book in bookList" :key="book.id" :book="book" />
      </div>
    </Navbar>
  </div>
</template>

<script setup lang="ts">
import Navbar from "../components/Navbar.vue";
import Cards from "@/components/Cards.vue";
import { ref, onMounted } from "vue";
import { useCounterStore } from "../stores/counter";
import { storeToRefs } from "pinia";

const main = useCounterStore();
const { getAllBook } = main;
const { bookList } = storeToRefs(main);

onMounted(async () => {
  await getAllBook();
});
</script>
