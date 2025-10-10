package com.senac.full.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ordem_servico")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class OrdemServico {

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
}