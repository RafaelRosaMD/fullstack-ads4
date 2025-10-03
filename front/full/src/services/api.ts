import axios from "axios";

const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL || "http://localhost:8080", 
  headers: { "Content-Type": "application/json" }
});

type LoginPayload = { email: string; senha: string };

// Exemplo de contrato esperado: { token: "jwt..." }
export async function login({ email, senha }: LoginPayload): Promise<string> {
  const { data } = await api.post("/auth/login", { email, senha });
  if (!data?.token) throw new Error("Resposta inválida do servidor (sem token).");
  return data.token as string;
}

export default api;