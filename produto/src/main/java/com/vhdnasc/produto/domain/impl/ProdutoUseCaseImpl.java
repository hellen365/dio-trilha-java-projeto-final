package com.vhdnasc.produto.domain.impl;

import com.vhdnasc.produto.domain.exception.BusinessException;
import com.vhdnasc.produto.domain.exception.ObjectNotFoundException;
import com.vhdnasc.produto.domain.usecase.ProdutoUseCase;
import com.vhdnasc.produto.infra.db.model.Produto;
import com.vhdnasc.produto.infra.db.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import static java.util.Optional.ofNullable;

public class ProdutoUseCaseImpl implements ProdutoUseCase {

    private static final Long serialVersionUID = 1L;

    private ProdutoRepository produtoRepository;

    public ProdutoUseCaseImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    @Override
    public List<Produto> findAll() {
        return this.produtoRepository.findAll();
    }

    @Override
    public Produto findById(Long id) {
        return this.produtoRepository.findById(id).orElseThrow(ObjectNotFoundException::new);
    }

    @Override
    public Produto create(Produto produto) {
        ofNullable(produto).orElseThrow(() -> new BusinessException("O produto a ser criado não deve ser nulo."));
        ofNullable(produto.getTipo()).orElseThrow(() -> new BusinessException("O tipo de produto não deve ser nulo."));


        this.validateChangeableId(produto.getId(), "created");
        if (produtoRepository.findByNome(produto.getNome()) != null) {
            throw new BusinessException(".Este produto já existe");
        }

        return this.produtoRepository.save(produto);
    }

    @Override
    public Produto update(Long id, Produto produto) {
        this.validateChangeableId(id, "updated");
        Produto dbProduto = this.findById(id);
        if (!dbProduto.getId().equals(produto.getId())) {
            throw new BusinessException("Os IDs de atualização devem ser iguais.");
        }

        setPropertyIfNotNull(produto.getNome(), dbProduto::setNome);
        setPropertyIfNotNull(produto.getQuantidade(), dbProduto::setQuantidade);
        setPropertyIfNotNull(produto.getValor(), dbProduto::setValor);
        setPropertyIfNotNull(produto.getTipo().getNome(), dbProduto.getTipo()::setNome);

        return this.produtoRepository.save(dbProduto);
    }

    @Override
    public void delete(Long id) {
        this.validateChangeableId(id, "deleted");
        Produto dbProduto = this.findById(id);
        this.produtoRepository.delete(dbProduto);
    }

    private void validateChangeableId(Long id, String operation) {
        if (serialVersionUID.equals(id)) {
            throw new BusinessException("O produto com ID "+serialVersionUID +"não pode ser "+operation);
        }
    }

    private <T> void setPropertyIfNotNull(T value, Consumer<T> setter){
        Optional.ofNullable(value).ifPresent(setter);
    }
}
