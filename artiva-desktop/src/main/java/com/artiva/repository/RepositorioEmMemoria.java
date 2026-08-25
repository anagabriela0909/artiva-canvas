package com.artiva.repository;

import com.artiva.model.Entidade;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/** Implementacao em memoria (dados mockados) do contrato Repositorio. */
public class RepositorioEmMemoria<T extends Entidade> implements Repositorio<T> {

    private final Map<String, T> registros = new LinkedHashMap<>();

    @Override
    public List<T> listarTodos() {
        return new ArrayList<>(registros.values());
    }

    @Override
    public Optional<T> buscarPorId(String id) {
        return Optional.ofNullable(registros.get(id));
    }

    @Override
    public T salvar(T entidade) {
        registros.put(entidade.getId(), entidade);
        return entidade;
    }

    @Override
    public boolean remover(String id) {
        return registros.remove(id) != null;
    }

    @Override
    public long contar() {
        return registros.size();
    }
}
