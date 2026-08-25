package com.artiva.model;

/** Usuario comum: explora o acervo e salva favoritos. */
public class Visitante extends Usuario {

    public Visitante(String nome, String usuario, String senha) {
        super(nome, usuario, senha);
    }

    @Override
    public String getPapel() {
        return "Explorador";
    }
}
