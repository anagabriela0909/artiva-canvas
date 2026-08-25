package com.artiva.view;

import com.artiva.controller.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/** Tela de entrada do software. */
public class LoginView extends HBox {

    private final LoginController controller;
    private boolean modoCadastro = false;

    private final TextField campoNome = new TextField();
    private final TextField campoUsuario = new TextField();
    private final PasswordField campoSenha = new PasswordField();
    private final Label mensagem = UI.comEstilo(new Label(""), "mensagem-erro");
    private final Button acao = UI.comEstilo(new Button("Entrar"), "botao-primario");
    private final Hyperlink alternar = UI.comEstilo(new Hyperlink("Nao tem conta? Criar conta"), "link-suave");
    private final VBox blocoNome = new VBox(6);
    private final Label titulo = UI.comEstilo(new Label("Entrar no acervo"), "login-titulo");
    private final Label legenda = UI.comEstilo(new Label(
            "Explore obras, artistas e tecnicas de pintura sem precisar ser especialista."), "login-legenda");

    public LoginView(LoginController controller) {
        this.controller = controller;
        getStyleClass().add("tela-login");
        setFillHeight(true);
        getChildren().addAll(painelArte(), painelFormulario());
    }

    private StackPane painelArte() {
        StackPane arte = new StackPane();
        arte.getStyleClass().add("login-arte");
        HBox.setHgrow(arte, Priority.ALWAYS);

        VBox texto = new VBox(14);
        texto.setAlignment(Pos.BOTTOM_LEFT);
        texto.setPadding(new Insets(56));
        texto.getChildren().addAll(
                UI.comEstilo(new Label("ARTIVA"), "marca-grande"),
                UI.comEstilo(new Label("A pintura, mais perto de voce."), "marca-frase"),
                UI.comEstilo(new Label("Obras - Estilos - Tecnicas - Materiais"), "marca-lista"));

        var moldura = UI.moldura("noite_estrelada.jpg", 720, 860);
        moldura.getStyleClass().add("moldura-login");
        arte.getChildren().addAll(moldura, texto);
        StackPane.setAlignment(texto, Pos.BOTTOM_LEFT);
        return arte;
    }

    private VBox painelFormulario() {
        VBox painel = new VBox(18);
        painel.getStyleClass().add("login-painel");
        painel.setAlignment(Pos.CENTER_LEFT);
        painel.setPadding(new Insets(64, 60, 64, 60));
        painel.setPrefWidth(520);
        painel.setMinWidth(460);

        campoNome.setPromptText("Seu nome");
        campoUsuario.setPromptText("Usuario");
        campoSenha.setPromptText("Senha");
        for (var c : new TextField[]{campoNome, campoUsuario, campoSenha}) {
            c.getStyleClass().add("campo");
        }

        blocoNome.getChildren().addAll(UI.etiqueta("NOME"), campoNome);
        blocoNome.setVisible(false);
        blocoNome.setManaged(false);

        VBox blocoUsuario = new VBox(6, UI.etiqueta("USUARIO"), campoUsuario);
        VBox blocoSenha = new VBox(6, UI.etiqueta("SENHA"), campoSenha);

        acao.setMaxWidth(Double.MAX_VALUE);
        acao.setOnAction(e -> submeter());
        campoSenha.setOnAction(e -> submeter());
        alternar.setOnAction(e -> alternarModo());

        Label dica = UI.comEstilo(new Label("Demonstracao: ana / artiva  -  curadoria: admin / admin"), "login-dica");
        dica.setWrapText(true);

        painel.getChildren().addAll(
                UI.comEstilo(new Label("ARTIVA"), "marca-painel"),
                titulo, legenda,
                blocoNome, blocoUsuario, blocoSenha,
                mensagem, acao, alternar, dica);
        return painel;
    }

    private void alternarModo() {
        modoCadastro = !modoCadastro;
        blocoNome.setVisible(modoCadastro);
        blocoNome.setManaged(modoCadastro);
        titulo.setText(modoCadastro ? "Criar conta" : "Entrar no acervo");
        acao.setText(modoCadastro ? "Criar conta" : "Entrar");
        alternar.setText(modoCadastro ? "Ja tenho conta. Entrar" : "Nao tem conta? Criar conta");
        mensagem.setText("");
    }

    private void submeter() {
        String erro = modoCadastro
                ? controller.criarConta(campoNome.getText(), campoUsuario.getText(), campoSenha.getText())
                : controller.entrar(campoUsuario.getText(), campoSenha.getText());
        mensagem.setText(erro == null ? "" : erro);
    }
}
