package org.uabc.ic.Modelo;

public class Muneca extends Tamagotchi {
    private boolean llorando;

    public Muneca(String nombre) {
        super(nombre);
    }

    public void expresarEmocion() {
        if (isDurmiendo()) {
            return;
        }
        if (getFelicidad() <= 60) {
            System.out.println(getNombre() + " dice: «Estoy aburrida».");
        } else {
            System.out.println(getNombre() + " dice: «Estoy feliz».");
        }
    }

    public void llorar() {
        if (isDurmiendo()) {
            return;
        }
        llorando = true;
        System.out.println(getNombre() + " está llorando.");
    }

    public boolean isLlorando() {
        return llorando;
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
        super.dormir();
        llorando = false;
    }

    @Override
    public String mostrarEstado() {
        return super.mostrarEstado() + "\nLlorando: " + (llorando ? "sí" : "no");
    }

    @Override
    protected void aplicarEfectoAlimento(TipoAlimento alimento) {
        int energiaRecuperada = switch (alimento) {
            case CROQUETAS -> 3;
            case FRUTA -> 8;
            case PREMIO -> 10;
        };
        modificarEnergia(energiaRecuperada);
        llorando = false;
        System.out.println(getNombre() + " prueba " + alimento.getNombreVisible() + ".");
    }

    @Override
    protected void aplicarEfectoJuego(TipoJuego juego) {
        switch (juego) {
            case PELOTA -> {
                modificarFelicidad(12);
                modificarEnergia(-7);
                System.out.println(getNombre() + " rueda la pelota con las manos.");
            }
            case PASEO -> {
                modificarFelicidad(20);
                modificarEnergia(-11);
                System.out.println(getNombre() + " pasea en su cochecito.");
            }
            case ESCONDIDAS -> {
                modificarFelicidad(23);
                modificarEnergia(-15);
                System.out.println(getNombre() + " se esconde detrás de la cortina.");
            }
            case SALTOS -> {
                modificarFelicidad(16);
                modificarEnergia(-10);
                System.out.println(getNombre() + " salta a la cuerda.");
            }
        }
        llorando = false;
    }
}