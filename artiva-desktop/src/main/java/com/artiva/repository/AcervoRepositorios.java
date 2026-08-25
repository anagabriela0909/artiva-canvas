package com.artiva.repository;

import com.artiva.model.Autor;
import com.artiva.model.ConteudoEducativo;
import com.artiva.model.Estilo;
import com.artiva.model.Favorito;
import com.artiva.model.Material;
import com.artiva.model.Tecnica;

/**
 * Agrupa os repositorios do acervo em um unico ponto de acesso.
 * Trocar por implementacoes de banco de dados exige alterar apenas esta classe.
 */
public class AcervoRepositorios {

    private final ObraRepositorio obras = new ObraRepositorio();
    private final UsuarioRepositorio usuarios = new UsuarioRepositorio();
    private final RepositorioEmMemoria<Autor> autores = new RepositorioEmMemoria<>();
    private final RepositorioEmMemoria<Estilo> estilos = new RepositorioEmMemoria<>();
    private final RepositorioEmMemoria<Tecnica> tecnicas = new RepositorioEmMemoria<>();
    private final RepositorioEmMemoria<Material> materiais = new RepositorioEmMemoria<>();
    private final RepositorioEmMemoria<Favorito> favoritos = new RepositorioEmMemoria<>();
    private final RepositorioEmMemoria<ConteudoEducativo> conteudos = new RepositorioEmMemoria<>();

    public ObraRepositorio obras() {
        return obras;
    }

    public UsuarioRepositorio usuarios() {
        return usuarios;
    }

    public Repositorio<Autor> autores() {
        return autores;
    }

    public Repositorio<Estilo> estilos() {
        return estilos;
    }

    public Repositorio<Tecnica> tecnicas() {
        return tecnicas;
    }

    public Repositorio<Material> materiais() {
        return materiais;
    }

    public Repositorio<Favorito> favoritos() {
        return favoritos;
    }

    public Repositorio<ConteudoEducativo> conteudos() {
        return conteudos;
    }
}
