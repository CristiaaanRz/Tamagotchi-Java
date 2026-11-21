package org.uabc.ic.Modelo;

import java.util.Arrays;
import java.util.List;

public enum TipoAlimento {
    CROQUETAS(Perro.class, Categoria.PRINCIPAL, "Croquetas", 12),
    POLLO_COCIDO(Perro.class, Categoria.COMPLEMENTO, "Pollo cocido", 7),
    GALLETA_CANINA(Perro.class, Categoria.PREMIO, "Galleta canina", 4),

    ATUN(Gato.class, Categoria.PRINCIPAL, "Atún", 10),
    PATE_FELINO(Gato.class, Categoria.COMPLEMENTO, "Paté para gatos", 5),
    SALMON(Gato.class, Categoria.PREMIO, "Bocaditos de salmón", 8),

    LECHE(Muneca.class, Categoria.PRINCIPAL, "Leche", 3),
    COMPOTA(Muneca.class, Categoria.COMPLEMENTO, "Compota de manzana", 8),
    GALLETA_DE_AVENA(Muneca.class, Categoria.PREMIO, "Galleta de avena", 10),

    HENO(Cuyo.class, Categoria.PRINCIPAL, "Heno", 6),
    PIMIENTO(Cuyo.class, Categoria.COMPLEMENTO, "Pimiento rojo", 11),
    PEPINO(Cuyo.class, Categoria.PREMIO, "Pepino", 3);

    public enum Categoria {
        PRINCIPAL, COMPLEMENTO, PREMIO
    }

    private final Class<? extends Tamagotchi> especie;
    private final Categoria categoria;
    private final String nombreVisible;
    private final int energia;

    TipoAlimento(Class<? extends Tamagotchi> especie, Categoria categoria, String nombreVisible, int energia) {
        this.especie = especie;
        this.categoria = categoria;
        this.nombreVisible = nombreVisible;
        this.energia = energia;
    }

    public String getNombreVisible() {
        return nombreVisible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    int getEnergia() {
        return energia;
    }

    boolean esPara(Tamagotchi mascota) {
        return especie.isInstance(mascota);
    }

    static List<TipoAlimento> para(Tamagotchi mascota) {
        return Arrays.stream(values()).filter(alimento -> alimento.esPara(mascota)).toList();
    }
}
