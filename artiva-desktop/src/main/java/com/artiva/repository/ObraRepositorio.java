package com.artiva.repository;

import com.artiva.model.Dificuldade;
import com.artiva.model.Obra;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ObraRepositorio extends RepositorioEmMemoria<Obra> {

    public List<Obra> buscarPorTexto(String termo) {
        String t = termo == null ? "" : termo.trim().toLowerCase();
        if (t.isEmpty()) {
            return listarTodos();
        }
        return listarTodos().stream()
                .filter(o -> o.textoPesquisavel().contains(t))
                .collect(Collectors.toList());
    }

    public List<Obra> buscarPorDificuldade(Dificuldade dificuldade) {
        return listarTodos().stream()
                .filter(o -> o.getDificuldade() == dificuldade)
                .collect(Collectors.toList());
    }

    public List<Obra> novidades() {
        return listarTodos().stream().filter(Obra::isNovidade).collect(Collectors.toList());
    }

    public List<Obra> maisFavoritadas() {
        return listarTodos().stream()
                .sorted(Comparator.comparingInt(Obra::getTotalFavoritos).reversed())
                .collect(Collectors.toList());
    }
}
