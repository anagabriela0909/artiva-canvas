package com.artiva.controller;

import com.artiva.model.Autor;
import com.artiva.model.Dificuldade;
import com.artiva.model.Estilo;
import com.artiva.model.Material;
import com.artiva.model.Obra;
import com.artiva.model.Tecnica;
import com.artiva.service.ArtivaContexto;

import java.util.List;

/** Controlador da area de curadoria (CRUD do acervo). */
public class AdminController {

    private final ArtivaContexto contexto;
    private final Navegacao navegacao;

    public AdminController(ArtivaContexto contexto, Navegacao navegacao) {
        this.contexto = contexto;
        this.navegacao = navegacao;
    }

    public List<Obra> obras() {
        return contexto.acervo().listarObras();
    }

    public List<Autor> autores() {
        return contexto.acervo().listarAutores();
    }

    public List<Estilo> estilos() {
        return contexto.acervo().listarEstilos();
    }

    public List<Tecnica> tecnicas() {
        return contexto.acervo().listarTecnicas();
    }

    public List<Material> materiais() {
        return contexto.acervo().listarMateriais();
    }

    public Obra cadastrarObra(String titulo, Autor autor, String ano, Estilo estilo, Tecnica tecnica,
                             Dificuldade dificuldade, String imagem, String descricao) {
        Obra obra = new Obra(titulo, autor, parseAno(ano), estilo, tecnica, dificuldade, imagem);
        obra.setDescricao(descricao);
        obra.setNovidade(true);
        return contexto.acervo().salvarObra(obra);
    }

    public Obra atualizarObra(Obra obra, String titulo, Autor autor, String ano, Estilo estilo,
                              Tecnica tecnica, Dificuldade dificuldade, String imagem, String descricao) {
        obra.setTitulo(titulo);
        obra.setAutor(autor);
        obra.setAno(parseAno(ano));
        obra.setEstilo(estilo);
        obra.setTecnica(tecnica);
        obra.setDificuldade(dificuldade);
        obra.setImagem(imagem);
        obra.setDescricao(descricao);
        return contexto.acervo().salvarObra(obra);
    }

    public boolean removerObra(Obra obra) {
        return contexto.acervo().removerObra(obra);
    }

    public Autor cadastrarAutor(String nome, String periodo, String biografia) {
        return contexto.acervo().salvarAutor(new Autor(nome, periodo, "", biografia, "", null));
    }

    public Estilo cadastrarEstilo(String nome, String periodo, String descricao, Dificuldade dificuldade) {
        return contexto.acervo().salvarEstilo(new Estilo(nome, periodo, descricao, dificuldade, null));
    }

    public Tecnica cadastrarTecnica(String nome, String descricao, Dificuldade dificuldade) {
        return contexto.acervo().salvarTecnica(new Tecnica(nome, descricao, dificuldade));
    }

    public Material cadastrarMaterial(String nome, String descricao) {
        return contexto.acervo().salvarMaterial(new Material(nome, descricao));
    }

    private int parseAno(String ano) {
        try {
            return Integer.parseInt(ano.trim());
        } catch (RuntimeException e) {
            return 0;
        }
    }

    public Navegacao navegacao() {
        return navegacao;
    }
}
