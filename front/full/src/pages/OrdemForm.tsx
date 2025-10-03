// src/pages/OrdemForm.tsx
import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { buscarOrdem, criarOrdem, atualizarOrdem } from "../services/ordens";
import type { OrdemCreate, OrdemUpdate } from "../types";

export default function OrdemForm() {
  const { id } = useParams();                  // string | undefined
  const editMode = Boolean(id);
  const navigate = useNavigate();

  // Como os campos são os mesmos, usamos um shape comum
  type FormState = { cliente: string; descricaoDefeito: string };

  const [form, setForm] = useState<FormState>({
    cliente: "",
    descricaoDefeito: "",
  });
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState<string | null>(null);

  useEffect(() => {
    async function carregar() {
      if (!editMode || !id) return;
      try {
        setLoading(true);
        const os = await buscarOrdem(Number(id));
        setForm({ cliente: os.cliente, descricaoDefeito: os.descricaoDefeito });
      } catch (err: any) {
        setErro(err?.response?.data?.message || "Falha ao carregar OS.");
      } finally {
        setLoading(false);
      }
    }
    carregar();
  }, [editMode, id]);

  function onChange(e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
    const { name, value } = e.target;
    setForm(prev => ({ ...prev, [name]: value }));
  }

  async function onSubmit(e: React.FormEvent) {
    e.preventDefault();
    setErro(null);
    setLoading(true);
    try {
      if (editMode && id) {
        const payload: OrdemUpdate = { ...form };
        await atualizarOrdem(Number(id), payload);
      } else {
        const payload: OrdemCreate = { ...form };
        await criarOrdem(payload);
      }
      navigate("/ordens");
    } catch (err: any) {
      setErro(err?.response?.data?.message || "Erro ao salvar OS.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="app-main">
      <div className="container py-4" style={{ maxWidth: 720 }}>
        <h1 className="h4 mb-3">{editMode ? "Editar OS" : "Nova OS"}</h1>

        {erro && <div className="alert alert-danger">{erro}</div>}

        <form onSubmit={onSubmit}>
          <div className="mb-3">
            <label htmlFor="cliente" className="form-label">Cliente</label>
            <input
              id="cliente"
              name="cliente"
              className="form-control"
              value={form.cliente}
              onChange={onChange}
              required
            />
          </div>

          <div className="mb-3">
            <label htmlFor="descricaoDefeito" className="form-label">Descrição do defeito</label>
            <textarea
              id="descricaoDefeito"
              name="descricaoDefeito"
              className="form-control"
              rows={4}
              value={form.descricaoDefeito}
              onChange={onChange}
              required
            />
          </div>

          <div className="d-flex gap-2">
            <button className="btn btn-dark" type="submit" disabled={loading}>
              {loading ? "Salvando..." : "Salvar"}
            </button>
            <button className="btn btn-outline-secondary" type="button" onClick={() => navigate("/ordens")} disabled={loading}>
              Cancelar
            </button>
          </div>
        </form>
      </div>
    </main>
  );
}