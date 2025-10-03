import { Routes, Route, Navigate } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import LoginPage from "./pages/LoginPage";
import OrdensPage from "./pages/OrdensPage";
import OrdensList from "./pages/OrdensList";
import OrdemForm from "./pages/OrdemForm";




export default function App() { 
  return (
    <div className="app-shell">
      <Header />
      <Routes>
        <Route path="/" element={<Navigate to="/login" replace />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="*" element={<Navigate to="/login" replace />} />
        <Route path="/ordens" element={<OrdensList />} />
        <Route path="/ordens/novo" element={<OrdemForm />} />
        <Route path="/ordens/:id/editar" element={<OrdemForm />} />
      </Routes>
      <Footer />
    </div>
  );
}