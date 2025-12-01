// src/services/api.ts
import axios from "axios";
import { store } from "../redux/store";  
import { loginSucesso, logout } from "../redux/authSlice";  

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080",
  headers: { "Content-Type": "application/json" },
});

// -------- INTERCEPTOR DE REQUISIÇÃO --------
// Agora busca o token da store do Redux
api.interceptors.request.use((config) => {
  const state = store.getState();
  const tokenFromRedux = state.auth.token;
  const tokenFromStorage = localStorage.getItem("token");

  const token = tokenFromRedux || tokenFromStorage;

  if (token) {
    config.headers = config.headers ?? {};
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// -------- INTERCEPTOR DE RESPOSTA --------
// Se der 401 (token inválido/expirado) -> logout no Redux
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      store.dispatch(logout());
      // opcional: redirecionar para login:
      // window.location.href = "/login";
    }
    return Promise.reject(error);
  }
);

// --------------------- LOGIN ---------------------
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

  // monta objeto usuário para o Redux
  const usuario = {
    id: data.id,
    nome: data.nome,
    email: data.email,
  };

  // Atualiza Redux: autenticado + token + dados do usuário
  store.dispatch(
    loginSucesso({
      usuario,
      token: data.token,
    })
  );

  // Se quiser persistir para reload de página:
  localStorage.setItem("token", data.token);
  localStorage.setItem("usuarioId", String(data.id));

  return data;
}

export default api;