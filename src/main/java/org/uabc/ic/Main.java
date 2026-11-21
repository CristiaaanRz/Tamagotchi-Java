package org.uabc.ic;

import org.uabc.ic.Controlador.ControladorTamagotchi;
import org.uabc.ic.Vista.VistaConsola;
import org.uabc.ic.Vista.VistaTamagotchiFX;

public class Main {
    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("--consola")) {
            new VistaConsola(new ControladorTamagotchi()).iniciar();
            return;
        }
        if (args.length > 0) {
            System.err.println("Uso: Main [--consola]");
            return;
        }
        VistaTamagotchiFX.main(args);
    }
}

