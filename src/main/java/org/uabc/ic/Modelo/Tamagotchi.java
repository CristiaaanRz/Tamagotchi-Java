package org.uabc.ic.Modelo;

public class Tamagotchi {

    protected String nombre;
    protected int hambre;
    protected int salud;
    protected int felicidad;
    protected int energia;

    public Tamagotchi(String nombre, int hambre, int salud, int felicidad, int energia) {
        this.nombre = nombre;
        this.hambre = hambre;
        this.salud = salud;
        this.felicidad = felicidad;
        this.energia = energia;
    }
    public Tamagotchi() {
        this.nombre = "Ramiro";
        this.hambre = 0;
        this.salud = 100;
        this.felicidad = 100;
        this.energia = 100;
    }
    }

}
