package org.uabc.ic.Modelo;

public enum TipoJuego {
    PELOTA("Jugar a la pelota"),
    PASEO("Dar un paseo"),
    ESCONDIDAS("Jugar a las escondidas"),
    SALTOS("Practicar saltos");

    private final String nombreVisible;
    TipoJuego(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }
    public String getNombreVisible() {
        return nombreVisible;
    }
}
