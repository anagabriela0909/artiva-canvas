package com.artiva.view;

import com.artiva.controller.AdminController;
import com.artiva.controller.FeedController;
import com.artiva.model.Autor;
import com.artiva.model.Dificuldade;
import com.artiva.model.Estilo;
import com.artiva.model.Material;
import com.artiva.model.Obra;
import com.artiva.model.Tecnica;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

import java.util.List;

/** Area de curadoria: CRUD de obras, artistas, estilos, tecnicas e materiais. */
public class AdminView extends VBox {

    private final AdminController controller;

    private final ListView<Obra> listaObras = new ListView<>();
    private final TextField titulo = new TextField();
    private final TextField ano = new TextField();
    private final TextField imagem = new TextField();
    private final TextArea descricao = new TextArea();
    private final ComboBox<Autor> autor = new ComboBox<>();
    private final ComboBox<Estilo> estilo = new ComboBox<>();
    private final ComboBox<Tecnica> tecnica = new ComboBox<>();
    private final ComboBox<Dificuldade> dificuldade = new ComboBox<>();
    private final Label aviso = UI.comEstilo(new Label(""), "mensagem-ok");

    private Obra emEdicao;

    public AdminView(AdminController controller) {
        this.controller = controller;
        getStyleClass().add("tela-admin");
        getChildren().addAll(barraTopo(), abas());
        recarregarObras();
    }

    private HBox barraTopo() {
        Button voltar = UI.comEstilo(new Button("< Voltar ao feed"), "botao-texto");
        voltar.setOnAction(e -> controller.navegacao().irParaFeed(FeedController.SECAO_EXPLORAR));
        Region flex = new Region();
        HBox.setHgrow(flex, Priority.ALWAYS);
        HBox barra = new HBox(16, voltar,
                UI.comEstilo(new Label("Curadoria do acervo"), "titulo-secao"), flex,
                UI.comEstilo(new Label("ARTIVA"), "marca-header"));
        barra.getStyleClass().add("header");
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.setPadding(new Insets(18, 40, 18, 40));
        return barra;
    }

    private TabPane abas() {
        TabPane abas = new TabPane();
        abas.getStyleClass().add("abas");
        abas.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        abas.getTabs().addAll(
                new Tab("Obras", painelObras()),
                new Tab("Artistas", painelArtistas()),
                new Tab("Estilos", painelEstilos()),
                new Tab("Tecnicas", painelTecnicas()),
                new Tab("Materiais", painelMateriais()));
        VBox.setVgrow(abas, Priority.ALWAYS);
        return abas;
    }

    // ---------------- obras ----------------

    private Region painelObras() {
        listaObras.getStyleClass().add("lista");
        listaObras.setPrefWidth(360);
        listaObras.setCellFactory(v -> new javafx.scene.control.ListCell<>() {
            @Override
            protected void updateItem(Obra obra, boolean vazio) {
                super.updateItem(obra, vazio);
                setText(vazio || obra == null ? null
                        : obra.getTitulo() + "  -  " + (obra.getAutor() == null ? "?" : obra.getAutor().getNome()));
            }
        });
        listaObras.getSelectionModel().selectedItemProperty().addListener((o, a, nova) -> carregar(nova));

        titulo.setPromptText("Titulo da obra");
        ano.setPromptText("Ano");
        imagem.setPromptText("Arquivo de imagem (ex: nenufares.jpg)");
        descricao.setPromptText("Descricao acessivel da obra");
        descricao.setPrefRowCount(4);
        descricao.getStyleClass().add("campo");
        for (TextField c : List.of(titulo, ano, imagem)) {
            c.getStyleClass().add("campo");
        }

        autor.setItems(FXCollections.observableArrayList(controller.autores()));
        estilo.setItems(FXCollections.observableArrayList(controller.estilos()));
        tecnica.setItems(FXCollections.observableArrayList(controller.tecnicas()));
        dificuldade.setItems(FXCollections.observableArrayList(Dificuldade.values()));
        dificuldade.getSelectionModel().select(Dificuldade.INICIANTE);
        for (ComboBox<?> c : List.of(autor, estilo, tecnica, dificuldade)) {
            c.getStyleClass().add("combo");
            c.setMaxWidth(Double.MAX_VALUE);
        }

        Button salvar = UI.comEstilo(new Button("Salvar obra"), "botao-primario");
        salvar.setOnAction(e -> salvarObra());
        Button novo = UI.comEstilo(new Button("Nova obra"), "botao-secundario");
        novo.setOnAction(e -> limparFormulario());
        Button remover = UI.comEstilo(new Button("Remover"), "botao-perigo");
        remover.setOnAction(e -> removerObra());

        VBox form = new VBox(12,
                UI.titulo("Cadastro de obra"),
                campo("Titulo", titulo), campo("Autor", autor), campo("Ano", ano),
                campo("Estilo", estilo), campo("Tecnica", tecnica), campo("Dificuldade", dificuldade),
                campo("Imagem", imagem), campo("Descricao", descricao),
                aviso, new HBox(10, salvar, novo, remover));
        form.getStyleClass().add("painel-form");
        form.setPadding(new Insets(28));
        HBox.setHgrow(form, Priority.ALWAYS);

        VBox esquerda = new VBox(12, UI.titulo("Acervo"), UI.subtitulo("Selecione para editar"), listaObras);
        VBox.setVgrow(listaObras, Priority.ALWAYS);
        esquerda.setPadding(new Insets(28));

        HBox painel = new HBox(24, esquerda, form);
        painel.getStyleClass().add("feed-corpo");
        return painel;
    }

