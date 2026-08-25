package com.artiva.controller;

import com.artiva.model.Autor;
import com.artiva.model.ConteudoEducativo;
import com.artiva.model.Estilo;
import com.artiva.model.Obra;
import com.artiva.model.Tecnica;
import com.artiva.model.Usuario;
import com.artiva.service.ArtivaContexto;

import java.util.List;
import java.util.Optional;

/** Controlador do feed: consulta os servicos e entrega dados prontos a view. */
public class FeedController {

    public static final String SECAO_EXPLORAR = "Explorar";
    public static final String SECAO_OBRAS = "Obras";
    public static final String SECAO_ESTILOS = "Estilos";
    public static final String SECAO_TECNICAS = "Tecnicas";
    public static final String SECAO_ARTISTAS = "Artistas";
    public static final String SECAO_FAVORITOS = "Favoritos";

    private final ArtivaContexto contexto;
    private final Navegacao navegacao;

    public FeedController(ArtivaContexto contexto, Navegacao navegacao) {
        this.contexto = contexto;
        this.navegacao = navegacao;
    }

    public List<String> secoes() {
        return List.of(SECAO_EXPLORAR, SECAO_OBRAS, SECAO_ESTILOS, SECAO_TECNICAS,
                SECAO_ARTISTAS, SECAO_FAVORITOS);
    }

    public Usuario usuarioAtual() {
        return contexto.autenticacao().getUsuarioAtual();
    }

    public boolean usuarioEhCurador() {
        Usuario u = usuarioAtual();
        return u != null && u.podeGerenciarAcervo();
    }

    public List<Obra> destaques() {
        return contexto.acervo().destaques();
    }

    public List<Obra> novidades() {
        return contexto.acervo().novidades();
    }

    public List<Obra> populares() {
        return contexto.acervo().populares();
    }

    public List<Obra> paraIniciantes() {
        return contexto.acervo().paraIniciantes();
    }

    public List<Obra> todasAsObras() {
        return contexto.acervo().listarObras();
    }

    public List<Obra> pesquisar(String termo) {
        return contexto.acervo().pesquisar(termo);
    }

    public List<Obra> favoritas() {
        return contexto.favoritos().obrasFavoritas(usuarioAtual());
    }

    public boolean alternarFavorito(Obra obra) {
        return contexto.favoritos().alternar(usuarioAtual(), obra);
    }

    public boolean ehFavorita(Obra obra) {
        return contexto.favoritos().ehFavorita(usuarioAtual(), obra);
    }

    public List<Estilo> estilos() {
        return contexto.acervo().listarEstilos();
    }

    public List<Tecnica> tecnicas() {
        return contexto.acervo().listarTecnicas();
    }

    public List<Autor> autores() {
        return contexto.acervo().listarAutores();
    }

    public List<ConteudoEducativo> conteudos() {
        return contexto.acervo().listarConteudos();
    }

    public Optional<Tecnica> tecnicaDaSemana() {
        return contexto.acervo().tecnicaDaSemana();
    }

    public Optional<Autor> artistaEmDestaque() {
        return contexto.acervo().artistaEmDestaque();
    }

    public List<Obra> obrasDoAutor(Autor autor) {
        return contexto.acervo().obrasDoAutor(autor);
    }

    public List<Obra> obrasDoEstilo(Estilo estilo) {
        return contexto.acervo().obrasDoEstilo(estilo);
    }

    public List<Obra> obrasDaTecnica(Tecnica tecnica) {
        return contexto.acervo().obrasDaTecnica(tecnica);
    }

    public Navegacao navegacao() {
        return navegacao;
    }
}
