package com.artiva.model;

/** Cards da secao "Para voce aprender". */
public class ConteudoEducativo extends Entidade {

    private String titulo;
    private String resumo;
    private String tempoLeitura;
    private Dificuldade dificuldade;

    public ConteudoEducativo(String titulo, String resumo, String tempoLeitura, Dificuldade dificuldade) {
        this.titulo = titulo;
        this.resumo = resumo;
        this.tempoLeitura = tempoLeitura;
        this.dificuldade = dificuldade;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    public String getTempoLeitura() {
        return tempoLeitura;
    }

    public void setTempoLeitura(String tempoLeitura) {
        this.tempoLeitura = tempoLeitura;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    @Override
    public String getRotulo() {
        return titulo;
    }
}
