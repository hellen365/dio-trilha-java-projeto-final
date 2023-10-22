package com.vhdnasc.produto.infra.db.model;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "tb_produtos")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Integer quantidade;

    private Double valor;

    @OneToOne(cascade = CascadeType.ALL)
    private Tipo tipo;
}
