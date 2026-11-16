package org.uabc.ic.Modelo;

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
