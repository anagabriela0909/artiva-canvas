package com.artiva.view;

import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Map;

/** Acesso a imagens e folha de estilo empacotadas no proprio projeto. */
public final class Recursos {

    private static final String PASTA_IMAGENS = "/com/artiva/images/";
    private static final Map<String, Image> CACHE = new HashMap<>();

    private Recursos() {
    }

    public static Image imagem(String nome) {
        if (nome == null || nome.isBlank()) {
            return null;
        }
        return CACHE.computeIfAbsent(nome, chave -> {
            var url = Recursos.class.getResource(PASTA_IMAGENS + chave);
            return url == null ? null : new Image(url.toExternalForm(), true);
        });
    }

    public static String css() {
        var url = Recursos.class.getResource("/com/artiva/css/artiva.css");
        return url == null ? null : url.toExternalForm();
    }
}
