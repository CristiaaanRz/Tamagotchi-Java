package org.uabc.ic.Controlador;

import org.uabc.ic.Modelo.Coleccion;
import org.uabc.ic.Vista.VistaConsola;

public class ControladorTamagotchi {
    private VistaConsola vista;
    private Coleccion modelo;

    public ControladorTamagotchi(VistaConsola vista, Coleccion modelo) {
        this.modelo=modelo;
        this.vista=vista;
    }

    public void iniciar(){
        int op;
        int tp;
        do{
            op = vista.mostrarMenuPrincipal();

            switch (op){
                case 1:
                    System.out.println("Leer informacion del Tamagotchi");
                    tp = vista.mostrarOpcionesTamagotchis();
                    String nombre = vista.leerCadena("Nombre del Tamagotchi");
                    int hambre = vista.leerNumero("Hambre del Tamagotchi");

                    switch (tp) {
                        case 1: //perro
                            int agresivo = vista.leerNumero("Agresividad del Tamagotchi");
                                    int hiper =  vista.leerNumero("Hiperactividad del Tamagotchi");

                            break;
                        case 2: // cuyo
                            break;
                        case 3: // muneca
                            break;
                    }
                case 2:
                    System.out.println("Mostrar lista de Tamagotchis");
                    vista.mostrarDatos(modelo.listaJuguetes());
                    break;
                    case 3:
                        break;

            }
        }
    }
}

