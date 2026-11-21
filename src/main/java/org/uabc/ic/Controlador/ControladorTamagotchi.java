package org.uabc.ic.Controlador;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

import org.uabc.ic.Modelo.Coleccion;
import org.uabc.ic.Modelo.Cuyo;
import org.uabc.ic.Modelo.EstadoAnimo;
import org.uabc.ic.Modelo.Gato;
import org.uabc.ic.Modelo.Muneca;
import org.uabc.ic.Modelo.Perro;
import org.uabc.ic.Modelo.Tamagotchi;
import org.uabc.ic.Modelo.TipoAlimento;
import org.uabc.ic.Modelo.TipoJuego;

public class ControladorTamagotchi {
    private enum Interaccion {
        ALIMENTAR, JUGAR, DORMIR, DESPERTAR, COMPORTAMIENTO_ESPECIAL
    }

    private final Coleccion coleccion = new Coleccion();
    private final Consumer<String> receptorMensajes;

    public ControladorTamagotchi() {
        this(System.out::println);
    }

    public ControladorTamagotchi(Consumer<String> receptorMensajes) {
        this.receptorMensajes = Objects.requireNonNull(receptorMensajes);
    }

    public Tamagotchi obtenerMascota(int indice) {
        return coleccion.obtener(indice);
    }

    public int cantidadMascotas() {
        return coleccion.cantidad();
    }

    public List<Tamagotchi> obtenerMascotas() {
        return coleccion.obtenerMascotas();
    }

    public boolean crearMascota(String tipo, String nombre) {
        if (nombre == null || nombre.isBlank()) {
            return informarError("Escribe un nombre para la mascota.");
        }

        String tipoNormalizado = tipo == null ? "" : tipo.strip().toLowerCase(Locale.ROOT);
        Tamagotchi mascota = switch (tipoNormalizado) {
            case "perro" -> new Perro(nombre);
            case "gato" -> new Gato(nombre);
            case "muneca", "muñeca" -> new Muneca(nombre);
            case "cuyo" -> new Cuyo(nombre);
            default -> null;
        };
        if (mascota == null) {
            return informarError("Elige un tipo de mascota válido.");
        }

        mascota.setReceptorMensajes(receptorMensajes);
        coleccion.agregar(mascota);
        receptorMensajes.accept("Se creó la mascota " + mascota.getNombre() + ".");
        return true;
    }

    public boolean alimentarMascota(int indice, TipoAlimento alimento) {
        if (alimento == null) {
            return informarError("Selecciona un alimento.");
        }
        Tamagotchi mascota = obtenerMascota(indice);
        if (mascota != null && !mascota.obtenerAlimentos().contains(alimento)) {
            return informarError("Ese alimento no corresponde a " + mascota.getNombre() + ".");
        }
        return interactuar(mascota, Interaccion.ALIMENTAR, seleccionada -> seleccionada.alimentar(alimento));
    }

    public boolean jugarConMascota(int indice, TipoJuego juego) {
        if (juego == null) {
            return informarError("Selecciona un juego.");
        }
        Tamagotchi mascota = obtenerMascota(indice);
        if (mascota != null && !mascota.obtenerJuegos().contains(juego)) {
            return informarError("Ese juego no corresponde a " + mascota.getNombre() + ".");
        }
        return interactuar(mascota, Interaccion.JUGAR, seleccionada -> seleccionada.jugar(juego));
    }

    public boolean dormirMascota(int indice) {
        return interactuar(obtenerMascota(indice), Interaccion.DORMIR, Tamagotchi::dormir);
    }

    public boolean despertarMascota(int indice) {
        return interactuar(obtenerMascota(indice), Interaccion.DESPERTAR, Tamagotchi::despertar);
    }

    public boolean realizarComportamientoEspecial(int indice) {
        return interactuar(obtenerMascota(indice), Interaccion.COMPORTAMIENTO_ESPECIAL, Tamagotchi::realizarComportamientoUnico);
    }

    private boolean interactuar(Tamagotchi mascota, Interaccion interaccion, Consumer<Tamagotchi> accion) {
        if (mascota == null) {
            return informarError("Selecciona una mascota válida.");
        }
        if (!mascota.estaVivo()) {
            return informarError(mascota.getNombre() + " ha muerto. Causa: "
                    + mascota.getDescripcionMuerte() + ".");
        }
        boolean vaADespertar = interaccion == Interaccion.DESPERTAR;
        if (mascota.isDurmiendo() != vaADespertar) {
            return informarError(mascota.getNombre()
                    + (vaADespertar ? " ya está despierta." : " está durmiendo."));
        }
        if (interaccion == Interaccion.JUGAR && !mascota.puedeJugar()) {
            return informarError(mascota.getNombre() + " necesita descansar antes de jugar.");
        }
        EstadoAnimo estadoAnterior = mascota.obtenerEstadoAnimo();
        accion.accept(mascota);
        if (interaccion == Interaccion.DORMIR) {
            mascota.avanzarDia();
            receptorMensajes.accept(mascota.getNombre() + " comienza un nuevo día.");
        }
        informarCambioEstado(mascota, estadoAnterior);
        return true;
    }

    private void informarCambioEstado(Tamagotchi mascota, EstadoAnimo estadoAnterior) {
        EstadoAnimo estado = mascota.obtenerEstadoAnimo();
        if (estado == EstadoAnimo.MUERTO) {
            receptorMensajes.accept(mascota.getNombre() + " ha muerto. Causa: "
                    + mascota.getDescripcionMuerte() + ".");
        } else if (estado != estadoAnterior && estado != EstadoAnimo.BIEN) {
            receptorMensajes.accept(mascota.getNombre() + ": " + estado.getMensaje());
        }
    }

    private boolean informarError(String mensaje) {
        receptorMensajes.accept(mensaje);
        return false;
    }
}
