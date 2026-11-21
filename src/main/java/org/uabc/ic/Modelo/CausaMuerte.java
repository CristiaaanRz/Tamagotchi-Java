package org.uabc.ic.Modelo;

public enum CausaMuerte {
    VEJEZ("Vejez"),
    DESCUIDO("Descuido");

    private final String descripcion;

    CausaMuerte(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
