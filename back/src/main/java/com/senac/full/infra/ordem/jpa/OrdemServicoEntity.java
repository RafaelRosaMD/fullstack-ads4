
package com.senac.full.infra.ordem.jpa;

import com.senac.full.domain.ordem.OrdemServico;
import com.senac.full.domain.ordem.StatusOrdemServico;
import com.senac.full.infra.usuario.jpa.UsuarioEntity;
import jakarta.persistence.*;

@Entity
@Table(name = "ordem_servico")
public class OrdemServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;

    @Column(length = 1000)
    private String descricaoDefeito;

    @Enumerated(EnumType.STRING)
    private StatusOrdemServico status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioEntity usuario;   // <-- ManyToOne aqui

    public OrdemServicoEntity() {
    }

    // construtor opcional se quiser

    // ========= MAPEAMENTO DOMAIN ↔ ENTITY =========

    public static OrdemServicoEntity fromDomain(OrdemServico os) {
        OrdemServicoEntity e = new OrdemServicoEntity();
        e.id = os.getId();
        e.cliente = os.getCliente();
        e.descricaoDefeito = os.getDescricaoDefeito();
        e.status = os.getStatus();

        // criamos um "proxy" de UsuarioEntity só com o id preenchido
        if (os.getUsuarioId() != null) {
            UsuarioEntity u = new UsuarioEntity();
            u.setId(os.getUsuarioId());
            e.usuario = u;
        } else {
            e.usuario = null;
        }

        return e;
    }

    public OrdemServico toDomain() {
        Long usuarioId = (usuario != null ? usuario.getId() : null);

        return new OrdemServico(
                this.id,
                this.cliente,
                this.descricaoDefeito,
                this.status,
                usuarioId
        );
    }

    // ========= GETTERS / SETTERS =========

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

    public UsuarioEntity getUsuario() {
        return usuario;
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

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }
}