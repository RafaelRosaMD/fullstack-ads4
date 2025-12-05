import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import type { RootState, AppDispatch } from "../redux/store";
import { 
  listarOrdens, 
  excluirOrdem, 
  iniciarServico, 
  finalizarServico 
} from "../services/ordens";
import type { OrdemServico } from "../types";
import { setFiltroStatus, type StatusFiltro } from "../redux/ordensUiSlice";

function BadgeStatus({ status }: { status: OrdemServico["status"] }) {
  const map: Record<OrdemServico["status"], string> = {
    ABERTA: "secondary",
    EM_EXECUCAO: "warning",
    FINALIZADA: "success",
  };
  return <span className={`badge text-bg-${map[status]}`}>{status}</span>;
}

export default function OrdensList() {
  const [dados, setDados] = useState<OrdemServico[]>([]);
  const [loading, setLoading] = useState(true);
  const [erro, setErro] = useState<string | null>(null);
  const [operandoId, setOperandoId] = useState<number | null>(null);

  const dispatch = useDispatch<AppDispatch>();
  const filtroStatus = useSelector(
    (state: RootState) => state.ordensUi.filtroStatus
  );

  const ordensFiltradas = dados.filter((o) =>
    filtroStatus === "TODAS" ? true : o.status === filtroStatus
  );

  async function carregar() {
    try {
      setLoading(true);
      setErro(null);
      const res = await listarOrdens();
      setDados(res);
    } catch (err: any) {
      setErro(err?.response?.data?.message || "Falha ao carregar ordens.");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    carregar();
  }, []);

  async function handleExcluir(id: number) {
    if (!confirm("Excluir esta ordem?")) return;
    try {
      setOperandoId(id);
      await excluirOrdem(id);
      await carregar();
    } catch (err: any) {
      alert(err?.response?.data?.message || "Não foi possível excluir.");
    } finally {
      setOperandoId(null);
    }
  }

  async function handleIniciar(id: number) {
    try {
      setOperandoId(id);
      await iniciarServico(id);
      await carregar();
    } catch (err: any) {
      alert(err?.response?.data?.message || "Não foi possível iniciar.");
    } finally {
      setOperandoId(null);
    }
  }

  async function handleFinalizar(id: number) {
    try {
      setOperandoId(id);
      await finalizarServico(id);
      await carregar();
    } catch (err: any) {
      alert(err?.response?.data?.message || "Não foi possível finalizar.");
    } finally {
      setOperandoId(null);
    }
  }

  return (
    <main className="app-main">
      <div className="container py-4">
        <div className="d-flex justify-content-between align-items-center mb-3">
          <h1 className="h4 m-0">Ordens de Serviço</h1>
          <Link className="btn btn-dark" to="/ordens/novo">
            Nova OS
          </Link>
        </div>

        {}
        <div className="mb-3">
          <label className="form-label">Filtrar por status</label>
          <select
            className="form-select"
            value={filtroStatus}
            onChange={(e) =>
              dispatch(setFiltroStatus(e.target.value as StatusFiltro))
            }
          >
            <option value="TODAS">Todas</option>
            <option value="ABERTA">Abertas</option>
            <option value="EM_EXECUCAO">Em execução</option>
            <option value="FINALIZADA">Finalizadas</option>
          </select>
        </div>

        {erro && <div className="alert alert-danger">{erro}</div>}
        {loading ? (
          <div className="text-muted">Carregando...</div>
        ) : ordensFiltradas.length === 0 ? (
          <div className="text-muted">Nenhuma OS encontrada para o filtro atual.</div>
        ) : (
          <div className="table-responsive">
            <table className="table align-middle">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Cliente</th>
                  <th>Defeito</th>
                  <th>Status</th>
                  <th className="text-end">Ações</th>
                </tr>
              </thead>
              <tbody>
                {ordensFiltradas.map((o) => (
                  <tr key={o.id}>
                    <td>{o.id}</td>
                    <td>{o.cliente}</td>
                    <td className="text-muted">{o.descricaoDefeito}</td>
                    <td>
                      <BadgeStatus status={o.status} />
                    </td>
                    <td className="text-end">
                      <div className="btn-group">
                        <Link
                          className="btn btn-outline-secondary btn-sm"
                          to={`/ordens/${o.id}/editar`}
                        >
                          Editar
                        </Link>

                        {o.status === "ABERTA" && (
                          <button
                            className="btn btn-outline-primary btn-sm"
                            onClick={() => handleIniciar(o.id)}
                            disabled={operandoId === o.id}
                          >
                            Iniciar
                          </button>
                        )}
                        {o.status === "EM_EXECUCAO" && (
                          <button
                            className="btn btn-outline-success btn-sm"
                            onClick={() => handleFinalizar(o.id)}
                            disabled={operandoId === o.id}
                          >
                            Finalizar
                          </button>
                        )}

                        <button
                          className="btn btn-outline-danger btn-sm"
                          onClick={() => handleExcluir(o.id)}
                          disabled={operandoId === o.id}
                        >
                          Excluir
                        </button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>
    </main>
  );
}