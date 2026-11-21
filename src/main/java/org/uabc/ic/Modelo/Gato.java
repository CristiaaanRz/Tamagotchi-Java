package org.uabc.ic.Modelo;

public class Gato extends Tamagotchi {
    private boolean ronroneando;

    public Gato(String nombre) {
        super(nombre);
    }

    public void maullar() {
        if (!estaVivo() || isDurmiendo()) {
            return;
        }
        emitirMensaje(getNombre() + " dice: ¡miau!");
    }

    public void ronronear() {
        if (!estaVivo() || isDurmiendo()) {
            return;
        }
        ronroneando = true;
        modificarFelicidad(8);
        emitirMensaje(getNombre() + " está ronroneando.");
    }

    public boolean isRonroneando() {
        return estaVivo() && ronroneando;
    }

    @Override
    public void realizarComportamientoUnico() {
        maullar();
        ronronear();
    }

    @Override
    public void dormir() {
        if (!estaVivo() || isDurmiendo()) {
            return;
        }

        emitirMensaje(getNombre() + " se hace bolita para dormir.");
        ronroneando = false;
        super.dormir();
    }

    @Override
    protected int energiaRecuperadaAlDormir() {
        return 50;
    }

    @Override
    public String mostrarEstado() {
        return super.mostrarEstado() + "\nRonroneando: " + (isRonroneando() ? "sí" : "no");
    }

    @Override
    protected void aplicarEfectoJuego(TipoJuego juego) {
        ronroneando = false;
        super.aplicarEfectoJuego(juego);
    }
}
