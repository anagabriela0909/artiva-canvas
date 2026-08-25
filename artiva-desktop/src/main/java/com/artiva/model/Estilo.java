package com.artiva.model;

public class Estilo extends Entidade {

    private String nome;
    private String descricao;
    private String periodo;
    private Dificuldade dificuldade;
    private String imagem;

    public Estilo(String nome, String periodo, String descricao, Dificuldade dificuldade, String imagem) {
        this.nome = nome;
        this.periodo = periodo;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.imagem = imagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    @Override
    public String getRotulo() {
        return nome;
    }
}
