package org.uabc.ic.Modelo;

public abstract class Tamagotchi
        implements TamagotchiActions {

    private String nombre;
    private int hambre;
    private int felicidad;
    private int energia;
    private boolean durmiendo;

    // Solo un parámetro (nombre) para que el usuario pueda elegir el nombre su mascota.
    public Tamagotchi(String nombre) {
        this.nombre = nombre;
        this.hambre = 0;
        this.felicidad = 100;
        this.energia = 100;
        this.durmiendo = false;
    }

    /**
     * Getters
     * @return
     */

    public String getNombre(){
        return nombre;
    }
    public int getHambre(){
        return hambre;
    }
    public int getFelicidad(){
        return felicidad;
    }
    public int getEnergia(){
        return energia;
    }
    public boolean isDurmiendo(){
        return durmiendo;
    }



    // Protected para que las clases hijas puedan usar el método, pero no las demás clases
    protected int limitar(int valor, int min, int max){
        if (valor<min){
            return min;
        }
        if (valor>max){
            return max;
        }
        return valor;
    }

    //REGLAS:
    //MAL	Hambre ≥ 80, energía ≤ 20 o felicidad ≤ 20
    //NECESITA_ATENCION	Hambre ≥ 40, energía ≤ 60 o felicidad ≤ 60
    //BIEN	Ninguna de las condiciones anteriores
    @Override
    public EstadoAnimo obtenerEstadoAnimo(){
        if (hambre >= 80 || energia <= 20 || felicidad <= 20){
            return EstadoAnimo.MAL;
        }
        if (hambre >= 40 || energia <= 60 || felicidad <= 60){
            return EstadoAnimo.NECESITA_ATENCION;
        }
        return EstadoAnimo.BIEN;
    }


    public void pasarTiempo() {
        if (durmiendo) {
            return;
        }

        modificarHambre(10);
        modificarEnergia(-5);
        modificarFelicidad(-5);
    }


    @Override
    public void alimentar(TipoAlimento alimento){
        if (durmiendo){
            System.out.println(nombre + " esta durmiendo. No puede comer.");
            return;
        }
        hambre =  limitar(hambre - 20, 0, 100);
        felicidad =  limitar(felicidad + 5, 0, 100);
        aplicarEfectoAlimento(alimento);
    }

    @Override
    public void jugar(TipoJuego juego) {
        if (durmiendo){
            System.out.println(nombre + " esta durmiendo. No puede jugar.");
            return;
        }
        if (energia <= 20){
            System.out.println(nombre + " esta demasiado cansado/a para jugar.");
            return;
        }
        aplicarEfectoJuego(juego);
    }

    @Override
    public void dormir() {
        if (durmiendo) {
            System.out.println(nombre + " ya esta durmiendo.");
            return;
        }

        durmiendo = true;
        modificarEnergia(40);
        System.out.println(nombre + " se ha quedado dormido.");
    }
    public void despertar() {
        if (!durmiendo) {
            System.out.println(nombre + " ya esta despierto.");
            return;
        }

        durmiendo = false;
        System.out.println(nombre + " ha despertado.");
    }


    @Override
    public String mostrarEstado(){
        return "=== ESTADO DE " + nombre + " ===\n"
                + "Hambre: " + hambre + "/100\n"
                + "Energia: " + energia + "/100\n"
                + "Felicidad: " + felicidad + "/100\n"
                + "Durmiendo: " + (durmiendo ? "Si" : "No") + "\n"
                + "Estado de animo: " + obtenerEstadoAnimo().getMensaje();
    }


    // Herramientas auxiliares para modificar atributos
    protected void modificarHambre(int cantidad){
        hambre = limitar(hambre + cantidad, 0, 100);
    }
    protected void modificarFelicidad(int cantidad){
        felicidad = limitar(felicidad + cantidad, 0, 100);
    }
    protected void modificarEnergia(int cantidad){
        energia = limitar(energia + cantidad, 0, 100);
    }





    // Todas las mascotas deben tener estos métodos, aunque no todas interactúen igual.
    protected abstract void aplicarEfectoAlimento(TipoAlimento alimento);

    protected abstract void aplicarEfectoJuego(TipoJuego juego);

    public abstract void realizarComportamientoUnico();
}