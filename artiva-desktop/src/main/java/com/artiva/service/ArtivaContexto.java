package com.artiva.service;

import com.artiva.repository.AcervoRepositorios;
import com.artiva.repository.CargaInicial;

/**
 * Composicao das dependencias do software (repositorios + servicos).
 * Ponto unico para trocar dados mockados por um banco de dados real.
 */
public class ArtivaContexto {

    private final AcervoRepositorios repositorios = new AcervoRepositorios();
    private final AcervoService acervoService;
    private final AutenticacaoService autenticacaoService;
    private final FavoritoService favoritoService;

    public ArtivaContexto() {
        CargaInicial.popular(repositorios);
        this.acervoService = new AcervoService(repositorios);
        this.autenticacaoService = new AutenticacaoService(repositorios);
        this.favoritoService = new FavoritoService(repositorios);
    }

    public AcervoService acervo() {
        return acervoService;
    }

    public AutenticacaoService autenticacao() {
        return autenticacaoService;
    }

    public FavoritoService favoritos() {
        return favoritoService;
    }
}
