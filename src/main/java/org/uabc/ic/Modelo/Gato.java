package org.uabc.ic.Modelo;

public class Gato extends Tamagotchi {
    private boolean ronroneando;

    public Gato(String nombre) {
        super(nombre);
    }

    public void maullar() {
        if (isDurmiendo()) {
            return;
        }
        System.out.println(getNombre() + " dice: ¡miau!");
    }

    public void ronronear() {
        if (isDurmiendo()) {
            return;
        }
        ronroneando = true;
        modificarFelicidad(8);
        System.out.println(getNombre() + " está ronroneando.");
    }

    public boolean isRonroneando() {
        return ronroneando;
    }

    @Override
    public void realizarComportamientoUnico() {
        maullar();
        ronronear();
    }

    @Override
    public void dormir() {
        if (isDurmiendo()) {
            return;
        }
        super.dormir();
        ronroneando = false;
        modificarEnergia(10);
        System.out.println(getNombre() + " se hace bolita para dormir.");
    }

    @Override
    public String mostrarEstado() {
        return super.mostrarEstado() + "\nRonroneando: " + (ronroneando ? "sí" : "no");
    }

    @Override
    protected void aplicarEfectoAlimento(TipoAlimento alimento) {
        int energiaRecuperada = switch (alimento) {
            case CROQUETAS -> 10;
            case FRUTA -> 5;
            case PREMIO -> 8;
        };
        modificarEnergia(energiaRecuperada);
        System.out.println(getNombre() + " come " + alimento.getNombreVisible() + ".");
    }

    @Override
    protected void aplicarEfectoJuego(TipoJuego juego) {
        ronroneando = false;
        switch (juego) {
            case PELOTA -> {
                modificarFelicidad(20);
                modificarEnergia(-15);
                System.out.println(getNombre() + " hace rodar la pelota con la pata.");
            }
            case PASEO -> {
                modificarFelicidad(9);
                modificarEnergia(-6);
                System.out.println(getNombre() + " pasea por el jardín.");
            }
            case ESCONDIDAS -> {
                modificarFelicidad(17);
                modificarEnergia(-11);
                System.out.println(getNombre() + " se esconde dentro de una caja.");
            }
            case SALTOS -> {
                modificarFelicidad(22);
                modificarEnergia(-19);
                System.out.println(getNombre() + " salta para atrapar una pluma.");
            }
        }
    }
}
