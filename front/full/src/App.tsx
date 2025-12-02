import { Routes, Route, Navigate } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import LoginPage from "./pages/LoginPage";
import OrdensList from "./pages/OrdensList";
import OrdemForm from "./pages/OrdemForm";

import PrivateRoute from "./routes/PrivateRoute";

export default function App() { 
  return (
    <div className="app-shell">
      <Header />

      <Routes>
        {/* Redireciona raiz para login */}
        <Route path="/" element={<Navigate to="/login" replace />} />

        {/* Login é público */}
        <Route path="/login" element={<LoginPage />} />

        {/* Rotas protegidas (exige autenticação) */}
        <Route 
          path="/ordens" 
          element={
            <PrivateRoute>
              <OrdensList />
            </PrivateRoute>
          } 
        />

        <Route 
          path="/ordens/novo" 
          element={
            <PrivateRoute>
              <OrdemForm />
            </PrivateRoute>
          } 
        />

        <Route 
          path="/ordens/:id/editar" 
          element={
            <PrivateRoute>
              <OrdemForm />
            </PrivateRoute>
          } 
        />

        {/* Qualquer rota inválida manda para login */}
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>

      <Footer />
    </div>
  );
}