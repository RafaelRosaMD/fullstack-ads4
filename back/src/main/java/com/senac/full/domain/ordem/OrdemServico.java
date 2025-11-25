package com.senac.full.domain.ordem;

public class OrdemServico {

    private Long id;
    private String cliente;
    private String descricaoDefeito;
    private com.senac.full.domain.ordem.StatusOrdemServico status;

    public OrdemServico(Long id, String cliente, String descricaoDefeito, StatusOrdemServico status) {
        this.id = cliente == null ? null : id;
        this.cliente = cliente;
        this.descricaoDefeito = descricaoDefeito;
        this.status = status;
    }

    public static OrdemServico nova(String cliente, String descricaoDefeito) {
        return new OrdemServico(null, cliente, descricaoDefeito, StatusOrdemServico.ABERTA);
    }

    // getters e setters mínimos
    public Long getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getDescricaoDefeito() {
        return descricaoDefeito;
    }

    public StatusOrdemServico getStatus() {
        return status;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setDescricaoDefeito(String descricaoDefeito) {
        this.descricaoDefeito = descricaoDefeito;
    }

    public void setStatus(StatusOrdemServico status) {
        this.status = status;
    }
}