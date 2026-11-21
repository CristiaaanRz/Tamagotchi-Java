package org.uabc.ic.Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;

public abstract class Tamagotchi implements TamagotchiActions {
    private static final int EDAD_MAXIMA = 60;
    private static final int DESGASTE_DIARIO = 10;
    private static final int AVISO_SIN_CUIDADO = 3;
    private static final int MARGEN_TEMPORAL_POR_COMIDA = 3;

    private final String nombre;
    private int hambre;
    private int felicidad = 100;
    private int energia = 100;
    private int edad;
    private int diasSinJugar;
    private int diasSinDormir;
    private boolean jugoHoy;
    private boolean durmiendo;
    private boolean descansoAplicadoHoy;
    private CausaMuerte causaMuerte;
    private Consumer<String> receptorMensajes = System.out::println;

    protected Tamagotchi(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio.");
        }
        this.nombre = nombre.strip();
    }

    public String getNombre() {
        return nombre;
    }

    public String getTipoVisible() {
        return getClass().getSimpleName();
    }

    public int getHambre() {
        return hambre;
    }

    public int getFelicidad() {
        return felicidad;
    }

    public int getEnergia() {
        return energia;
    }

    /** Límite base por aburrimiento, antes de la recuperación temporal por comer. */
    public int getFelicidadMaxima() {
        return maximoPorCuidado(diasSinJugar);
    }

    /** Límite base por cansancio, antes de la recuperación temporal por comer. */
    public int getEnergiaMaxima() {
        return maximoPorCuidado(diasSinDormir);
    }

    private static int maximoPorCuidado(int diasSinCuidado) {
        return Math.max(0, 100 - diasSinCuidado * DESGASTE_DIARIO);
    }

    public int getEdad() {
        return edad;
    }

    public int getDiasSinJugar() {
        return diasSinJugar;
    }

    public int getDiasSinDormir() {
        return diasSinDormir;
    }

    public List<TipoAlimento> obtenerAlimentos() {
        return TipoAlimento.para(this);
    }

    public List<TipoJuego> obtenerJuegos() {
        return TipoJuego.para(this);
    }

    public List<String> obtenerAvisosDeCuidado() {
        if (!estaVivo()) {
            return List.of();
        }
        List<String> avisos = new ArrayList<>(3);
        if (hambre >= 80) {
            avisos.add("Necesita comer.");
        }
        if (diasSinJugar >= AVISO_SIN_CUIDADO || felicidad <= 20) {
            avisos.add("Necesita jugar.");
        }
        if (diasSinDormir >= AVISO_SIN_CUIDADO || energia <= 20) {
            avisos.add("Necesita dormir.");
        }
        return avisos;
    }

    public boolean isDurmiendo() {
        return durmiendo;
    }

    public boolean estaVivo() {
        return causaMuerte == null;
    }

    public CausaMuerte getCausaMuerte() {
        return causaMuerte;
    }

    public String getDescripcionMuerte() {
        if (causaMuerte == null) {
            return "";
        }
        if (causaMuerte == CausaMuerte.VEJEZ) {
            return causaMuerte.getDescripcion();
        }
        List<String> motivos = new ArrayList<>(3);
        if (hambre == 100) {
            motivos.add("hambre al máximo");
        }
        if (energia == 0) {
            motivos.add("energía en cero");
        }
        if (felicidad == 0) {
            motivos.add("felicidad en cero");
        }
        return causaMuerte.getDescripcion() + " (" + String.join(", ", motivos) + ")";
    }

    public void setReceptorMensajes(Consumer<String> receptorMensajes) {
        this.receptorMensajes = Objects.requireNonNull(receptorMensajes);
    }

    protected void emitirMensaje(String mensaje) {
        receptorMensajes.accept(mensaje);
    }

    @Override
    public EstadoAnimo obtenerEstadoAnimo() {
        if (!estaVivo()) {
            return EstadoAnimo.MUERTO;
        }
        if (hambre >= 80 || energia <= 20 || felicidad <= 20) {
            return EstadoAnimo.MAL;
        }
        if (hambre >= 40 || energia <= 60 || felicidad <= 60
                || diasSinJugar >= AVISO_SIN_CUIDADO || diasSinDormir >= AVISO_SIN_CUIDADO) {
            return EstadoAnimo.NECESITA_ATENCION;
        }
        return EstadoAnimo.BIEN;
    }

    public void avanzarDia() {
        if (!estaVivo()) {
            return;
        }

        edad++;
        modificarHambre(10);
        if (!jugoHoy) {
            diasSinJugar++;
            felicidad = Math.clamp(felicidad - DESGASTE_DIARIO, 0, getFelicidadMaxima());
        }
        jugoHoy = false;
        if (!durmiendo) {
            diasSinDormir++;
            energia = Math.clamp(energia - DESGASTE_DIARIO, 0, getEnergiaMaxima());
        } else if (!descansoAplicadoHoy) {
            modificarEnergia(energiaRecuperadaAlDormir());
        }
        descansoAplicadoHoy = false;
        verificarEstadoVital();
    }

    private void verificarEstadoVital() {
        if (hambre == 100 || energia == 0 || felicidad == 0) {
            morir(CausaMuerte.DESCUIDO);
        } else if (edad >= EDAD_MAXIMA) {
            morir(CausaMuerte.VEJEZ);
        }
    }

    private void morir(CausaMuerte causa) {
        causaMuerte = causa;
        durmiendo = false;
    }

    @Override
    public void alimentar(TipoAlimento alimento) {
        if (!estaVivo() || durmiendo || alimento == null || !alimento.esPara(this)) {
            return;
        }
        modificarHambre(-20);
        felicidad = recuperarConComida(felicidad, 5, getFelicidadMaxima());
        aplicarEfectoAlimento(alimento);
    }

    @Override
    public void jugar(TipoJuego juego) {
        if (!puedeJugar() || juego == null || !juego.esPara(this)) {
            return;
        }
        diasSinJugar = 0;
        jugoHoy = true;
        modificarHambre(10);
        aplicarEfectoJuego(juego);
        verificarEstadoVital();
    }

    public boolean puedeJugar() {
        return estaVivo() && !durmiendo && energia > 20;
    }

    @Override
    public void dormir() {
        if (!estaVivo() || durmiendo) {
            return;
        }
        durmiendo = true;
        diasSinDormir = 0;
        descansoAplicadoHoy = true;
        modificarEnergia(energiaRecuperadaAlDormir());
        emitirMensaje(nombre + " se ha quedado dormido.");
    }

    public void despertar() {
        if (!estaVivo() || !durmiendo) {
            return;
        }
        durmiendo = false;
        emitirMensaje(nombre + " ha despertado.");
    }

    @Override
    public String mostrarEstado() {
        String estado = "=== ESTADO DE " + nombre + " ===\n"
                + "Edad: " + edad + (edad == 1 ? " día\n" : " días\n")
                + "Hambre: " + hambre + "/100\n"
                + "Energía: " + energia + "/100\n"
                + "Felicidad: " + felicidad + "/100\n"
                + "Durmiendo: " + (durmiendo ? "Sí" : "No") + "\n"
                + "Días sin jugar: " + diasSinJugar + "\n"
                + "Días sin dormir: " + diasSinDormir + "\n"
                + "Estado: " + obtenerEstadoAnimo().getMensaje();
        if (getEnergiaMaxima() < 100) {
            estado += "\nLímite base de energía por cansancio: " + getEnergiaMaxima() + "/100.";
        }
        if (getFelicidadMaxima() < 100) {
            estado += "\nLímite base de felicidad por aburrimiento: " + getFelicidadMaxima() + "/100.";
        }
        for (String aviso : obtenerAvisosDeCuidado()) {
            estado += "\n" + aviso;
        }
        if (causaMuerte != null) {
            estado += "\nCausa de muerte: " + getDescripcionMuerte();
        }
        return estado;
    }

    protected void modificarHambre(int cantidad) {
        hambre = Math.clamp(hambre + cantidad, 0, 100);
    }

    protected void modificarFelicidad(int cantidad) {
        felicidad = Math.clamp(felicidad + cantidad, 0, Math.max(felicidad, getFelicidadMaxima()));
    }

    protected void modificarEnergia(int cantidad) {
        energia = Math.clamp(energia + cantidad, 0, Math.max(energia, getEnergiaMaxima()));
    }

    private static int recuperarConComida(int nivel, int ganancia, int limiteBase) {
        int limiteConComida = Math.min(100, limiteBase + MARGEN_TEMPORAL_POR_COMIDA);
        return Math.min(nivel + ganancia, limiteConComida);
    }

    protected int energiaRecuperadaAlDormir() {
        return 40;
    }

    protected void aplicarEfectoAlimento(TipoAlimento alimento) {
        energia = recuperarConComida(energia, alimento.getEnergia(), getEnergiaMaxima());
        emitirMensaje(nombre + " come " + alimento.getNombreVisible().toLowerCase(Locale.ROOT) + ".");
    }

    protected void aplicarEfectoJuego(TipoJuego juego) {
        modificarFelicidad(juego.getFelicidad());
        modificarEnergia(-juego.getEnergia());
        emitirMensaje(nombre + " " + juego.getDescripcion() + ".");
    }

    public abstract void realizarComportamientoUnico();
}
