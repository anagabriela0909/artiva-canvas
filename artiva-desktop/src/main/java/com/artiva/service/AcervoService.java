package com.artiva.service;

import com.artiva.model.Autor;
import com.artiva.model.ConteudoEducativo;
import com.artiva.model.Dificuldade;
import com.artiva.model.Estilo;
import com.artiva.model.Material;
import com.artiva.model.Obra;
import com.artiva.model.Tecnica;
import com.artiva.repository.AcervoRepositorios;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/** Regras de negocio do acervo. Nenhuma tela acessa repositorio diretamente. */
public class AcervoService {

    private final AcervoRepositorios repos;

    public AcervoService(AcervoRepositorios repos) {
        this.repos = repos;
    }

    public List<Obra> listarObras() {
        return repos.obras().listarTodos();
    }

    public List<Obra> destaques() {
        return repos.obras().maisFavoritadas().stream().limit(3).collect(Collectors.toList());
    }

    public List<Obra> novidades() {
        return repos.obras().novidades();
    }

    public List<Obra> populares() {
        return repos.obras().maisFavoritadas();
    }

    public List<Obra> paraIniciantes() {
        return listarObras().stream()
                .filter(o -> o.getDificuldade().acessivelParaIniciante())
                .collect(Collectors.toList());
    }

    public List<Obra> pesquisar(String termo) {
        return repos.obras().buscarPorTexto(termo);
    }

    public List<Obra> porDificuldade(Dificuldade dificuldade) {
        return repos.obras().buscarPorDificuldade(dificuldade);
    }

    public List<Obra> obrasDoEstilo(Estilo estilo) {
        return listarObras().stream()
                .filter(o -> o.getEstilo() != null && o.getEstilo().equals(estilo))
                .collect(Collectors.toList());
    }

    public List<Obra> obrasDaTecnica(Tecnica tecnica) {
        return listarObras().stream()
                .filter(o -> o.getTecnica() != null && o.getTecnica().equals(tecnica))
                .collect(Collectors.toList());
    }

    public List<Estilo> listarEstilos() {
        return repos.estilos().listarTodos();
    }

    public List<Tecnica> listarTecnicas() {
        return repos.tecnicas().listarTodos();
    }

    public List<Autor> listarAutores() {
        return repos.autores().listarTodos();
    }

    public List<Material> listarMateriais() {
        return repos.materiais().listarTodos();
    }

    public List<ConteudoEducativo> listarConteudos() {
        return repos.conteudos().listarTodos();
    }

    public Optional<Tecnica> tecnicaDaSemana() {
        return listarTecnicas().stream()
                .filter(t -> t.getDificuldade() == Dificuldade.AVANCADO)
                .findFirst()
                .or(() -> listarTecnicas().stream().findFirst());
    }

    public Optional<Autor> artistaEmDestaque() {
        return destaques().stream().map(Obra::getAutor).filter(a -> a != null).findFirst();
    }

    public List<Obra> obrasDoAutor(Autor autor) {
        return listarObras().stream()
                .filter(o -> o.getAutor() != null && o.getAutor().equals(autor))
                .collect(Collectors.toList());
    }

    // ---------- area administrativa ----------

    public Obra salvarObra(Obra obra) {
        return repos.obras().salvar(obra);
    }

    public boolean removerObra(Obra obra) {
        return repos.obras().remover(obra.getId());
    }

    public Autor salvarAutor(Autor autor) {
        return repos.autores().salvar(autor);
    }

    public Estilo salvarEstilo(Estilo estilo) {
        return repos.estilos().salvar(estilo);
    }

    public Tecnica salvarTecnica(Tecnica tecnica) {
        return repos.tecnicas().salvar(tecnica);
    }

    public Material salvarMaterial(Material material) {
        return repos.materiais().salvar(material);
    }
}
