// src/services/api.ts
import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080",
  headers: { "Content-Type": "application/json" },
});

// Interceptor: adiciona o token automaticamente no header Authorization
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers = config.headers ?? {};
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

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

  // salva token e id do usuário para uso no resto do app
  localStorage.setItem("token", data.token);
  localStorage.setItem("usuarioId", String(data.id));

  return data;
}

export default api;