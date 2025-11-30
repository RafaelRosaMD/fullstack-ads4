// src/pages/LoginPage.tsx
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import type { AppDispatch } from "../redux/store";
import { loginSucesso } from "../redux/authSlice";
import { login } from "../services/api";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [erro, setErro] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();
  const dispatch = useDispatch<AppDispatch>();

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setErro(null);
    setLoading(true);

    try {
      // chama a API (usa api.ts)
      const data = await login({ email, senha });
      // data: { token, id, nome, email, role }

      // atualiza Redux
      dispatch(
        loginSucesso({
          token: data.token,
          usuario: {
            id: data.id,
            nome: data.nome,
            email: data.email,
          },
        })
      );

      // token e usuarioId já foram salvos no localStorage dentro do login()
      // via api.ts

      navigate("/ordens");
    } catch (err: any) {
      setErro("Usuário ou senha inválidos.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="app-main">
      <div className="container py-4" style={{ maxWidth: 480 }}>
        <h1 className="h4 mb-3">Login</h1>

        {erro && <div className="alert alert-danger">{erro}</div>}

        <form onSubmit={handleSubmit}>
          <div className="mb-3">
            <label className="form-label" htmlFor="email">
              E-mail
            </label>
            <input
              id="email"
              type="email"
              className="form-control"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </div>

          <div className="mb-3">
            <label className="form-label" htmlFor="senha">
              Senha
            </label>
            <input
              id="senha"
              type="password"
              className="form-control"
              value={senha}
              onChange={(e) => setSenha(e.target.value)}
              required
            />
          </div>

          <button className="btn btn-dark" type="submit" disabled={loading}>
            {loading ? "Entrando..." : "Entrar"}
          </button>
        </form>
      </div>
    </main>
  );
}