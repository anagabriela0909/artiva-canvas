package com.artiva.model;

/** Curador do acervo: herda tudo de Usuario e amplia as permissoes. */
public class Administrador extends Usuario {

    private String setor;

    public Administrador(String nome, String usuario, String senha, String setor) {
        super(nome, usuario, senha);
        this.setor = setor;
    }

    @Override
    public String getPapel() {
        return "Curador";
    }

    @Override
    public boolean podeGerenciarAcervo() {
        return true;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }
}
