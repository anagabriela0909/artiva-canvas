package com.artiva.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Tecnica extends Entidade {

    private String nome;
    private String descricao;
    private Dificuldade dificuldade;
    private final List<Material> materiais = new ArrayList<>();

    public Tecnica(String nome, String descricao, Dificuldade dificuldade, Material... materiais) {
        this.nome = nome;
        this.descricao = descricao;
        this.dificuldade = dificuldade;
        this.materiais.addAll(Arrays.asList(materiais));
    }

    public void adicionarMaterial(Material material) {
        if (material != null && !materiais.contains(material)) {
            materiais.add(material);
        }
    }

    public List<Material> getMateriais() {
        return Collections.unmodifiableList(materiais);
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

    public Dificuldade getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(Dificuldade dificuldade) {
        this.dificuldade = dificuldade;
    }

    @Override
    public String getRotulo() {
        return nome;
    }
}
