package com.artiva.view;

import com.artiva.model.Dificuldade;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/** Fabrica de componentes visuais reutilizados pelas telas. */
public final class UI {

    private UI() {
    }

    public static Label titulo(String texto) {
        return comEstilo(new Label(texto), "titulo-secao");
    }

    public static Label subtitulo(String texto) {
        return comEstilo(new Label(texto), "subtitulo-secao");
    }

    public static Label corpo(String texto) {
        Label l = comEstilo(new Label(texto), "texto-corpo");
        l.setWrapText(true);
        return l;
    }

    public static Label etiqueta(String texto) {
        return comEstilo(new Label(texto), "etiqueta");
    }

    public static Label selo(Dificuldade dificuldade) {
        Label l = new Label(dificuldade.getRotulo());
        l.getStyleClass().addAll("selo", "selo-" + dificuldade.name().toLowerCase());
        return l;
    }

    public static Label seloNovo() {
        return comEstilo(new Label("NOVO"), "selo-novo");
    }

    public static <T extends Node> T comEstilo(T node, String... classes) {
        node.getStyleClass().addAll(classes);
        return node;
    }

    /** Moldura de imagem com cantos arredondados e zoom suave no hover. */
    public static StackPane moldura(String nomeImagem, double largura, double altura) {
        StackPane moldura = new StackPane();
        moldura.getStyleClass().add("moldura");
        moldura.setPrefSize(largura, altura);
        moldura.setMinSize(largura, altura);
        moldura.setMaxSize(largura, altura);

        Image imagem = Recursos.imagem(nomeImagem);
        if (imagem != null) {
            ImageView view = new ImageView(imagem);
            view.setFitWidth(largura);
            view.setFitHeight(altura);
            view.setPreserveRatio(false);
            view.setSmooth(true);
            moldura.getChildren().add(view);
            aplicarZoom(moldura, view);
        } else {
            Label vazio = new Label("Artiva");
            vazio.getStyleClass().add("moldura-vazia");
            moldura.getChildren().add(vazio);
            StackPane.setAlignment(vazio, Pos.CENTER);
        }

        Rectangle recorte = new Rectangle(largura, altura);
        recorte.setArcWidth(18);
        recorte.setArcHeight(18);
        moldura.setClip(recorte);
        return moldura;
    }

    private static void aplicarZoom(Pane alvoHover, Node imagem) {
        ScaleTransition entra = new ScaleTransition(Duration.millis(260), imagem);
        entra.setToX(1.06);
        entra.setToY(1.06);
        entra.setInterpolator(Interpolator.EASE_OUT);

        ScaleTransition sai = new ScaleTransition(Duration.millis(260), imagem);
        sai.setToX(1.0);
        sai.setToY(1.0);
        sai.setInterpolator(Interpolator.EASE_OUT);

        alvoHover.setOnMouseEntered(e -> {
            sai.stop();
            entra.playFromStart();
        });
        alvoHover.setOnMouseExited(e -> {
            entra.stop();
            sai.playFromStart();
        });
    }

    public static Region espacador() {
        Region r = new Region();
        r.setMinHeight(4);
        return r;
    }
}
