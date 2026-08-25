package com.artiva.model;

public class Autor extends Entidade {

    private String nome;
    private String periodo;
    private String nacionalidade;
    private String biografia;
    private String curiosidade;
    private String imagem;

    public Autor(String nome, String periodo, String nacionalidade, String biografia,
                 String curiosidade, String imagem) {
        this.nome = nome;
        this.periodo = periodo;
        this.nacionalidade = nacionalidade;
        this.biografia = biografia;
        this.curiosidade = curiosidade;
        this.imagem = imagem;
    }

    public Autor(String nome, String periodo) {
        this(nome, periodo, "", "", "", null);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getNacionalidade() {
        return nacionalidade;
    }

    public void setNacionalidade(String nacionalidade) {
        this.nacionalidade = nacionalidade;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getCuriosidade() {
        return curiosidade;
    }

    public void setCuriosidade(String curiosidade) {
        this.curiosidade = curiosidade;
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