    private void salvarObra() {
        if (titulo.getText().isBlank()) {
            mostrar("Informe o titulo da obra.", false);
            return;
        }
        Dificuldade d = dificuldade.getValue() == null ? Dificuldade.INICIANTE : dificuldade.getValue();
        if (emEdicao == null) {
            controller.cadastrarObra(titulo.getText(), autor.getValue(), ano.getText(), estilo.getValue(),
                    tecnica.getValue(), d, imagem.getText(), descricao.getText());
            mostrar("Obra cadastrada no acervo.", true);
        } else {
            controller.atualizarObra(emEdicao, titulo.getText(), autor.getValue(), ano.getText(),
                    estilo.getValue(), tecnica.getValue(), d, imagem.getText(), descricao.getText());
            mostrar("Obra atualizada.", true);
        }
        recarregarObras();
        limparFormulario();
    }

    private void removerObra() {
        if (emEdicao == null) {
            mostrar("Selecione uma obra na lista.", false);
            return;
        }
        controller.removerObra(emEdicao);
        mostrar("Obra removida do acervo.", true);
        recarregarObras();
        limparFormulario();
    }

    private void carregar(Obra obra) {
        emEdicao = obra;
        if (obra == null) {
            return;
        }
        titulo.setText(obra.getTitulo());
        ano.setText(String.valueOf(obra.getAno()));
        imagem.setText(obra.getImagem() == null ? "" : obra.getImagem());
        descricao.setText(obra.getDescricao());
        autor.setValue(obra.getAutor());
        estilo.setValue(obra.getEstilo());
        tecnica.setValue(obra.getTecnica());
        dificuldade.setValue(obra.getDificuldade());
    }

    private void limparFormulario() {
        emEdicao = null;
        listaObras.getSelectionModel().clearSelection();
        titulo.clear();
        ano.clear();
        imagem.clear();
        descricao.clear();
        autor.setValue(null);
        estilo.setValue(null);
        tecnica.setValue(null);
        dificuldade.setValue(Dificuldade.INICIANTE);
    }

    private void recarregarObras() {
        listaObras.setItems(FXCollections.observableArrayList(controller.obras()));
    }

    // ---------------- demais cadastros ----------------

    private Region painelArtistas() {
        TextField nome = campoSimples("Nome do artista");
        TextField periodo = campoSimples("Periodo (ex: 1853 - 1890)");
        TextArea bio = areaSimples("Biografia curta");
        ListView<Autor> lista = lista(controller.autores());
        Button salvar = UI.comEstilo(new Button("Cadastrar artista"), "botao-primario");
        salvar.setOnAction(e -> {
            if (nome.getText().isBlank()) {
                mostrar("Informe o nome do artista.", false);
                return;
            }
            controller.cadastrarAutor(nome.getText(), periodo.getText(), bio.getText());
            lista.setItems(FXCollections.observableArrayList(controller.autores()));
            nome.clear();
            periodo.clear();
            bio.clear();
            mostrar("Artista cadastrado.", true);
        });
        return painelCadastro("Artistas", lista,
                List.of(campo("Nome", nome), campo("Periodo", periodo), campo("Biografia", bio)), salvar);
    }

