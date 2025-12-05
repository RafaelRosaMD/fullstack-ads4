export type StatusOrdemServico = "ABERTA" | "EM_EXECUCAO" | "FINALIZADA";

export interface OrdemServico {
  id: number;
  cliente: string;
  descricaoDefeito: string;
  status: StatusOrdemServico;
}

export type OrdemCreate = {
  cliente: string;
  descricaoDefeito: string;
  usuarioId: number;   
}

export type OrdemUpdate = {
  cliente: string;
  descricaoDefeito: string;
  usuarioId: number;   
}