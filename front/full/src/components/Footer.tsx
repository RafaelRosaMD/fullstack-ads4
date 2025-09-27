function Footer() {
  return (
    <footer className="app-footer">
      <div className="container py-3 text-center text-muted" style={{ fontSize: 14 }}>
        © {new Date().getFullYear()} Rafael Rosa — Todos os direitos reservados
      </div>
    </footer>
  );
}

export default Footer;