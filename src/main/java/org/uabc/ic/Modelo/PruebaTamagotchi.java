
package org.uabc.ic.Modelo;

public class PruebaTamagotchi {

    public static void main(String[] args) {

        Perro perro = new Perro("Ramiro");

        // Estado inicial
        System.out.println("=== ESTADO INICIAL ===");
        System.out.println(perro.mostrarEstado());

        // Simular el paso del tiempo
        System.out.println("\n=== PASO DEL TIEMPO ===");

        for (int i = 0; i < 5; i++) {
            perro.pasarTiempo();
        }

        System.out.println(perro.mostrarEstado());

        // Alimentar
        System.out.println("\n=== ALIMENTANDO ===");
        perro.alimentar(TipoAlimento.CROQUETAS);
        System.out.println(perro.mostrarEstado());

        // Jugar
        System.out.println("\n=== JUGANDO ===");
        perro.jugar(TipoJuego.PELOTA);
        System.out.println(perro.mostrarEstado());

        // Dormir
        System.out.println("\n=== DURMIENDO ===");
        perro.dormir();
        System.out.println(perro.mostrarEstado());

        // Intentar jugar dormido
        System.out.println("\n=== INTENTAR JUGAR DORMIDO ===");
        perro.jugar(TipoJuego.PASEO);

        // Despertar
        System.out.println("\n=== DESPERTANDO ===");
        perro.despertar();
        System.out.println(perro.mostrarEstado());

        // Comportamiento exclusivo
        System.out.println("\n=== COMPORTAMIENTO UNICO ===");
        perro.realizarComportamientoUnico();
        System.out.println("Ladridos: " + perro.getNivelLadridos());





        System.out.println("\n=== PRUEBA DE COLECCION ===");

        Coleccion coleccion = new Coleccion();

        coleccion.agregar(new Perro("Ramire"));
        coleccion.agregar(new Gato("Michi"));
        coleccion.agregar(new Muneca("Ana"));
        coleccion.agregar(new Cuyo("Pelusa"));

        System.out.println("Mascotas creadas: " + coleccion.cantidad());

        for (int i = 0; i < coleccion.cantidad(); i++) {
            Tamagotchi mascota = coleccion.obtener(i);

            System.out.println(
                    (i + 1) + ". " + mascota.getNombre() + " - " + mascota.getClass().getSimpleName());
        }

    }
}
