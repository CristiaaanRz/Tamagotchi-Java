package org.uabc.ic.Controlador;

import org.uabc.ic.Modelo.Coleccion;
import org.uabc.ic.Modelo.Tamagotchi;
import org.uabc.ic.Modelo.Perro;
import org.uabc.ic.Modelo.Gato;
import org.uabc.ic.Modelo.Muneca;
import org.uabc.ic.Modelo.Cuyo;
import org.uabc.ic.Modelo.TipoAlimento;
import org.uabc.ic.Modelo.TipoJuego;

import java.util.List;

public class ControladorTamagotchi {

    private Coleccion coleccion;

    public ControladorTamagotchi() {
        this.coleccion = new Coleccion();
    }

    public Tamagotchi obtenerMascota(int indice) {
        return coleccion.obtener(indice);
    }

    public int cantidadMascotas() {
        return coleccion.cantidad();
    }




    public boolean crearMascota(String tipo, String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        Tamagotchi mascota;

        switch (tipo.toLowerCase()) {
            case "perro":
                mascota = new Perro(nombre);
                break;
            case "gato":
                mascota = new Gato(nombre);
                break;
            case "muneca":
                mascota = new Muneca(nombre);
                break;
            case "cuyo":
                mascota = new Cuyo(nombre);
                break;
            default:
                return false;
        }
        coleccion.agregar(mascota);
        return true;
    }



    public boolean alimentarMascota(int indice, TipoAlimento alimento) {
        Tamagotchi mascota = coleccion.obtener(indice);

        if (mascota == null || alimento == null || mascota.isDurmiendo()){
            return false;
        }
        mascota.alimentar(alimento);
        return true;
    }

    public boolean jugarConMascota(int indice, TipoJuego juego) {
        Tamagotchi mascota = coleccion.obtener(indice);

        if (mascota == null || juego == null || mascota.isDurmiendo() || mascota.getEnergia() <= 20){
            return false;
        }
        mascota.jugar(juego);
        return true;
    }

    public boolean dormirMascota(int indice){
        Tamagotchi mascota = coleccion.obtener(indice);

        if (mascota == null || mascota.isDurmiendo()){
            return false;
        }
        mascota.dormir();
        return true;
    }

    public boolean despertarMascota(int indice){
        Tamagotchi mascota = coleccion.obtener(indice);

        if (mascota == null || !mascota.isDurmiendo()){
            return false;
        }
        mascota.despertar();
        return true;
    }




    public List<Tamagotchi> obtenerMascotas(){
        return coleccion.obtenerMascotas();
    }

}