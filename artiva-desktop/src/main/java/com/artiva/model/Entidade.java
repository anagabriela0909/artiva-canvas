package com.artiva.model;

import java.util.Objects;
import java.util.UUID;

/**
 * Abstracao base de todas as entidades do dominio Artiva.
 * Garante identidade unica e comparacao consistente (encapsulamento do id).
 */
public abstract class Entidade {

    private final String id;

    protected Entidade() {
        this(UUID.randomUUID().toString());
    }

    protected Entidade(String id) {
        this.id = Objects.requireNonNull(id, "id nao pode ser nulo");
    }

    public String getId() {
        return id;
    }

    /** Rotulo curto usado nas telas. Cada subclasse define o seu (polimorfismo). */
    public abstract String getRotulo();

    @Override
    public boolean equals(Object outro) {
        if (this == outro) return true;
        if (!(outro instanceof Entidade)) return false;
        return id.equals(((Entidade) outro).id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return getRotulo();
    }
}
