package com.vhdnasc.produto.infra.db.repository;

import com.vhdnasc.produto.infra.db.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
    Produto findByNome(String nome);
}
