package org.uabc.ic.Modelo;

public class Perro extends Tamagotchi {
    private int nivelLadridos;

    public Perro(String nombre){
        super(nombre);
        this.nivelLadridos = 0;
    }

    public void ladrar(){
        nivelLadridos++;
        System.out.println(getNombre() + " esta ladrando.");
    }

    // Persigue su cola aumentando su felicidad y disminuye su energia
    public void perseguirCola(){
        System.out.println(getNombre() + " esta persiguiendo su cola.");
    }

    public int getNivelLadridos(){
        return nivelLadridos;
    }

    @Override
    public void realizarComportamientoUnico(){
        ladrar();
    }

    @Override
    protected void aplicarEfectoAlimento(TipoAlimento alimento) {
        switch (alimento) {
            case CROQUETAS:
                System.out.println(getNombre() + " esta comiendo sus croquetas favoritas.");
                break;
            case FRUTA:
                System.out.println(getNombre() + " esta comiendo frutita.");
                break;
            case PREMIO:
                System.out.println(getNombre() + " esta disfrutando de un dulce");
                break;
        }
    }

    @Override
    protected void aplicarEfectoJuego(TipoJuego juego) {
        switch (juego) {
            case PELOTA:
                System.out.println(getNombre() + " esta corriendo detrás de su pelota.");
                break;
            case PASEO:
                System.out.println(getNombre() + " salio a dar un paseo al parque.");
                break;
            case ESCONDIDAS:
                System.out.println(getNombre() + " esta en su escondite esperando ser encontrado.");
                break;
            case SALTOS:
                System.out.println(getNombre() + " esta practicando saltos.");
                break;
        }
    }



}