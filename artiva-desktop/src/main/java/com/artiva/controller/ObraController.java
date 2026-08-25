package com.artiva.controller;

import com.artiva.model.Obra;
import com.artiva.model.Usuario;
import com.artiva.service.ArtivaContexto;

public class ObraController {

    private final ArtivaContexto contexto;
    private final Navegacao navegacao;

    public ObraController(ArtivaContexto contexto, Navegacao navegacao) {
        this.contexto = contexto;
        this.navegacao = navegacao;
    }

    public Usuario usuarioAtual() {
        return contexto.autenticacao().getUsuarioAtual();
    }

    public boolean alternarFavorito(Obra obra) {
        return contexto.favoritos().alternar(usuarioAtual(), obra);
    }

    public boolean ehFavorita(Obra obra) {
        return contexto.favoritos().ehFavorita(usuarioAtual(), obra);
    }

    public java.util.List<Obra> relacionadas(Obra obra) {
        return contexto.acervo().obrasDoEstilo(obra.getEstilo()).stream()
                .filter(o -> !o.equals(obra))
                .toList();
    }

    public Navegacao navegacao() {
        return navegacao;
    }
}
