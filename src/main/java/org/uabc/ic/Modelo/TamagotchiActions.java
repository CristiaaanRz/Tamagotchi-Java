package org.uabc.ic.Modelo;

/**
 * Define las acciones básicas que todos los Tamagotchis deben ofrecer.
 */

public interface TamagotchiActions {

    void alimentar(TipoAlimento alimento);

    void jugar(TipoJuego juego);

    void dormir();

    String mostrarEstado();

    EstadoAnimo obtenerEstadoAnimo();
}
