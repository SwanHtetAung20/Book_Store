import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/HomeView.vue";
import LoginView from "@/views/LoginView.vue";
import Register from "@/views/Register.vue";
import activateAccount from "@/components/activate-account.vue";
import NotFound from "@/components/NotFound.vue";
import BookRegistration from "@/views/BookRegistration.vue";
import BookEdit from "@/views/BookEdit.vue";
import PaymentRequest from "@/views/PaymentRequest.vue";
import PaymentRequestView from "@/views/PaymentRequestView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "Login",
      component: LoginView,
    },
    {
      path: "/register",
      name: "Register",
      component: Register,
    },
    {
      path: "/home",
      name: "home",
      component: HomeView,
    },
    {
      path: "/activate-account",
      name: "activateAccount",
      component: activateAccount,
    },
    {
      path: "/add-book",
      name: "book-registraion",
      component: BookRegistration,
    },
    {
      path: "/book-edit",
      name: "EditBook",
      component: BookEdit,
    },
    {
      path: "/buy-book",
      name: "PaymentRequest",
      component: PaymentRequest,
    },
    {
      path: "/payment-request",
      name: "PaymentRequestView",
      component: PaymentRequestView,
    },
    {
      path: "/:catchAll(.*)",
      name: "not-found",
      component: NotFound,
    },
  ],
});

export default router;
