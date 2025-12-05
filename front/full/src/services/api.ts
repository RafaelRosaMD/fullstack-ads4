// src/services/api.ts
import axios from "axios";
import { store } from "../redux/store";
import { logout } from "../redux/authSlice";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080",
  headers: { "Content-Type": "application/json" },
});

// ---------- INTERCEPTOR DE REQUISIÇÃO ----------
// Token vem EXCLUSIVAMENTE da store do Redux
api.interceptors.request.use((config) => {
  const state = store.getState();
  const token = state.auth.token; // 👈 pega da Redux store

  if (token) {
    config.headers = config.headers ?? {};
    config.headers.Authorization = `Bearer ${token}`;
  }

  return config;
});

// ---------- INTERCEPTOR DE RESPOSTA ----------
// Se der 401 -> faz logout no Redux
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      store.dispatch(logout());
      // opcional: redirecionar pra login (se quiser algo extra visual)
      // window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

// ----------------- LOGIN -----------------
export type LoginPayload = { email: string; senha: string };

export type LoginResponse = {
  token: string;
  id: number;
  nome: string;
  email: string;
  role: string;
};

export async function login({
  email,
  senha,
}: LoginPayload): Promise<LoginResponse> {
  const { data } = await api.post<LoginResponse>("/auth/login", { email, senha });

  if (!data?.token) {
    throw new Error("Resposta inválida do servidor (sem token).");
  }

  // ❌ NÃO mexe com localStorage aqui.
  // Quem guarda token + usuário é o Redux (na LoginPage).

  return data;
}

export default api;