package com.vhdnasc.produto.domain.dto.produto;

import com.vhdnasc.produto.domain.dto.tipo.TIpoResponse;
import lombok.Data;

@Data
public class ProdutoResponse {

    private String nome;

    private Integer quantidade;

    private Double valor;

    private TIpoResponse tipo;
}
