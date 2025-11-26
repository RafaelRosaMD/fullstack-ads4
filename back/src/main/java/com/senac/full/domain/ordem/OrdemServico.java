package com.senac.full.domain.ordem;

public class OrdemServico {

    private Long id;
    private String cliente;
    private String descricaoDefeito;
    private StatusOrdemServico status;
    private Long usuarioId;

    public OrdemServico(Long id,
                        String cliente,
                        String descricaoDefeito,
                        StatusOrdemServico status,
                        Long usuarioId) {
        this.id = id;
        this.cliente = cliente;
        this.descricaoDefeito = descricaoDefeito;
        this.status = status;
        this.usuarioId = usuarioId;
    }

    public static OrdemServico nova(String cliente,
                                    String descricaoDefeito,
                                    Long usuarioId) {
        return new OrdemServico(
                null,
                cliente,
                descricaoDefeito,
                StatusOrdemServico.ABERTA,
                usuarioId
        );
    }

    public Long getId() { return id; }
    public String getCliente() { return cliente; }
    public String getDescricaoDefeito() { return descricaoDefeito; }
    public StatusOrdemServico getStatus() { return status; }
    public Long getUsuarioId() { return usuarioId; }

    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setDescricaoDefeito(String descricaoDefeito) { this.descricaoDefeito = descricaoDefeito; }
    public void setStatus(StatusOrdemServico status) { this.status = status; }
}