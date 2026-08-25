package com.artiva.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Entidade central do acervo. Associa-se a Autor, Estilo, Tecnica e Material.
 */
public class Obra extends Entidade {

    private String titulo;
    private Autor autor;
    private int ano;
    private Estilo estilo;
    private Tecnica tecnica;
    private Dificuldade dificuldade;
    private String descricao;
    private String contextoHistorico;
    private String curiosidade;
    private String imagem;
    private boolean novidade;
    private int totalFavoritos;
    private final List<Material> materiais = new ArrayList<>();

    public Obra(String titulo, Autor autor, int ano, Estilo estilo, Tecnica tecnica,
                Dificuldade dificuldade, String imagem) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
        this.estilo = estilo;
        this.tecnica = tecnica;
        this.dificuldade = dificuldade;
        this.imagem = imagem;
        this.descricao = "";
        this.contextoHistorico = "";
        this.curiosidade = "";
    }

    public void adicionarMaterial(Material material) {
        if (material != null && !materiais.contains(material)) {
            materiais.add(material);
        }
    }

    public List<Material> getMateriais() {
        if (materiais.isEmpty() && tecnica != null) {
            return tecnica.getMateriais();
        }
        return Collections.unmodifiableList(materiais);
    }

    public void registrarFavorito() {
        totalFavoritos++;
    }

    public void removerFavorito() {
        if (totalFavoritos > 0) {
            totalFavoritos--;
        }
    }

    /** Texto usado na busca textual do software. */
    public String textoPesquisavel() {
        return String.join(" ",
                titulo,
                autor == null ? "" : autor.getNome(),
                estilo == null ? "" : estilo.getNome(),
                tecnica == null ? "" : tecnica.getNome(),
                dificuldade == null ? "" : dificuldade.getRotulo(),
                descricao).toLowerCase();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public Estilo getEstilo() {
        return estilo;
    }

    public void setEstilo(Estilo estilo) {
        this.estilo = estilo;
    }

    public Tecnica getTecnica() {
        return tecnica;
    }

    public void setTecnica(Tecnica tecnica) {
        this.tecnica = tecnica;
    }

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContextoHistorico() {
        return contextoHistorico;
    }

    public void setContextoHistorico(String contextoHistorico) {
        this.contextoHistorico = contextoHistorico;
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

    public boolean isNovidade() {
        return novidade;
    }

    public void setNovidade(boolean novidade) {
        this.novidade = novidade;
    }

    public int getTotalFavoritos() {
        return totalFavoritos;
    }

    public void setTotalFavoritos(int totalFavoritos) {
        this.totalFavoritos = Math.max(0, totalFavoritos);
    }

    @Override
    public String getRotulo() {
        return titulo;
    }
}
