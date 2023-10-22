package com.vhdnasc.produto.domain.mapper;

import com.vhdnasc.produto.domain.dto.produto.ProdutoRequest;
import com.vhdnasc.produto.domain.dto.produto.ProdutoResponse;
import com.vhdnasc.produto.infra.db.model.Produto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProdutorMapper {

    Produto toEntity(ProdutoRequest request);
    ProdutoResponse toResponse(Produto produtos);
    List<ProdutoResponse> toListResponse(List<Produto> produtos);
}
