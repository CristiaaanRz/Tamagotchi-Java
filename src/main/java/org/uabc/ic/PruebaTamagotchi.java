package org.uabc.ic;

import org.uabc.ic.Modelo.Tamagotchi;
import org.uabc.ic.Modelo.TipoAlimento;
import org.uabc.ic.Modelo.TipoJuego;

public class PruebaTamagotchi {

    public static void main(String[] args) {

        MascotaPrueba mascota = new MascotaPrueba("Epona");

        System.out.println("=== SIMULADOR TAMAGOTCHI ===");

        System.out.println("Nombre: " + mascota.getNombre());
        System.out.println("Hambre: " + mascota.getHambre());
        System.out.println("Felicidad: " + mascota.getFelicidad());
        System.out.println("Energia: " + mascota.getEnergia());
        System.out.println("Durmiendo: " + mascota.isDurmiendo());

        System.out.println("\nEstado de animo:");
        System.out.println(mascota.obtenerEstadoAnimo().getMensaje());

        System.out.println("\n=== OPCIONES DISPONIBLES ===");

        System.out.println("\nAlimentos:");
        for (TipoAlimento alimento : TipoAlimento.values()) {
            System.out.println("- " + alimento.getNombreVisible());
        }

        System.out.println("\nJuegos:");
        for (TipoJuego juego : TipoJuego.values()) {
            System.out.println("- " + juego.getNombreVisible());
        }
    }

    // Clase temporal para realizar pruebas
    static class MascotaPrueba extends Tamagotchi {

        public MascotaPrueba(String nombre) {
            super(nombre);
        }

        @Override
        public void alimentar(TipoAlimento alimento) {
            System.out.println("Alimentacion pendiente");
        }

        @Override
        public void jugar(TipoJuego juego) {
            System.out.println("Juego pendiente");
        }

        @Override
        public void dormir() {
            System.out.println("Descanso pendiente");
        }

        @Override
        public String mostrarEstado() {
            return "Estado de " + getNombre();
        }
    }
}
