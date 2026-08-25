package com.artiva.controller;

import com.artiva.model.Obra;

/** Contrato de navegacao entre as telas do software. */
public interface Navegacao {

    void irParaLogin();

    void irParaFeed(String secao);

    void irParaDetalheObra(Obra obra);

    void irParaAdministracao();

    void pesquisar(String termo);
}
