package org.uabc.ic.Modelo;

public class Cuyo extends Tamagotchi {
    private int maderaRoida;

    public Cuyo(String nombre) {
        super(nombre);
    }

    public void hacerCuyCuy() {
        if (isDurmiendo()) {
            return;
        }
        System.out.println(getNombre() + " dice: ¡cuy, cuy!");
    }

    public void roerMadera() {
        if (isDurmiendo() || getEnergia() <= 20) {
            return;
        }
        maderaRoida++;
        modificarFelicidad(10);
        modificarEnergia(-5);
        System.out.println(getNombre() + " roe un trozo de madera.");
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

    @Override
    protected void aplicarEfectoAlimento(TipoAlimento alimento) {
        int energiaRecuperada = switch (alimento) {
            case CROQUETAS -> 6;
            case FRUTA -> 11;
            case PREMIO -> 3;
        };
        modificarEnergia(energiaRecuperada);
        System.out.println(getNombre() + " mordisquea " + alimento.getNombreVisible() + ".");
    }

    @Override
    protected void aplicarEfectoJuego(TipoJuego juego) {
        switch (juego) {
            case PELOTA -> {
                modificarFelicidad(14);
                modificarEnergia(-10);
                System.out.println(getNombre() + " empuja una pelota pequeña con el hocico.");
            }
            case PASEO -> {
                modificarFelicidad(11);
                modificarEnergia(-8);
                System.out.println(getNombre() + " explora su corral.");
            }
            case ESCONDIDAS -> {
                modificarFelicidad(21);
                modificarEnergia(-13);
                System.out.println(getNombre() + " se esconde en un túnel.");
            }
            case SALTOS -> {
                modificarFelicidad(18);
                modificarEnergia(-16);
                System.out.println(getNombre() + " da pequeños saltos de alegría.");
            }
        }
    }
}