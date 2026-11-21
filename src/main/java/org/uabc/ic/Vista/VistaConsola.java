package org.uabc.ic.Vista;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

import org.uabc.ic.Controlador.ControladorTamagotchi;
import org.uabc.ic.Modelo.Tamagotchi;
import org.uabc.ic.Modelo.TipoAlimento;
import org.uabc.ic.Modelo.TipoJuego;

public class VistaConsola {
    private final Scanner scanner = new Scanner(System.in);
    private final ControladorTamagotchi controlador;

    public VistaConsola(ControladorTamagotchi controlador) {
        this.controlador = controlador;
    }

    public void mostrarMenuPrincipal() {
        System.out.println("""

                === SIMULADOR TAMAGOTCHI ===
                1. Crear mascota
                2. Ver mascotas
                3. Interactuar con mascota
                4. Salir""");
    }

    private int leerOpcion(int minimo, int maximo) {
        while (true) {
            String entrada = leerTexto("Selecciona una opción:");
            if (entrada == null) {
                return -1;
            }
            try {
                int opcion = Integer.parseInt(entrada.strip());
                if (opcion >= minimo && opcion <= maximo) {
                    return opcion;
                }
            } catch (NumberFormatException excepcion) {
                // Se solicita de nuevo la opción cuando la entrada no es numérica.
            }
            System.out.printf("Ingresa un número entre %d y %d.%n", minimo, maximo);
        }
    }

    public String leerTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    public void mostrarMascotas(List<Tamagotchi> mascotas) {
        if (mascotas.isEmpty()) {
            System.out.println("No hay mascotas registradas.");
            return;
        }

        System.out.println("\n--- MIS MASCOTAS ---");
        for (int i = 0; i < mascotas.size(); i++) {
            Tamagotchi mascota = mascotas.get(i);
            System.out.printf("%d. %s (%s, %d %s). %s%n", i + 1, mascota.getNombre(),
                    mascota.getTipoVisible(), mascota.getEdad(), mascota.getEdad() == 1 ? "día" : "días",
                    mascota.obtenerEstadoAnimo().getMensaje());
        }
    }

    private void crearMascota() {
        System.out.println("\n--- CREAR MASCOTA ---");
        String tipo = leerTexto("Tipo (Perro, Gato, Muñeca o Cuyo):");
        if (tipo == null) {
            return;
        }
        String nombre = leerTexto("Nombre:");
        if (nombre != null) {
            controlador.crearMascota(tipo, nombre);
        }
    }

    private void interactuarConMascota() {
        List<Tamagotchi> mascotas = controlador.obtenerMascotas();
        mostrarMascotas(mascotas);
        if (mascotas.isEmpty()) {
            return;
        }

        System.out.println("0. Volver");
        int seleccion = leerOpcion(0, mascotas.size());
        if (seleccion <= 0) {
            return;
        }
        int indice = seleccion - 1;
        Tamagotchi mascota = controlador.obtenerMascota(indice);
        while (true) {
            System.out.println("\n" + mascota);
            if (!mascota.estaVivo()) {
                return;
            }
            System.out.println("""

                    1. Alimentar
                    2. Jugar
                    3. Dormir y comenzar el día siguiente
                    4. Despertar
                    5. Comportamiento especial
                    0. Volver""");
            switch (leerOpcion(0, 5)) {
                case 1 -> {
                    TipoAlimento alimento = elegir(mascota.obtenerAlimentos(), TipoAlimento::getNombreVisible);
                    if (alimento != null) {
                        controlador.alimentarMascota(indice, alimento);
                    }
                }
                case 2 -> {
                    TipoJuego juego = elegir(mascota.obtenerJuegos(), TipoJuego::getNombreVisible);
                    if (juego != null) {
                        controlador.jugarConMascota(indice, juego);
                    }
                }
                case 3 -> controlador.dormirMascota(indice);
                case 4 -> controlador.despertarMascota(indice);
                case 5 -> controlador.realizarComportamientoEspecial(indice);
                default -> {
                    return;
                }
            }
        }
    }

    private <T> T elegir(List<T> opciones, Function<T, String> nombreVisible) {
        for (int i = 0; i < opciones.size(); i++) {
            System.out.printf("%d. %s%n", i + 1, nombreVisible.apply(opciones.get(i)));
        }
        System.out.println("0. Cancelar");
        int opcion = leerOpcion(0, opciones.size());
        return opcion <= 0 ? null : opciones.get(opcion - 1);
    }

    public void iniciar() {
        while (true) {
            mostrarMenuPrincipal();
            switch (leerOpcion(1, 4)) {
                case 1 -> crearMascota();
                case 2 -> mostrarMascotas(controlador.obtenerMascotas());
                case 3 -> interactuarConMascota();
                default -> {
                    System.out.println("Saliendo del programa...");
                    return;
                }
            }
        }
    }
}
