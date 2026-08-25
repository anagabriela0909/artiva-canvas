package com.artiva.controller;

import com.artiva.service.ArtivaContexto;
import com.artiva.service.AutenticacaoService.CredenciaisInvalidasException;

/** Controlador da tela de login: nenhuma regra de negocio fica na view. */
public class LoginController {

    private final ArtivaContexto contexto;
    private final Navegacao navegacao;

    public LoginController(ArtivaContexto contexto, Navegacao navegacao) {
        this.contexto = contexto;
        this.navegacao = navegacao;
    }

    /** Retorna null em caso de sucesso ou a mensagem de erro. */
    public String entrar(String login, String senha) {
        try {
            contexto.autenticacao().entrar(login, senha);
            navegacao.irParaFeed(FeedController.SECAO_EXPLORAR);
            return null;
        } catch (CredenciaisInvalidasException e) {
            return e.getMessage();
        }
    }

    public String criarConta(String nome, String login, String senha) {
        try {
            contexto.autenticacao().criarConta(nome, login, senha);
            navegacao.irParaFeed(FeedController.SECAO_EXPLORAR);
            return null;
        } catch (CredenciaisInvalidasException e) {
            return e.getMessage();
        }
    }
}
