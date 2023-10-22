package com.vhdnasc.produto.domain.dto.produto;

import com.vhdnasc.produto.domain.dto.tipo.TipoRequest;
import lombok.Data;

@Data
public class ProdutoRequest {

    private String nome;

    private Integer quantidade;

    private Double valor;

    private TipoRequest tipo;

}
