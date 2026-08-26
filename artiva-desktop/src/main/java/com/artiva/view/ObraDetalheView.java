package com.artiva.view;

import com.artiva.controller.FeedController;
import com.artiva.controller.ObraController;
import com.artiva.model.Material;
import com.artiva.model.Obra;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

/** Tela de detalhamento de uma obra do acervo. */
public class ObraDetalheView extends VBox {

    private final ObraController controller;
    private final Obra obra;
    private final Button favoritar = UI.comEstilo(new Button(), "botao-primario");
    private final Label metrica = UI.comEstilo(new Label(), "card-metrica");

    public ObraDetalheView(ObraController controller, Obra obra) {
        this.controller = controller;
        this.obra = obra;
        getStyleClass().add("tela-detalhe");
        getChildren().addAll(barraTopo(), corpo());
        atualizarFavorito();
    }

    private HBox barraTopo() {
        Button voltar = UI.comEstilo(new Button("< Voltar ao feed"), "botao-texto");
        voltar.setOnAction(e -> controller.navegacao().irParaFeed(FeedController.SECAO_EXPLORAR));

        Region flex = new Region();
        HBox.setHgrow(flex, Priority.ALWAYS);

        HBox barra = new HBox(16, voltar, flex, UI.comEstilo(new Label("ARTIVA"), "marca-header"));
        barra.getStyleClass().add("header");
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setPadding(new Insets(18, 40, 18, 40));
        return barra;
    }

    private ScrollPane corpo() {
        VBox conteudo = new VBox(40);
        conteudo.setPadding(new Insets(36, 40, 60, 40));
        conteudo.getStyleClass().add("feed-corpo");

        HBox principal = new HBox(36, UI.moldura(obra.getImagem(), 620, 460), fichaTecnica());
        principal.setAlignment(Pos.TOP_LEFT);
        conteudo.getChildren().add(principal);

        conteudo.getChildren().add(textos());
        conteudo.getChildren().add(materiais());

        List<Obra> relacionadas = controller.relacionadas(obra);
        if (!relacionadas.isEmpty()) {
            VBox bloco = new VBox(18,
                    UI.titulo("Obras do mesmo estilo"),
                    UI.subtitulo("Continue explorando por proximidade visual"));
            FlowPane grade = new FlowPane(22, 22);
            for (Obra o : relacionadas) {
                grade.getChildren().add(new ObraCard(o, 300, 200, false,
                        alvo -> controller.navegacao().irParaDetalheObra(alvo)));
            }
            bloco.getChildren().add(grade);
            conteudo.getChildren().add(bloco);
        }

        ScrollPane scroll = new ScrollPane(conteudo);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("scroll-feed");
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return scroll;
    }

    private VBox fichaTecnica() {
        VBox ficha = new VBox(14);
        ficha.getStyleClass().add("ficha");
        ficha.setPadding(new Insets(28));
        ficha.setPrefWidth(420);

        Label titulo = UI.comEstilo(new Label(obra.getTitulo()), "detalhe-titulo");
        titulo.setWrapText(true);

        HBox chips = new HBox(8);
        if (obra.getEstilo() != null) {
            chips.getChildren().add(UI.comEstilo(new Label(obra.getEstilo().getNome()), "chip"));
        }
        if (obra.getTecnica() != null) {
            chips.getChildren().add(UI.comEstilo(new Label(obra.getTecnica().getNome()), "chip"));
        }
        chips.getChildren().add(UI.selo(obra.getDificuldade()));

        favoritar.setMaxWidth(Double.MAX_VALUE);
        favoritar.setOnAction(e -> {
            controller.alternarFavorito(obra);
            atualizarFavorito();
        });

        ficha.getChildren().addAll(
                UI.etiqueta("FICHA DA OBRA"),
                titulo,
                UI.comEstilo(new Label((obra.getAutor() == null ? "Autoria desconhecida"
                        : obra.getAutor().getNome()) + "  -  " + obra.getAno()), "card-autor"),
                chips,
                linha("Estilo", obra.getEstilo() == null ? "-" : obra.getEstilo().getNome()),
                linha("Tecnica", obra.getTecnica() == null ? "-" : obra.getTecnica().getNome()),
                linha("Dificuldade", obra.getDificuldade().getRotulo()),
                metrica,
                favoritar);
        return ficha;
    }

    private VBox textos() {
        VBox bloco = new VBox(26);
        bloco.getChildren().add(textoSecao("Sobre a obra", obra.getDescricao()));
        bloco.getChildren().add(textoSecao("Contexto historico", obra.getContextoHistorico()));
        bloco.getChildren().add(textoSecao("Curiosidade", obra.getCuriosidade()));
        if (obra.getAutor() != null) {
            bloco.getChildren().add(textoSecao("Sobre o artista", obra.getAutor().getBiografia()));
        }
        return bloco;
    }

    private VBox materiais() {
        VBox bloco = new VBox(14, UI.titulo("Materiais utilizados"),
                UI.subtitulo("O que costuma estar por tras dessa tecnica"));
        FlowPane chips = new FlowPane(10, 10);
        List<Material> lista = obra.getMateriais();
        if (lista.isEmpty()) {
            chips.getChildren().add(UI.comEstilo(new Label("Nao informado."), "vazio"));
        }
        for (Material m : lista) {
            VBox card = new VBox(6,
                    UI.comEstilo(new Label(m.getNome()), "card-titulo"),
                    UI.corpo(m.getDescricao()));
            card.getStyleClass().addAll("card", "card-texto");
            card.setPrefWidth(260);
            card.setMaxWidth(260);
            chips.getChildren().add(card);
        }
        bloco.getChildren().add(chips);
        return bloco;
    }

    private VBox textoSecao(String titulo, String texto) {
        return new VBox(8, UI.titulo(titulo),
                UI.corpo(texto == null || texto.isBlank() ? "Informacao ainda nao cadastrada." : texto));
    }

    private HBox linha(String rotulo, String valor) {
        HBox linha = new HBox(10, UI.etiqueta(rotulo.toUpperCase()),
                UI.comEstilo(new Label(valor), "texto-corpo"));
        linha.setAlignment(Pos.CENTER_LEFT);
        return linha;
    }

    private void atualizarFavorito() {
        boolean favorita = controller.ehFavorita(obra);
        favoritar.setText(favorita ? "Remover dos favoritos" : "Favoritar esta obra");
        metrica.setText(obra.getTotalFavoritos() + " pessoas favoritaram");
    }
}
