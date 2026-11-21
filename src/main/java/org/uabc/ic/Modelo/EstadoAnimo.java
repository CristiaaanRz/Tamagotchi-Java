package org.uabc.ic.Modelo;

/**
 * En esta clase se definen los estados de ánimo para cada tamagotchi disponible.
 */

public enum EstadoAnimo {
    BIEN("Se siente bien y contento."),
    NECESITA_ATENCION("Necesita atención."),
    MAL("Está en estado crítico (enfermedad por falta de cuidados)."),
    MUERTO("Ha muerto.");

    private final String mensaje;

    EstadoAnimo(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
