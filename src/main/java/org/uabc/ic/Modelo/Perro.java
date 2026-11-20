package org.uabc.ic.Modelo;

public class Perro extends Tamagotchi {
    private int nivelLadridos;

    public Perro(String nombre) {
        super(nombre);
    }

    public void ladrar() {
        if (isDurmiendo()) {
            return;
        }
        nivelLadridos++;
        System.out.println(getNombre() + " está ladrando.");
    }

    public void perseguirCola() {
        if (isDurmiendo() || getEnergia() <= 20) {
            return;
        }
        modificarFelicidad(12);
        modificarEnergia(-8);
        System.out.println(getNombre() + " está persiguiendo su cola.");
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

    @Override
    protected void aplicarEfectoAlimento(TipoAlimento alimento) {
        int energiaRecuperada = switch (alimento) {
            case CROQUETAS -> 12;
            case FRUTA -> 7;
            case PREMIO -> 4;
        };
        modificarEnergia(energiaRecuperada);
        System.out.println(getNombre() + " come " + alimento.getNombreVisible() + ".");
    }

    @Override
    protected void aplicarEfectoJuego(TipoJuego juego) {
        switch (juego) {
            case PELOTA -> {
                modificarFelicidad(22);
                modificarEnergia(-18);
                System.out.println(getNombre() + " corre detrás de su pelota.");
            }
            case PASEO -> {
                modificarFelicidad(18);
                modificarEnergia(-14);
                System.out.println(getNombre() + " pasea por el parque.");
            }
            case ESCONDIDAS -> {
                modificarFelicidad(13);
                modificarEnergia(-9);
                System.out.println(getNombre() + " se esconde detrás de un árbol.");
            }
            case SALTOS -> {
                modificarFelicidad(17);
                modificarEnergia(-12);
                System.out.println(getNombre() + " salta por encima de un tronco.");
            }
        }
    }
}