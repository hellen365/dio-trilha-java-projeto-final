package com.vhdnasc.produto.domain.usecase;

import java.util.List;

public interface CrudUseCase<ID, T> {

    List<T> findAll();

    T findById(ID id);

    T create(T entity);

    T update(ID id, T entity);

    void delete(ID id);
}
