package com.artiva.view;

import com.artiva.controller.FeedController;
import com.artiva.model.Autor;
import com.artiva.model.ConteudoEducativo;
import com.artiva.model.Estilo;
import com.artiva.model.Obra;
import com.artiva.model.Tecnica;
import com.artiva.model.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.List;

/** Tela principal: header de navegacao + feed completo da galeria. */
public class FeedView extends VBox {

    private final FeedController controller;
    private final String secao;
    private final String termoBusca;

    public FeedView(FeedController controller, String secao, String termoBusca) {
        this.controller = controller;
        this.secao = secao == null ? FeedController.SECAO_EXPLORAR : secao;
        this.termoBusca = termoBusca;
        getStyleClass().add("tela-feed");
        getChildren().addAll(header(), conteudo());
    }

    // ---------------- header ----------------

    private HBox header() {
        HBox barra = new HBox(28);
        barra.getStyleClass().add("header");
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setPadding(new Insets(20, 40, 20, 40));

        Label marca = UI.comEstilo(new Label("ARTIVA"), "marca-header");
        marca.setOnMouseClicked(e -> controller.navegacao().irParaFeed(FeedController.SECAO_EXPLORAR));

        HBox menu = new HBox(6);
        menu.setAlignment(Pos.CENTER_LEFT);
        for (String item : controller.secoes()) {
            Button botao = UI.comEstilo(new Button(item), "menu-item");
            if (item.equals(secao)) {
                botao.getStyleClass().add("menu-item-ativo");
            }
            botao.setOnAction(e -> controller.navegacao().irParaFeed(item));
            menu.getChildren().add(botao);
        }

        Region flex = new Region();
        HBox.setHgrow(flex, Priority.ALWAYS);

        TextField busca = UI.comEstilo(new TextField(termoBusca == null ? "" : termoBusca), "campo-busca");
        busca.setPromptText("Buscar obras, artistas, estilos...");
        busca.setPrefWidth(260);
        busca.setOnAction(e -> controller.navegacao().pesquisar(busca.getText()));

        barra.getChildren().addAll(marca, menu, flex, busca);

        if (controller.usuarioEhCurador()) {
            Button curadoria = UI.comEstilo(new Button("Curadoria"), "botao-secundario");
            curadoria.setOnAction(e -> controller.navegacao().irParaAdministracao());
            barra.getChildren().add(curadoria);
        }

        Usuario usuario = controller.usuarioAtual();
        Label avatar = UI.comEstilo(new Label(usuario == null ? "AR" : usuario.getIniciais()), "avatar");
        VBox identidade = new VBox(2,
                UI.comEstilo(new Label(usuario == null ? "Visitante" : usuario.getNome()), "avatar-nome"),
                UI.comEstilo(new Label(usuario == null ? "" : usuario.getPapel()), "avatar-papel"));
        Button sair = UI.comEstilo(new Button("Sair"), "botao-texto");
        sair.setOnAction(e -> controller.navegacao().irParaLogin());

        barra.getChildren().addAll(avatar, identidade, sair);
        return barra;
    }

    // ---------------- conteudo ----------------

    private ScrollPane conteudo() {
        VBox corpo = new VBox(46);
        corpo.getStyleClass().add("feed-corpo");
        corpo.setPadding(new Insets(36, 40, 60, 40));

        if (termoBusca != null && !termoBusca.isBlank()) {
            List<Obra> achados = controller.pesquisar(termoBusca);
            corpo.getChildren().add(secaoObras("Resultados para \"" + termoBusca + "\"",
                    achados.size() + " obra(s) encontrada(s)", achados));
        } else {
            switch (secao) {
                case FeedController.SECAO_OBRAS -> corpo.getChildren().add(
                        secaoObras("Todas as obras", "O acervo completo do Artiva", controller.todasAsObras()));
                case FeedController.SECAO_ESTILOS -> corpo.getChildren().add(blocoEstilos());
                case FeedController.SECAO_TECNICAS -> corpo.getChildren().add(blocoTecnicas());
                case FeedController.SECAO_ARTISTAS -> corpo.getChildren().add(blocoArtistas());
                case FeedController.SECAO_FAVORITOS -> corpo.getChildren().add(
                        secaoObras("Suas obras favoritas", "Tudo o que voce salvou para estudar depois",
                                controller.favoritas()));
                default -> corpo.getChildren().addAll(explorar());
            }
        }

        ScrollPane scroll = new ScrollPane(corpo);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("scroll-feed");
        VBox.setVgrow(scroll, Priority.ALWAYS);
        return scroll;
    }

