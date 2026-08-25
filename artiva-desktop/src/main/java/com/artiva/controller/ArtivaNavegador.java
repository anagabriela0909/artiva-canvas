package com.artiva.controller;

import com.artiva.model.Obra;
import com.artiva.service.ArtivaContexto;
import com.artiva.view.AdminView;
import com.artiva.view.FeedView;
import com.artiva.view.LoginView;
import com.artiva.view.ObraDetalheView;
import com.artiva.view.Recursos;
import javafx.animation.FadeTransition;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/** Controlador de navegacao: decide qual tela ocupa a janela. */
public class ArtivaNavegador implements Navegacao {

    private final Stage janela;
    private final ArtivaContexto contexto;
    private Scene cena;

    public ArtivaNavegador(Stage janela, ArtivaContexto contexto) {
        this.janela = janela;
        this.contexto = contexto;
    }

    public void iniciar() {
        janela.setTitle("Artiva - galeria e aprendizado de pintura");
        janela.setMinWidth(1180);
        janela.setMinHeight(760);
        irParaLogin();
        janela.show();
    }

    @Override
    public void irParaLogin() {
        contexto.autenticacao().sair();
        trocar(new LoginView(new LoginController(contexto, this)));
    }

    @Override
    public void irParaFeed(String secao) {
        exigirSessao();
        trocar(new FeedView(new FeedController(contexto, this), secao, null));
    }

    @Override
    public void pesquisar(String termo) {
        exigirSessao();
        trocar(new FeedView(new FeedController(contexto, this), FeedController.SECAO_EXPLORAR, termo));
    }

    @Override
    public void irParaDetalheObra(Obra obra) {
        exigirSessao();
        trocar(new ObraDetalheView(new ObraController(contexto, this), obra));
    }

    @Override
    public void irParaAdministracao() {
        exigirSessao();
        if (!contexto.autenticacao().getUsuarioAtual().podeGerenciarAcervo()) {
            irParaFeed(FeedController.SECAO_EXPLORAR);
            return;
        }
        trocar(new AdminView(new AdminController(contexto, this)));
    }

    private void exigirSessao() {
        if (contexto.autenticacao().getUsuarioAtual() == null) {
            throw new IllegalStateException("Nenhum usuario autenticado.");
        }
    }

    private void trocar(Parent raiz) {
        if (cena == null) {
            cena = new Scene(raiz, 1320, 860);
            String css = Recursos.css();
            if (css != null) {
                cena.getStylesheets().add(css);
            }
            janela.setScene(cena);
        } else {
            cena.setRoot(raiz);
        }
        FadeTransition fade = new FadeTransition(Duration.millis(240), raiz);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.play();
    }
}
