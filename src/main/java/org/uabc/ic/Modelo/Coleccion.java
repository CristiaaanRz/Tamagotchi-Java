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
        if (indice < 0 || indice >= mascotas.size()){
            return null;
        }
        return mascotas.get(indice);
    }

    public ArrayList<Tamagotchi> obtenerMascotas(){
        return new ArrayList<>(mascotas);   // Se copia la lista, mas no los objetos Tamagotchi.
    }
}