    private List<Region> explorar() {
        return List.of(
                hero(),
                secaoObras("Destaques da galeria", "As obras mais favoritadas pela comunidade",
                        controller.destaques()),
                secaoObras("Recem-cadastradas", "Novidades que acabaram de entrar no acervo",
                        controller.novidades()),
                blocoEstilos(),
                blocoTecnicaDaSemana(),
                blocoArtistaEmDestaque(),
                blocoAprender(),
                secaoObras("Populares agora", "O que mais desperta interesse", controller.populares()),
                secaoObras("Para quem esta comecando", "Obras acessiveis para iniciantes",
                        controller.paraIniciantes()));
    }

    private Region hero() {
        List<Obra> destaques = controller.destaques();
        Obra principal = destaques.isEmpty() ? null : destaques.get(0);

        VBox texto = new VBox(16);
        texto.setAlignment(Pos.CENTER_LEFT);
        texto.setPadding(new Insets(48));
        texto.setMaxWidth(520);
        texto.getChildren().addAll(
                UI.etiqueta("GALERIA DIGITAL"),
                UI.comEstilo(new Label("A pintura, mais perto de voce."), "hero-titulo"),
                UI.corpo("Explore obras, artistas, estilos, tecnicas e materiais em uma linguagem simples "
                        + "- sem precisar ser especialista em arte."));
        if (principal != null) {
            Button abrir = UI.comEstilo(new Button("Comecar por " + principal.getTitulo()), "botao-primario");
            abrir.setOnAction(e -> controller.navegacao().irParaDetalheObra(principal));
            texto.getChildren().add(abrir);
        }

        StackPane arte = new StackPane();
        arte.getStyleClass().add("hero-arte");
        if (principal != null) {
            arte.getChildren().add(UI.moldura(principal.getImagem(), 560, 340));
        }

        HBox hero = new HBox(32, texto, arte);
        hero.getStyleClass().add("hero");
        hero.setAlignment(Pos.CENTER_LEFT);
        return hero;
    }

    private Region secaoObras(String titulo, String subtitulo, List<Obra> obras) {
        VBox bloco = new VBox(18, cabecalho(titulo, subtitulo));
        if (obras.isEmpty()) {
            bloco.getChildren().add(UI.comEstilo(new Label("Nada por aqui ainda."), "vazio"));
            return bloco;
        }
        FlowPane grade = grade();
        boolean grande = obras.size() <= 3;
        for (Obra obra : obras) {
            grade.getChildren().add(new ObraCard(obra, grande ? 380 : 300, grande ? 260 : 200, grande,
                    o -> controller.navegacao().irParaDetalheObra(o),
                    controller::alternarFavorito, controller::ehFavorita));
        }
        bloco.getChildren().add(grade);
        return bloco;
    }

    private Region blocoEstilos() {
        VBox bloco = new VBox(18, cabecalho("Estilos para conhecer",
                "Movimentos que moldaram a historia da pintura"));
        FlowPane grade = grade();
        for (Estilo estilo : controller.estilos()) {
            VBox card = cardTexto(estilo.getNome(), estilo.getPeriodo(), estilo.getDescricao());
            card.getChildren().add(UI.selo(estilo.getDificuldade()));
            card.getChildren().add(UI.comEstilo(new Label(
                    controller.obrasDoEstilo(estilo).size() + " obras no acervo"), "card-metrica"));
            grade.getChildren().add(card);
        }
        bloco.getChildren().add(grade);
        return bloco;
    }

    private Region blocoTecnicas() {
        VBox bloco = new VBox(18, cabecalho("Tecnicas de pintura",
                "Como as obras sao construidas, na pratica"));
        FlowPane grade = grade();
        for (Tecnica tecnica : controller.tecnicas()) {
            VBox card = cardTexto(tecnica.getNome(),
                    tecnica.getMateriais().size() + " materiais", tecnica.getDescricao());
            card.getChildren().add(UI.selo(tecnica.getDificuldade()));
            HBox chips = new HBox(8);
            tecnica.getMateriais().forEach(m ->
                    chips.getChildren().add(UI.comEstilo(new Label(m.getNome()), "chip")));
            card.getChildren().add(chips);
            grade.getChildren().add(card);
        }
        bloco.getChildren().add(grade);
        return bloco;
    }

