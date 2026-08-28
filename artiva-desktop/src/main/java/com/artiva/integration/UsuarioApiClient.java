package com.artiva.integration;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

/**
 * Cliente HTTP do back-end Java (Spring Boot).
 * Envia o cadastro de usuario via POST /api/usuarios com corpo JSON.
 */
public class UsuarioApiClient {

    /** Pode ser sobrescrito por -Dartiva.api.url=http://host:porta */
    private static final String URL_PADRAO = "http://localhost:8080/api/usuarios";

    private final String endpoint;
    private final HttpClient http = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();

    public UsuarioApiClient() {
        this(System.getProperty("artiva.api.url", URL_PADRAO));
    }

    public UsuarioApiClient(String endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * Envia o cadastro. O campo "usuario" da tela e mapeado para a chave "email"
     * esperada pelo back-end.
     */
    public RespostaApi cadastrar(String nome, String usuario, String senha) {
        String json = "{"
                + "\"nome\":" + aspas(nome) + ","
                + "\"email\":" + aspas(usuario) + ","
                + "\"senha\":" + aspas(senha)
                + "}";
        try {
            HttpRequest requisicao = HttpRequest.newBuilder(URI.create(endpoint))
                    .timeout(Duration.ofSeconds(8))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();
            HttpResponse<String> resposta = http.send(requisicao, HttpResponse.BodyHandlers.ofString());
            int status = resposta.statusCode();
            if (status >= 200 && status < 300) {
                return RespostaApi.sucesso("Cadastro enviado ao servidor com sucesso.");
            }
            return RespostaApi.recusado("O servidor recusou o cadastro (HTTP " + status + "): "
                    + resumo(resposta.body()));
        } catch (Exception e) {
            return RespostaApi.indisponivel("Nao foi possivel falar com o servidor em " + endpoint + ".");
        }
    }

    private static String resumo(String corpo) {
        if (corpo == null || corpo.isBlank()) {
            return "sem detalhes.";
        }
        String limpo = corpo.replaceAll("\\s+", " ").trim();
        return limpo.length() > 160 ? limpo.substring(0, 160) + "..." : limpo;
    }

    private static String aspas(String valor) {
        String v = valor == null ? "" : valor;
        StringBuilder sb = new StringBuilder("\"");
        for (char c : v.toCharArray()) {
            switch (c) {
                case '"' -> sb.append("\\\"");
                case '\\' -> sb.append("\\\\");
                case '\n' -> sb.append("\\n");
                case '\r' -> sb.append("\\r");
                case '\t' -> sb.append("\\t");
                default -> sb.append(c);
            }
        }
        return sb.append('"').toString();
    }

    /** Resultado da chamada ao back-end. */
    public record RespostaApi(Situacao situacao, String mensagem) {

        public enum Situacao { SUCESSO, RECUSADO, INDISPONIVEL }

        public boolean ok() {
            return situacao == Situacao.SUCESSO;
        }

        static RespostaApi sucesso(String m) {
            return new RespostaApi(Situacao.SUCESSO, m);
        }

        static RespostaApi recusado(String m) {
            return new RespostaApi(Situacao.RECUSADO, m);
        }

        static RespostaApi indisponivel(String m) {
            return new RespostaApi(Situacao.INDISPONIVEL, m);
        }
    }
}
