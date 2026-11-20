package org.uabc.ic.Vista;

import java.util.Scanner;
import java.util.List;

import org.uabc.ic.Modelo.Tamagotchi;
import org.uabc.ic.Controlador.ControladorTamagotchi;

public class VistaConsola {
    private Scanner scanner;
    private ControladorTamagotchi controlador;

    public VistaConsola(ControladorTamagotchi controlador) {
        this.scanner = new Scanner(System.in);
        this.controlador = controlador;
    }


    public void mostrarMenuPrincipal() {
        System.out.println("\n=== SIMULADOR TAMAGOTCHI ===");
        System.out.println("1. Crear mascota");
        System.out.println("2. Ver mascotas");
        System.out.println("3. Interactuar con mascota");
        System.out.println("4. Salir");
    }


    public int leerOpcion() {
        System.out.println("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine();
        return opcion;
    }


    public String leerTexto(String mensaje) {
        System.out.println(mensaje);
        return scanner.nextLine();
    }


    public void mostrarMensaje(String mensaje) {
        System.out.println(mensaje);
    }


    public void mostrarMascotas(List<Tamagotchi> mascotas) {
        if (mascotas.isEmpty()) {
            mostrarMensaje("No tiene mascotas registradas.");
            return;
        }

        System.out.println("\n---Mis Mascotas---");
        for (int i = 0; i < mascotas.size(); i++) {
            Tamagotchi mascota = mascotas.get(i);

            System.out.println((i + 1) + ". " + mascota.getNombre() +
                    " - " + mascota.getClass().getSimpleName());
        }
    }


    private void crearMascota() {

        mostrarMensaje("\n--- CREAR MASCOTA ---");
        mostrarMensaje("Tipos disponibles: \nPerro, \nGato, \nMuñeca, \nCuyo\n");

        String tipo = leerTexto("Tipo: ");
        String nombre = leerTexto("Nombre: ");

        boolean creada = controlador.crearMascota(tipo, nombre);

        if (creada) {
            mostrarMensaje("Mascota creada correctamente.");
        } else {
            mostrarMensaje("No se pudo crear la mascota.");
        }
    }







    public void iniciar() {
        int opcion;

        do {
            mostrarMenuPrincipal();
            opcion = leerOpcion();

            switch (opcion) {
                case 1:
                    crearMascota();
                    break;
                case 2:
                    mostrarMascotas(controlador.obtenerMascotas());
                    break;
                case 3:
                    mostrarMensaje("en proceso brou, aqui va eso de alimentar, jugar, dormir y despertar...");
                    break;
                case 4:
                    mostrarMensaje("Saliendo del programa...");
                    break;
                default:
                    mostrarMensaje("Opcion no valida.");
            }
        } while (opcion != 4);
    }

}