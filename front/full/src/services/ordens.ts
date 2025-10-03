import api from "./api";
import type { OrdemServico, OrdemCreate, OrdemUpdate } from "../types";

export async function listarOrdens(): Promise<OrdemServico[]> {
  const { data } = await api.get("/ordens");
  return data;
}

export async function buscarOrdem(id: number): Promise<OrdemServico> {
  const { data } = await api.get(`/ordens/${id}`);
  return data;
}

export async function criarOrdem(payload: OrdemCreate): Promise<OrdemServico> {
  const { data } = await api.post("/ordens", payload);
  return data;
}

export async function atualizarOrdem(id: number, payload: OrdemUpdate): Promise<OrdemServico> {
  const { data } = await api.put(`/ordens/${id}`, payload);
  return data;
}

export async function excluirOrdem(id: number): Promise<void> {
  await api.delete(`/ordens/${id}`);
}

export async function iniciarServico(id: number): Promise<OrdemServico> {
  const { data } = await api.post(`/ordens/${id}/iniciar`);
  return data;
}

export async function finalizarServico(id: number): Promise<OrdemServico> {
  const { data } = await api.post(`/ordens/${id}/finalizar`);
  return data;
}