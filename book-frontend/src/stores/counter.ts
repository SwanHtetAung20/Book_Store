import { ref, computed } from "vue";
import { defineStore } from "pinia";
import { useToast } from "vue-toastification";
import axios from "axios";
import { useRouter } from "vue-router";

export interface Book {
  id?: string;
  name?: string;
  title?: string;
  description?: string;
  author?: string;
  photo?: string;
  price?: string;
}

export interface BookRequest {
  id?: string;
  name?: string;
  title?: string;
  description?: string;
  author?: string;
  price?: string;
  photo?: File;
}

enum paymentBy {
  KBZ,
  AYA,
  CB,
}

enum status {
  INPROGRESS,
  DONE,
}

export interface PaymentRequest {
  id?: string;
  bookId?: string;
  userId?: string;
  count?: string;
  price?: string;
  paymentBy?: paymentBy;
  status?: status;
  createdDate?: Date;
  photo?: string;
  file?: File;
}

export const useCounterStore = defineStore("main", () => {
  const role = ref<string>("");
  const toast = useToast();
  const router = useRouter();
  const bookList = ref<Book[]>([]);
  const selectedBook = ref<Book>({});
  const paymentsList = ref<PaymentRequest[]>([]);

  const login = async (email: string, password: string): Promise<void> => {
    try {
      const res = await axios.post(`/api/auth`, { email, password });
      if (res.status !== 200) {
        toast.error(res.data.error);
        return;
      }
      localStorage.setItem("token", res.data.token);
      role.value = res.data.role;
      console.log("ROLE", res.data.role);
      toast.success("Login Successfully.!");
      router.push("/home");
    } catch (error: any) {
      toast.error(error.response.data.businessErrorDescription);
    }
  };

  const isAdmin = () => {
    return role.value === "ADMIN";
  };

  const register = async (
    name: string,
    email: string,
    password: string
  ): Promise<void> => {
    try {
      const res = await axios.post(`/api/auth/register`, {
        name,
        email,
        password,
      });
      if (res.status !== 202) {
        toast.error(res.data.error);
        return;
      }
      router.push("/activate-account");
    } catch (error: any) {
      if (error.response?.data?.validationErrors) {
        error.response.data.validationErrors.forEach((e: string) => {
          toast.error(e);
        });
      } else {
        toast.error(
          error.response?.data?.error ||
            "Network connection failed! Please try again later."
        );
      }
    }
  };

  const activate_Account = async (otp: string) => {
    try {
      const res = await axios.get(`/api/auth/activate-account`, {
        params: {
          token: otp,
        },
      });
      if (res.status !== 200) {
        toast.error(res.data.error);
        return;
      }
      toast.success("Your account activation is successful.!Please Login.");
      router.push("/");
    } catch (error: any) {
      toast.error(error.response?.data?.error);
    }
  };

  const book_add = async (obj: BookRequest) => {
    const formData = new FormData();

    if (obj.name) formData.append("name", obj.name);
    if (obj.title) formData.append("title", obj.title);
    if (obj.description) formData.append("description", obj.description);
    if (obj.author) formData.append("author", obj.author);
    if (obj.photo) formData.append("photo", obj.photo);
    if (obj.price) formData.append("price", obj.price);

    const token = localStorage.getItem("token");
    try {
      const res = await axios.post(`/api/admin/books`, formData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (res.status !== 202) {
        toast.error(res.data.error);
        return;
      }
      toast.success("Successfully created.!");
      router.push("/home");
    } catch (error: any) {
      console.log("ddf", error);
      if (error.response?.data?.validationErrors) {
        error.response.data.validationErrors.forEach((e: string) => {
          toast.error(e);
        });
      } else {
        toast.error(
          error.response?.data?.businessErrorDescription ||
            "Invalid Token. Please login again and try it back.!"
        );
      }
    }
  };

  const getAllBook = async () => {
    const token = localStorage.getItem("token");
    try {
      const res = await axios.get(`/api/admin/books`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (res.status !== 200) {
        toast.error("Something wrong.! Please try again.!");
        return;
      }
      bookList.value = res.data;
    } catch (error: any) {
      toast.error(
        error.response?.data?.businessErrorDescription ||
          "Invalid Token. Please login again and try it back.!"
      );
    }
  };

  const deleteBook = async (id: string) => {
    const token = localStorage.getItem("token");
    try {
      const res = await axios.delete(`/api/admin/books/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (res.status !== 200) {
        toast.error(res.data.error || "Something wrong.! Please try again.!");
        return;
      }
      bookList.value = bookList.value.filter((book: Book) => book.id !== id);
      toast.success("Deleted successfully.!");
      router.push("/home");
    } catch (error: any) {
      toast.error(
        error.response?.data?.businessErrorDescription ||
          "Invalid Token. Please login again and try it back.!"
      );
    }
  };

  const book_edit = async (obj: BookRequest) => {
    const token = localStorage.getItem("token");
    const formData = new FormData();

    if (obj.id) formData.append("id", obj.id);
    if (obj.name) formData.append("name", obj.name);
    if (obj.title) formData.append("title", obj.title);
    if (obj.description) formData.append("description", obj.description);
    if (obj.author) formData.append("author", obj.author);
    if (obj.photo) formData.append("photo", obj.photo);
    if (obj.price) formData.append("price", obj.price);
    try {
      const res = await axios.put(`/api/admin/books/edit/${obj.id}`, formData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (res.status !== 200) {
        toast.error(res.data.error || "Something wrong.! Please try again.!");
        return;
      }
      toast.success("Successfully updated,!");
      router.push("/home");
    } catch (error: any) {
      if (error.response?.data?.validationErrors) {
        error.response.data.validationErrors.forEach((e: string) => {
          toast.error(e);
        });
      } else {
        toast.error(
          error.response?.data?.businessErrorDescription ||
            "Invalid Token. Please login again and try it back.!"
        );
      }
    }
  };

  const orderedBook = async (obj: PaymentRequest) => {
    const token = localStorage.getItem("token");
    const formData = new FormData();

    if (obj.id) formData.append("id", obj.id);
    if (obj.bookId) formData.append("bookId", obj.bookId);
    if (obj.count) formData.append("count", obj.count);
    if (obj.price) formData.append("price", obj.price);
    if (obj.paymentBy) formData.append("paymentBy", obj.paymentBy.toString());
    if (obj.status) formData.append("status", obj.status.toString());
    if (obj.file) formData.append("file", obj.file);
    try {
      const res = await axios.post(`/api/user/payments`, formData, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (res.status !== 202) {
        toast.error(res.data.error || "Something wrong.! Please try again.!");
        return;
      }
      toast.success(
        "Your request is in progress, We will send you an email later.Thanks for choosing us.!"
      );
      router.push("/home");
    } catch (error: any) {
      console.log(error);
      if (error.response?.data?.validationErrors) {
        error.response.data.validationErrors.forEach((e: string) => {
          toast.error(e);
        });
      } else {
        toast.error(
          error.response?.data?.businessErrorDescription ||
            "Invalid Token. Please login again and try it back.!"
        );
      }
    }
  };

  const getInProgressPayment = async () => {
    const token = localStorage.getItem("token");
    try {
      const res = await axios.get(`/api/user/payments/in-progress`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (res.status !== 200) {
        toast.error("Something wrong.! Please try again.!");
        return;
      }
      paymentsList.value = res.data;
    } catch (error: any) {
      toast.error(
        error.response?.data?.businessErrorDescription ||
          "Invalid Token. Please login again and try it back.!"
      );
    }
  };

  const cancelRequest = async (id: string) => {
    const token = localStorage.getItem("token");
    try {
      const res = await axios.get(`/api/user/payments/request-cancel/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (res.status !== 200) {
        toast.error(res.data.error || "Something wrong.! Please try again.!");
        return;
      }
      paymentsList.value = paymentsList.value.filter(
        (payment: PaymentRequest) => payment.id !== id
      );
      toast.success(
        "Cancel successfully. and already sent an email for rejection!"
      );
    } catch (error: any) {
      toast.error(
        error.response?.data?.businessErrorDescription ||
          "Invalid Token. Please login again and try it back.!"
      );
    }
  };

  const acceptRequest = async (id: string) => {
    const token = localStorage.getItem("token");
    try {
      const res = await axios.get(`/api/user/payments/request-accept/${id}`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (res.status !== 200) {
        toast.error(res.data.error || "Something wrong.! Please try again.!");
        return;
      }
      paymentsList.value = paymentsList.value.filter(
        (payment: PaymentRequest) => payment.id !== id
      );
      toast.success(
        "Accept successfully. and already sent an email for acceptance!"
      );
    } catch (error: any) {
      toast.error(
        error.response?.data?.businessErrorDescription ||
          "Invalid Token. Please login again and try it back.!"
      );
    }
  };
  const logout = () => {
    role.value = "";
    localStorage.removeItem("token");
    toast.success("Logout successfully.!");
    router.push("/");
  };

  return {
    login,
    register,
    activate_Account,
    book_add,
    getAllBook,
    bookList,
    deleteBook,
    selectedBook,
    book_edit,
    orderedBook,
    paymentsList,
    getInProgressPayment,
    cancelRequest,
    acceptRequest,
    isAdmin,
    logout,
  };
});
