import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "primeicons/primeicons.css";
import Toast from "vue-toastification";
import "vue-toastification/dist/index.css";
import "./assets/style.css";

const app = createApp(App);

app.use(createPinia());
app.use(router);
app.use(Toast);

app.mount("#app");
