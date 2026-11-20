package org.uabc.ic.Modelo;

import java.util.ArrayList;

public class Coleccion {
    private ArrayList<Tamagotchi> mascotas;

    public Coleccion(){
        mascotas = new ArrayList<>();
    }

    public void agregar(Tamagotchi mascota){
        mascotas.add(mascota);
    }

    public int cantidad(){
        return mascotas.size();
    }

    public Tamagotchi obtener(int indice){
        return mascotas.get(indice);
    }
}
