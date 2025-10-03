export type StatusOrdemServico = "ABERTA" | "EM_EXECUCAO" | "FINALIZADA";

export interface OrdemServico {
  id: number;
  cliente: string;
  descricaoDefeito: string;
  status: StatusOrdemServico;
}

export interface OrdemCreate {
  cliente: string;
  descricaoDefeito: string;
}

export interface OrdemUpdate {
  cliente: string;
  descricaoDefeito: string;
}