package org.uabc.ic.Modelo;

/**
 * En esta clase se definen los tipos de alimento para todos los tamagotchis disponibles.
 */

public enum TipoAlimento {
    CROQUETAS("Croquetas"),
    FRUTA("Fruta fresca"),
    PREMIO("Dulce");

    private final String nombreVisible;

    TipoAlimento(String nombreVisible) {
        this.nombreVisible = nombreVisible;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }
}
