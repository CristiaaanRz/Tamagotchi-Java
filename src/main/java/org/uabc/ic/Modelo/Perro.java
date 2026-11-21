package org.uabc.ic.Modelo;

public class Perro extends Tamagotchi {
    private int nivelLadridos;

    public Perro(String nombre) {
        super(nombre);
    }

    public void ladrar() {
        if (!estaVivo() || isDurmiendo()) {
            return;
        }
        nivelLadridos++;
        emitirMensaje(getNombre() + " está ladrando.");
    }

    public void perseguirCola() {
        if (!puedeJugar()) {
            return;
        }
        modificarFelicidad(12);
        modificarEnergia(-8);
        emitirMensaje(getNombre() + " está persiguiendo su cola.");
    }

    public int getNivelLadridos() {
        return nivelLadridos;
    }

    @Override
    public void realizarComportamientoUnico() {
        ladrar();
        perseguirCola();
    }

    @Override
    public String mostrarEstado() {
        return super.mostrarEstado() + "\nNivel de ladridos: " + nivelLadridos;
    }
}
