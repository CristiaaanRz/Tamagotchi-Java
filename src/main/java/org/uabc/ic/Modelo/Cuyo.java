package org.uabc.ic.Modelo;

public class Cuyo extends Tamagotchi {
    private int maderaRoida;

    public Cuyo(String nombre) {
        super(nombre);
    }

    public void hacerCuyCuy() {
        if (!estaVivo() || isDurmiendo()) {
            return;
        }
        emitirMensaje(getNombre() + " dice: ¡cuy, cuy!");
    }

    public void roerMadera() {
        if (!puedeJugar()) {
            return;
        }
        maderaRoida++;
        modificarFelicidad(10);
        modificarEnergia(-5);
        emitirMensaje(getNombre() + " roe un trozo de madera.");
    }

    public int getMaderaRoida() {
        return maderaRoida;
    }

    @Override
    public void realizarComportamientoUnico() {
        hacerCuyCuy();
        roerMadera();
    }

    @Override
    public String mostrarEstado() {
        return super.mostrarEstado() + "\nTrozos de madera roída: " + maderaRoida;
    }
}
