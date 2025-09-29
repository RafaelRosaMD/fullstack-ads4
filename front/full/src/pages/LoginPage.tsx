import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { login } from "../services/api";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState("");
  const [loading, setLoading] = useState(false);
  const [erro, setErro] = useState<string | null>(null);
  const navigate = useNavigate();

  async function handleSubmit(e: React.FormEvent) {
    e.preventDefault();
    setErro(null);
    setLoading(true);
    try {
      const token = await login({ email, senha });
      // Armazenar token (ex.: localStorage) — simples para começo
      localStorage.setItem("token", token);
      navigate("/ordens"); // redireciona após login
    } catch (err: any) {
      setErro(err?.message || "Falha no login. Verifique as credenciais.");
    } finally {
      setLoading(false);
    }
  }

  return (
    <main className="app-main">
      <div className="container d-flex justify-content-center align-items-center" style={{ minHeight: "70vh" }}>
        <div className="login-card w-100">
          <h1 className="h4 mb-3 text-center">Acessar conta</h1>


          <form onSubmit={handleSubmit} noValidate>
            <div className="mb-3">
              <label htmlFor="email" className="form-label" style={{ marginLeft: "12px" }}>E-mail</label>
              <input
                id="email"
                type="email"
                className="form-control"
                placeholder="seu@email.com"
                autoComplete="username"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            
            </div>

            <div className="mb-3">
              <label htmlFor="senha" className="form-label" style={{ marginLeft: "12px" }}>Senha</label>
              <input
                id="senha"
                type="password"
                className="form-control"
                placeholder="Digite sua senha"
                autoComplete="current-password"
                value={senha}
                onChange={(e) => setSenha(e.target.value)}
                required
              />
            </div>

            {erro && (
              <div className="alert alert-danger py-2" role="alert">
                {erro}
              </div>
            )}

            <button type="submit" className="btn btn-dark w-100" disabled={loading}>
              {loading ? "Entrando..." : "Entrar"}
            </button>
          </form>

          <div className="mt-3 text-center">
            <a href="#" className="link-secondary" onClick={(e) => e.preventDefault()}>
              Esqueci minha senha
            </a>
          </div>
        </div>
      </div>
    </main>
  );
}

export default LoginPage;