package com.artiva;

import com.artiva.controller.ArtivaNavegador;
import com.artiva.service.ArtivaContexto;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Classe principal do software Artiva.
 * Executar esta classe abre diretamente a interface grafica JavaFX.
 */
public class ArtivaApplication extends Application {

    @Override
    public void start(Stage janela) {
        ArtivaContexto contexto = new ArtivaContexto();
        new ArtivaNavegador(janela, contexto).iniciar();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
