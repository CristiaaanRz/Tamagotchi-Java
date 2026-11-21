package org.uabc.ic.Modelo;

import java.util.Arrays;
import java.util.List;

public enum TipoJuego {
    PELOTA(Perro.class, Categoria.DESTREZA, "Traer la pelota", 22, 18, "corre detrás de su pelota"),
    PASEO(Perro.class, Categoria.EXPLORACION, "Pasear por el parque", 18, 14, "pasea por el parque"),
    BUSCAR_JUGUETE(Perro.class, Categoria.JUEGO_LIBRE, "Buscar el juguete", 13, 9,
            "encuentra su juguete detrás de un árbol"),

    PLUMA(Gato.class, Categoria.DESTREZA, "Cazar la pluma", 20, 15, "atrapa una pluma con la pata"),
    OVILLO(Gato.class, Categoria.JUEGO_LIBRE, "Jugar con el ovillo", 9, 6, "enreda el ovillo con las patas"),
    CAJA(Gato.class, Categoria.EXPLORACION, "Explorar una caja", 17, 11, "se esconde dentro de una caja"),

    PINTAR(Muneca.class, Categoria.DESTREZA, "Pintar con crayones", 12, 7, "dibuja con sus crayones"),
    COCHECITO(Muneca.class, Categoria.EXPLORACION, "Pasear en cochecito", 20, 11, "pasea en su cochecito"),
    HORA_DEL_TE(Muneca.class, Categoria.JUEGO_LIBRE, "Jugar al té", 23, 15, "prepara una mesa para el té"),

    MIMBRE(Cuyo.class, Categoria.DESTREZA, "Rodar la bola de mimbre", 14, 10,
            "empuja una bola de mimbre con el hocico"),
    CORRAL(Cuyo.class, Categoria.EXPLORACION, "Explorar el corral", 11, 8, "explora su corral"),
    TUNEL(Cuyo.class, Categoria.JUEGO_LIBRE, "Cruzar un túnel", 21, 13, "corre por un túnel");

    public enum Categoria {
        DESTREZA, EXPLORACION, JUEGO_LIBRE
    }

    private final Class<? extends Tamagotchi> especie;
    private final Categoria categoria;
    private final String nombreVisible;
    private final int felicidad;
    private final int energia;
    private final String descripcion;

    TipoJuego(Class<? extends Tamagotchi> especie, Categoria categoria, String nombreVisible,
              int felicidad, int energia, String descripcion) {
        this.especie = especie;
        this.categoria = categoria;
        this.nombreVisible = nombreVisible;
        this.felicidad = felicidad;
        this.energia = energia;
        this.descripcion = descripcion;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    int getFelicidad() {
        return felicidad;
    }

    int getEnergia() {
        return energia;
    }

    String getDescripcion() {
        return descripcion;
    }

    boolean esPara(Tamagotchi mascota) {
        return especie.isInstance(mascota);
    }

    static List<TipoJuego> para(Tamagotchi mascota) {
        return Arrays.stream(values()).filter(juego -> juego.esPara(mascota)).toList();
    }
}
