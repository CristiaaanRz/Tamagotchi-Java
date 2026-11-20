package org.uabc.ic;

import org.uabc.ic.Controlador.ControladorTamagotchi;
import org.uabc.ic.Vista.VistaConsola;

public class Main {

    public static void main(String[] args) {

        ControladorTamagotchi controlador = new ControladorTamagotchi();

        VistaConsola vista = new VistaConsola(controlador);

        vista.iniciar();
    }
}

