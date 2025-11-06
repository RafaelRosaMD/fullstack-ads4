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
type LoginPayload = { email: string; senha: string };

// Espera resposta no formato { token: "jwt..." }
export async function login({ email, senha }: LoginPayload): Promise<string> {
  const { data } = await api.post("/auth/login", { email, senha });

  if (!data?.token) {
    throw new Error("Resposta inválida do servidor (sem token).");
  }

  // Armazena o token localmente
  localStorage.setItem("token", data.token);
  return data.token;
}

export default api;

// aqui tenho que junta a api usando a validacao pelo redux e nao local storage como está, berenice vai mandar