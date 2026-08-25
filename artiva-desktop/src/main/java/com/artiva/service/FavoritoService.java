package com.artiva.service;

import com.artiva.model.Favorito;
import com.artiva.model.Obra;
import com.artiva.model.Usuario;
import com.artiva.repository.AcervoRepositorios;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FavoritoService {

    private final AcervoRepositorios repos;

    public FavoritoService(AcervoRepositorios repos) {
        this.repos = repos;
    }

    /** Favorita ou desfavorita a obra. Retorna true quando a obra ficou favorita. */
    public boolean alternar(Usuario usuario, Obra obra) {
        if (usuario == null || obra == null) {
            return false;
        }
        if (usuario.temFavorito(obra)) {
            usuario.getFavoritos().stream()
                    .filter(f -> f.getObra().equals(obra))
                    .findFirst()
                    .ifPresent(f -> repos.favoritos().remover(f.getId()));
            usuario.removerFavoritoDaObra(obra);
            obra.removerFavorito();
            return false;
        }
        Favorito favorito = repos.favoritos().salvar(new Favorito(usuario, obra));
        usuario.adicionarFavorito(favorito);
        obra.registrarFavorito();
        return true;
    }

    public boolean ehFavorita(Usuario usuario, Obra obra) {
        return usuario != null && usuario.temFavorito(obra);
    }

    public List<Obra> obrasFavoritas(Usuario usuario) {
        if (usuario == null) {
            return List.of();
        }
        return usuario.getFavoritos().stream()
                .sorted(Comparator.comparing(Favorito::getSalvoEm).reversed())
                .map(Favorito::getObra)
                .collect(Collectors.toList());
    }
}
