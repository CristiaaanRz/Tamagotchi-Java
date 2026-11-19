package org.uabc.ic.Vista;

import java.util.Scanner;

    public class VistaConsola {
        Scanner leerNumeros = new Scanner(System.in);
        Scanner leerCadenas = new Scanner(System.in);

        public void mostrarDatos(String cadena){
            System.out.println(cadena);
        }

        //Para leer y regresar datos tipo cadena
        public String leerCadena(String cadena){
            System.out.println(cadena);
            return leerCadenas.nextLine();
        }

        public int leerNumero(String cadena) {
            System.out.println(cadena);
            return leerNumeros.nextInt();
        }

        public int mostrarMenuPrincipal(){
            System.out.println("1.");
            System.out.println("2.");
            System.out.println("3.");
            return leerNumeros.nextInt();
    }

    public int mostrarOpcionesTamagotchis(){
            System.out.println("Ingrese el numero de la opcion");
            System.out.println("1.");
            System.out.println("2.");
            System.out.println("3.");
            return leerNumeros.nextInt();
    }

}
