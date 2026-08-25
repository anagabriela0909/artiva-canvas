package com.artiva.service;

import com.artiva.model.Usuario;
import com.artiva.model.Visitante;
import com.artiva.repository.AcervoRepositorios;

import java.util.Optional;

public class AutenticacaoService {

    private final AcervoRepositorios repos;
    private Usuario usuarioAtual;

    public AutenticacaoService(AcervoRepositorios repos) {
        this.repos = repos;
    }

    public Usuario entrar(String login, String senha) throws CredenciaisInvalidasException {
        Optional<Usuario> encontrado = repos.usuarios().buscarPorLogin(login);
        if (encontrado.isEmpty() || !encontrado.get().autenticar(login, senha)) {
            throw new CredenciaisInvalidasException("Usuario ou senha incorretos.");
        }
        usuarioAtual = encontrado.get();
        return usuarioAtual;
    }

    public Usuario criarConta(String nome, String login, String senha) throws CredenciaisInvalidasException {
        if (nome == null || nome.isBlank() || login == null || login.isBlank() || senha == null || senha.length() < 4) {
            throw new CredenciaisInvalidasException("Preencha nome, usuario e uma senha com ao menos 4 caracteres.");
        }
        if (repos.usuarios().buscarPorLogin(login).isPresent()) {
            throw new CredenciaisInvalidasException("Este usuario ja existe.");
        }
        Usuario novo = repos.usuarios().salvar(new Visitante(nome, login, senha));
        usuarioAtual = novo;
        return novo;
    }

    public void sair() {
        usuarioAtual = null;
    }

    public Usuario getUsuarioAtual() {
        return usuarioAtual;
    }

    /** Excecao de negocio da autenticacao. */
    public static class CredenciaisInvalidasException extends Exception {
        public CredenciaisInvalidasException(String mensagem) {
            super(mensagem);
        }
    }
}
