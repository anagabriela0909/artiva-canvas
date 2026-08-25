package com.artiva.repository;

import com.artiva.model.Usuario;

import java.util.Optional;

public class UsuarioRepositorio extends RepositorioEmMemoria<Usuario> {

    public Optional<Usuario> buscarPorLogin(String login) {
        return listarTodos().stream()
                .filter(u -> u.getUsuario().equalsIgnoreCase(login))
                .findFirst();
    }
}