    private Region blocoArtistas() {
        VBox bloco = new VBox(18, cabecalho("Artistas", "Quem pintou o que voce ve"));
        FlowPane grade = grade();
        for (Autor autor : controller.autores()) {
            VBox card = cardTexto(autor.getNome(), autor.getPeriodo(), autor.getBiografia());
            card.getChildren().add(UI.comEstilo(new Label(
                    controller.obrasDoAutor(autor).size() + " obras no acervo"), "card-metrica"));
            grade.getChildren().add(card);
        }
        bloco.getChildren().add(grade);
        return bloco;
    }

    private Region blocoTecnicaDaSemana() {
        VBox bloco = new VBox(18, cabecalho("Tecnica da semana", "Um aprofundamento por vez"));
        controller.tecnicaDaSemana().ifPresentOrElse(tecnica -> {
            VBox texto = new VBox(12,
                    UI.etiqueta("TECNICA"),
                    UI.comEstilo(new Label(tecnica.getNome()), "destaque-titulo"),
                    UI.corpo(tecnica.getDescricao()),
                    UI.selo(tecnica.getDificuldade()));
            texto.setMaxWidth(560);
            HBox faixa = new HBox(28, texto);
            faixa.getStyleClass().add("faixa-destaque");
            faixa.setAlignment(Pos.CENTER_LEFT);
            faixa.setPadding(new Insets(32));
            bloco.getChildren().add(faixa);
        }, () -> bloco.getChildren().add(UI.comEstilo(new Label("Sem tecnicas cadastradas."), "vazio")));
        return bloco;
    }

    private Region blocoArtistaEmDestaque() {
        VBox bloco = new VBox(18, cabecalho("Artista em destaque", "Conheca a trajetoria por tras das obras"));
        controller.artistaEmDestaque().ifPresentOrElse(autor -> {
            VBox texto = new VBox(12,
                    UI.etiqueta("ARTISTA"),
                    UI.comEstilo(new Label(autor.getNome()), "destaque-titulo"),
                    UI.comEstilo(new Label(autor.getPeriodo() + "  -  " + autor.getNacionalidade()), "card-autor"),
                    UI.corpo(autor.getBiografia()));
            texto.setMaxWidth(560);
            HBox faixa = new HBox(28,
                    UI.moldura(autor.getImagem() == null ? "artista_destaque.jpg" : autor.getImagem(), 300, 260),
                    texto);
            faixa.getStyleClass().add("faixa-destaque");
            faixa.setAlignment(Pos.CENTER_LEFT);
            faixa.setPadding(new Insets(24));
            bloco.getChildren().add(faixa);
        }, () -> bloco.getChildren().add(UI.comEstilo(new Label("Sem artistas cadastrados."), "vazio")));
        return bloco;
    }

    private Region blocoAprender() {
        VBox bloco = new VBox(18, cabecalho("Para voce aprender",
                "Leituras rapidas para entender o que esta vendo"));
        FlowPane grade = grade();
        for (ConteudoEducativo conteudo : controller.conteudos()) {
            VBox card = cardTexto(conteudo.getTitulo(), conteudo.getTempoLeitura(), conteudo.getResumo());
            card.getChildren().add(UI.selo(conteudo.getDificuldade()));
            grade.getChildren().add(card);
        }
        bloco.getChildren().add(grade);
        return bloco;
    }

    // ---------------- auxiliares ----------------

    private VBox cabecalho(String titulo, String subtitulo) {
        return new VBox(4, UI.titulo(titulo), UI.subtitulo(subtitulo));
    }

    private FlowPane grade() {
        FlowPane grade = new FlowPane(22, 22);
        grade.getStyleClass().add("grade");
        return grade;
    }

    private VBox cardTexto(String titulo, String legenda, String descricao) {
        VBox card = new VBox(10);
        card.getStyleClass().addAll("card", "card-texto");
        card.setPrefWidth(300);
        card.setMaxWidth(300);
        Label t = UI.comEstilo(new Label(titulo), "card-titulo");
        t.setWrapText(true);
        card.getChildren().addAll(t, UI.comEstilo(new Label(legenda == null ? "" : legenda), "card-autor"),
                UI.corpo(descricao == null ? "" : descricao));
        return card;
    }
}
