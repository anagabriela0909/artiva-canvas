package com.artiva.model;

import java.time.LocalDateTime;

/** Associacao entre um usuario e uma obra salva. */
public class Favorito extends Entidade {

    private final Usuario usuario;
    private final Obra obra;
    private final LocalDateTime salvoEm;

    public Favorito(Usuario usuario, Obra obra) {
        this.usuario = usuario;
        this.obra = obra;
        this.salvoEm = LocalDateTime.now();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Obra getObra() {
        return obra;
    }

    public LocalDateTime getSalvoEm() {
        return salvoEm;
    }

    @Override
    public String getRotulo() {
        return obra.getTitulo() + " - " + usuario.getNome();
    }
}
