package org.uabc.ic.Modelo;

public class Muneca extends Tamagotchi {
    private boolean llorando;

    public Muneca(String nombre) {
        super(nombre);
    }

    @Override
    public String getTipoVisible() {
        return "Muñeca";
    }

    public void expresarEmocion() {
        if (!estaVivo() || isDurmiendo()) {
            return;
        }
        if (getFelicidad() <= 60) {
            emitirMensaje(getNombre() + " dice: «Estoy aburrida».");
        } else {
            emitirMensaje(getNombre() + " dice: «Estoy feliz».");
        }
    }

    public void llorar() {
        if (!estaVivo() || isDurmiendo()) {
            return;
        }
        llorando = true;
        emitirMensaje(getNombre() + " está llorando.");
    }

    public boolean isLlorando() {
        return estaVivo() && llorando;
    }

    @Override
    public void realizarComportamientoUnico() {
        expresarEmocion();
        if (getFelicidad() <= 60) {
            llorar();
        }
    }

    @Override
    public void dormir() {
        if (!estaVivo() || isDurmiendo()) {
            return;
        }
        super.dormir();
        llorando = false;
    }

    @Override
    public String mostrarEstado() {
        return super.mostrarEstado() + "\nLlorando: " + (isLlorando() ? "sí" : "no");
    }

    @Override
    protected void aplicarEfectoAlimento(TipoAlimento alimento) {
        llorando = false;
        super.aplicarEfectoAlimento(alimento);
    }

    @Override
    protected void aplicarEfectoJuego(TipoJuego juego) {
        llorando = false;
        super.aplicarEfectoJuego(juego);
    }
}
