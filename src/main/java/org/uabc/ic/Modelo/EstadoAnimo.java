package org.uabc.ic.Modelo;

/**
 * En esta clase se definen los estados de ánimo para cada tamagotchi disponible.
 */

public enum EstadoAnimo {
    BIEN("Se siente bien y contento."),
    NECESITA_ATENCION("Necesita atencion; empieza a sentir hambre, sueño o aburrimiento."),
    MAL("!Se siente mal! Necesita cuidados especiales urgentemente.");

    private final String mensaje;

    EstadoAnimo(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
