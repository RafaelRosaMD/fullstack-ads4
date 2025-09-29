import { Routes, Route, Navigate } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import LoginPage from "./pages/LoginPage";
import Teste from "./components/Teste";


function OrdensPage() {
  return (
    <main className="app-main">
      <div className="container py-4">
        <h1 className="h4 mb-3">Ordens</h1>
        <p className="text-muted">Página placeholder — em breve sua listagem de ordens.</p>
      </div>
    </main>
  );
}


export default function App() {
  return (
    <div className="app-shell">
      <Header />
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/ordens" element={<OrdensPage />} />
        <Route path="/teste" element={<Teste />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
      </Routes>
      <Footer />
    </div>
  );
}