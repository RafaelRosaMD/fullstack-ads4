package com.senac.full.infra.ordem.jpa;

import com.senac.full.domain.ordem.OrdemServico;
import com.senac.full.domain.ordem.StatusOrdemServico;
import jakarta.persistence.*;

@Entity
@Table(name = "ordem_servico")
public class OrdemServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String cliente;

    @Column(nullable = false, length = 1000)
    private String descricaoDefeito;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatusOrdemServico status;

    public OrdemServicoEntity() {
    }

    public OrdemServicoEntity(Long id, String cliente, String descricaoDefeito, StatusOrdemServico status) {
        this.id = id;
        this.cliente = cliente;
        this.descricaoDefeito = descricaoDefeito;
        this.status = status;
    }

    public static OrdemServicoEntity fromDomain(OrdemServico os) {
        return new OrdemServicoEntity(
                os.getId(),
                os.getCliente(),
                os.getDescricaoDefeito(),
                os.getStatus()
        );
    }

    public OrdemServico toDomain() {
        return new OrdemServico(
                this.id,
                this.cliente,
                this.descricaoDefeito,
                this.status
        );
    }

    // getters e setters do JPA

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

    public void setId(Long id) {
        this.id = id;
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