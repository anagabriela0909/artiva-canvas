package com.artiva.repository;

import com.artiva.model.Entidade;

import java.util.List;
import java.util.Optional;

/**
 * Contrato generico de persistencia. A implementacao atual e em memoria,
 * mas basta criar uma implementacao JDBC/JPA para ligar o software a um banco.
 */
public interface Repositorio<T extends Entidade> {

    List<T> listarTodos();

    Optional<T> buscarPorId(String id);

    T salvar(T entidade);

    boolean remover(String id);

    long contar();
}
