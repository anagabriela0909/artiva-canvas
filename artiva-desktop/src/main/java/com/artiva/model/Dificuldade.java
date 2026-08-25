package com.artiva.model;

/** Nivel de dificuldade usado por obras, estilos e tecnicas. */
public enum Dificuldade {

    INICIANTE("Iniciante"),
    INTERMEDIARIO("Intermediario"),
    AVANCADO("Avancado");

    private final String rotulo;

    Dificuldade(String rotulo) {
        this.rotulo = rotulo;
    }

    public String getRotulo() {
        return rotulo;
    }

    public boolean acessivelParaIniciante() {
        return this == INICIANTE || this == INTERMEDIARIO;
    }

    public static Dificuldade porRotulo(String rotulo) {
        for (Dificuldade d : values()) {
            if (d.rotulo.equalsIgnoreCase(rotulo)) {
                return d;
            }
        }
        return INICIANTE;
    }

    @Override
    public String toString() {
        return rotulo;
    }
}