    private Region painelEstilos() {
        TextField nome = campoSimples("Nome do estilo");
        TextField periodo = campoSimples("Periodo");
        TextArea desc = areaSimples("Descricao");
        ComboBox<Dificuldade> nivel = new ComboBox<>(FXCollections.observableArrayList(Dificuldade.values()));
        nivel.getStyleClass().add("combo");
        nivel.setValue(Dificuldade.INICIANTE);
        ListView<Estilo> lista = lista(controller.estilos());
        Button salvar = UI.comEstilo(new Button("Cadastrar estilo"), "botao-primario");
        salvar.setOnAction(e -> {
            if (nome.getText().isBlank()) {
                mostrar("Informe o nome do estilo.", false);
                return;
            }
            controller.cadastrarEstilo(nome.getText(), periodo.getText(), desc.getText(), nivel.getValue());
            lista.setItems(FXCollections.observableArrayList(controller.estilos()));
            nome.clear();
            periodo.clear();
            desc.clear();
            mostrar("Estilo cadastrado.", true);
        });
        return painelCadastro("Estilos", lista,
                List.of(campo("Nome", nome), campo("Periodo", periodo), campo("Descricao", desc),
                        campo("Dificuldade", nivel)), salvar);
    }

    private Region painelTecnicas() {
        TextField nome = campoSimples("Nome da tecnica");
        TextArea desc = areaSimples("Como a tecnica funciona");
        ComboBox<Dificuldade> nivel = new ComboBox<>(FXCollections.observableArrayList(Dificuldade.values()));
        nivel.getStyleClass().add("combo");
        nivel.setValue(Dificuldade.INICIANTE);
        ListView<Tecnica> lista = lista(controller.tecnicas());
        Button salvar = UI.comEstilo(new Button("Cadastrar tecnica"), "botao-primario");
        salvar.setOnAction(e -> {
            if (nome.getText().isBlank()) {
                mostrar("Informe o nome da tecnica.", false);
                return;
            }
            controller.cadastrarTecnica(nome.getText(), desc.getText(), nivel.getValue());
            lista.setItems(FXCollections.observableArrayList(controller.tecnicas()));
            nome.clear();
            desc.clear();
            mostrar("Tecnica cadastrada.", true);
        });
        return painelCadastro("Tecnicas", lista,
                List.of(campo("Nome", nome), campo("Descricao", desc), campo("Dificuldade", nivel)), salvar);
    }

    private Region painelMateriais() {
        TextField nome = campoSimples("Nome do material");
        TextArea desc = areaSimples("Para que serve");
        ListView<Material> lista = lista(controller.materiais());
        Button salvar = UI.comEstilo(new Button("Cadastrar material"), "botao-primario");
        salvar.setOnAction(e -> {
            if (nome.getText().isBlank()) {
                mostrar("Informe o nome do material.", false);
                return;
            }
            controller.cadastrarMaterial(nome.getText(), desc.getText());
            lista.setItems(FXCollections.observableArrayList(controller.materiais()));
            nome.clear();
            desc.clear();
            mostrar("Material cadastrado.", true);
        });
        return painelCadastro("Materiais", lista,
                List.of(campo("Nome", nome), campo("Descricao", desc)), salvar);
    }

    // ---------------- helpers ----------------

    private Region painelCadastro(String titulo, ListView<?> lista, List<VBox> campos, Button salvar) {
        VBox esquerda = new VBox(12, UI.titulo(titulo), UI.subtitulo("Itens ja cadastrados"), lista);
        VBox.setVgrow(lista, Priority.ALWAYS);
        esquerda.setPadding(new Insets(28));
        esquerda.setPrefWidth(360);

        VBox form = new VBox(12);
        form.getStyleClass().add("painel-form");
        form.setPadding(new Insets(28));
        form.getChildren().add(UI.titulo("Novo cadastro"));
        form.getChildren().addAll(campos);
        form.getChildren().addAll(aviso, salvar);
        HBox.setHgrow(form, Priority.ALWAYS);

        HBox painel = new HBox(24, esquerda, form);
        painel.getStyleClass().add("feed-corpo");
        return painel;
    }

    private <T> ListView<T> lista(List<T> itens) {
        ListView<T> lista = new ListView<>(FXCollections.observableArrayList(itens));
        lista.getStyleClass().add("lista");
        return lista;
    }

    private TextField campoSimples(String prompt) {
        TextField campo = new TextField();
        campo.setPromptText(prompt);
        campo.getStyleClass().add("campo");
        return campo;
    }

    private TextArea areaSimples(String prompt) {
        TextArea area = new TextArea();
        area.setPromptText(prompt);
        area.setPrefRowCount(3);
        area.getStyleClass().add("campo");
        return area;
    }

    private VBox campo(String rotulo, javafx.scene.Node controle) {
        return new VBox(6, UI.etiqueta(rotulo.toUpperCase()), controle);
    }

    private void mostrar(String texto, boolean ok) {
        aviso.setText(texto);
        aviso.getStyleClass().removeAll("mensagem-ok", "mensagem-erro");
        aviso.getStyleClass().add(ok ? "mensagem-ok" : "mensagem-erro");
    }
}
