package com.artiva.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe abstrata de usuario. Concentra dados e comportamentos comuns
 * (encapsulamento) e delega a subclasses o papel e as permissoes (polimorfismo).
 */
public abstract class Usuario extends Entidade {

    private String nome;
    private String usuario;
    private String senha;
    private final List<Favorito> favoritos = new ArrayList<>();

    protected Usuario(String nome, String usuario, String senha) {
        this.nome = nome;
        this.usuario = usuario;
        this.senha = senha;
    }

    public abstract String getPapel();

    /** Por padrao um usuario comum nao gerencia o acervo. */
    public boolean podeGerenciarAcervo() {
        return false;
    }

    public String getIniciais() {
        String limpo = nome == null ? "" : nome.trim();
        if (limpo.isEmpty()) {
            return "AR";
        }
        String[] partes = limpo.split("\\s+");
        if (partes.length == 1) {
            return partes[0].substring(0, Math.min(2, partes[0].length())).toUpperCase();
        }
        return ("" + partes[0].charAt(0) + partes[partes.length - 1].charAt(0)).toUpperCase();
    }

    public boolean autenticar(String usuario, String senha) {
        return this.usuario.equalsIgnoreCase(usuario) && this.senha.equals(senha);
    }

    public void adicionarFavorito(Favorito favorito) {
        if (favorito != null && !favoritos.contains(favorito)) {
            favoritos.add(favorito);
        }
    }

    public void removerFavoritoDaObra(Obra obra) {
        favoritos.removeIf(f -> f.getObra().equals(obra));
    }

    public boolean temFavorito(Obra obra) {
        return favoritos.stream().anyMatch(f -> f.getObra().equals(obra));
    }

    public List<Favorito> getFavoritos() {
        return Collections.unmodifiableList(favoritos);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    protected String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    @Override
    public String getRotulo() {
        return nome + " (" + getPapel() + ")";
    }
}
