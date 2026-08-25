package com.artiva.view;

import com.artiva.model.Obra;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.function.Consumer;
import java.util.function.Predicate;

/** Card de obra usado nas diversas secoes do feed. */
public class ObraCard extends VBox {

    public ObraCard(Obra obra, double largura, double alturaImagem, boolean grande,
                    Consumer<Obra> aoAbrir) {
        this(obra, largura, alturaImagem, grande, aoAbrir, null, null);
    }

    public ObraCard(Obra obra, double largura, double alturaImagem, boolean grande,
                    Consumer<Obra> aoAbrir, Consumer<Obra> aoFavoritar, Predicate<Obra> ehFavorita) {
        getStyleClass().addAll("card", grande ? "card-grande" : "card-medio");
        setSpacing(12);
        setPrefWidth(largura);
        setMaxWidth(largura);

        StackPane moldura = UI.moldura(obra.getImagem(), largura - 32, alturaImagem);
        if (obra.isNovidade()) {
            Label novo = UI.seloNovo();
            StackPane.setAlignment(novo, Pos.TOP_LEFT);
            moldura.getChildren().add(novo);
        }

        Label titulo = UI.comEstilo(new Label(obra.getTitulo()), grande ? "card-titulo-grande" : "card-titulo");
        titulo.setWrapText(true);
        Label autor = UI.comEstilo(new Label(obra.getAutor() == null ? "Autoria desconhecida"
                : obra.getAutor().getNome()), "card-autor");

        HBox meta = new HBox(8);
        meta.setAlignment(Pos.CENTER_LEFT);
        if (obra.getEstilo() != null) {
            meta.getChildren().add(UI.comEstilo(new Label(obra.getEstilo().getNome()), "chip"));
        }
        if (obra.getTecnica() != null) {
            meta.getChildren().add(UI.comEstilo(new Label(obra.getTecnica().getNome()), "chip"));
        }
        meta.getChildren().add(UI.selo(obra.getDificuldade()));

        getChildren().addAll(moldura, titulo, autor, meta);

        if (aoFavoritar != null && ehFavorita != null) {
            Button favoritar = UI.comEstilo(new Button(), "botao-favorito");
            atualizarFavorito(favoritar, obra, ehFavorita);
            favoritar.setOnAction(e -> {
                aoFavoritar.accept(obra);
                atualizarFavorito(favoritar, obra, ehFavorita);
                e.consume();
            });
            Region flex = new Region();
            HBox.setHgrow(flex, javafx.scene.layout.Priority.ALWAYS);
            HBox rodape = new HBox(10, UI.comEstilo(new Label(obra.getTotalFavoritos()
                    + " favoritos"), "card-metrica"), flex, favoritar);
            rodape.setAlignment(Pos.CENTER_LEFT);
            getChildren().add(rodape);
        }

        setOnMouseClicked(e -> aoAbrir.accept(obra));
        hoverProperty().addListener((obs, antes, agora) -> {
            if (agora) {
                getStyleClass().add("card-hover");
            } else {
                getStyleClass().remove("card-hover");
            }
        });
    }

    private void atualizarFavorito(Button botao, Obra obra, Predicate<Obra> ehFavorita) {
        boolean favorita = ehFavorita.test(obra);
        botao.setText(favorita ? "Salva" : "Favoritar");
        botao.getStyleClass().remove("botao-favorito-ativo");
        if (favorita) {
            botao.getStyleClass().add("botao-favorito-ativo");
        }
    }
}
