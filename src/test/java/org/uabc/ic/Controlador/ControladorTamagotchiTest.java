package org.uabc.ic.Controlador;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.uabc.ic.Modelo.CausaMuerte;
import org.uabc.ic.Modelo.EstadoAnimo;
import org.uabc.ic.Modelo.Tamagotchi;
import org.uabc.ic.Modelo.TipoAlimento;
import org.uabc.ic.Modelo.TipoJuego;

class ControladorTamagotchiTest {
    @ParameterizedTest
    @ValueSource(strings = {"Perro", "Gato", "Muñeca", "Cuyo"})
    void alimentarConservaLosBeneficiosDeCadaAlimento(String tipo) {
        Set<Integer> gananciasDeEnergia = new HashSet<>();
        for (TipoAlimento alimento : crearControlador(tipo).obtenerMascota(0).obtenerAlimentos()) {
            ControladorTamagotchi controlador = prepararConDiasSinCuidado(tipo, 3);
            Tamagotchi mascota = controlador.obtenerMascota(0);
            String caso = tipo + " con " + alimento;
            assertTrue(controlador.jugarConMascota(0, mascota.obtenerJuegos().getLast()), caso);
            int hambreAntes = mascota.getHambre();
            int felicidadAntes = mascota.getFelicidad();
            int energiaAntes = mascota.getEnergia();

            assertTrue(controlador.alimentarMascota(0, alimento), caso);
            assertEquals(3, mascota.getEdad(), caso);
            assertTrue(mascota.getHambre() < hambreAntes, caso);
            assertTrue(mascota.getFelicidad() > felicidadAntes, caso);
            assertTrue(mascota.getEnergia() > energiaAntes, caso);
            assertTrue(gananciasDeEnergia.add(mascota.getEnergia() - energiaAntes), caso);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Perro", "Gato", "Muñeca", "Cuyo"})
    void comerRecuperaEnergiaYFelicidadEnElLimiteSinEvitarElDesgaste(String tipo) {
        for (TipoAlimento alimento : crearControlador(tipo).obtenerMascota(0).obtenerAlimentos()) {
            ControladorTamagotchi controlador = prepararConDiasSinCuidado(tipo, 1);
            Tamagotchi mascota = controlador.obtenerMascota(0);
            String caso = tipo + " con " + alimento;
            assertEquals(90, mascota.getEnergia(), caso);
            assertEquals(90, mascota.getFelicidad(), caso);

            assertTrue(controlador.alimentarMascota(0, alimento), caso);
            assertEquals(0, mascota.getHambre(), caso);
            assertEquals(93, mascota.getEnergia(), caso);
            assertEquals(93, mascota.getFelicidad(), caso);
            assertEquals(1, mascota.getEdad(), caso);

            assertTrue(controlador.alimentarMascota(0, alimento), caso);
            assertEquals(93, mascota.getEnergia(), caso);
            assertEquals(93, mascota.getFelicidad(), caso);

            mascota.avanzarDia();
            assertEquals(2, mascota.getEdad(), caso);
            assertEquals(80, mascota.getEnergia(), caso);
            assertEquals(80, mascota.getFelicidad(), caso);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Perro", "Gato", "Muñeca", "Cuyo"})
    void jugarAumentaHambreYFelicidadYGastaEnergia(String tipo) {
        Set<Integer> gananciasDeFelicidad = new HashSet<>();
        Set<Integer> costosDeEnergia = new HashSet<>();
        for (TipoJuego juego : crearControlador(tipo).obtenerMascota(0).obtenerJuegos()) {
            ControladorTamagotchi controlador = prepararConDiasSinCuidado(tipo, 3);
            Tamagotchi mascota = controlador.obtenerMascota(0);
            String caso = tipo + " con " + juego;

            assertTrue(controlador.jugarConMascota(0, juego), caso);
            assertEquals(3, mascota.getEdad(), caso);
            assertEquals(40, mascota.getHambre(), caso);
            assertTrue(mascota.getFelicidad() > 70, caso);
            assertTrue(mascota.getEnergia() < 70, caso);
            assertTrue(gananciasDeFelicidad.add(mascota.getFelicidad() - 70), caso);
            assertTrue(costosDeEnergia.add(70 - mascota.getEnergia()), caso);
        }
    }

    @ParameterizedTest
    @ValueSource(strings = {"Perro", "Gato", "Muñeca", "Cuyo"})
    void comerYJugarRespetanLosLimitesDeUnaMascotaNueva(String tipo) {
        ControladorTamagotchi controlador = crearControlador(tipo);
        Tamagotchi mascota = controlador.obtenerMascota(0);

        assertTrue(controlador.alimentarMascota(0, mascota.obtenerAlimentos().getFirst()));
        assertEquals(0, mascota.getHambre());
        assertEquals(100, mascota.getEnergia());
        assertEquals(100, mascota.getFelicidad());
        assertTrue(controlador.jugarConMascota(0, mascota.obtenerJuegos().getFirst()));
        assertEquals(10, mascota.getHambre());
        assertEquals(100, mascota.getFelicidad());
        assertTrue(mascota.getEnergia() < 100);
        assertEquals(0, mascota.getEdad());
    }

    @Test
    void cadaEspecieTieneTresOpcionesDistintasDeComidaYJuego() {
        Set<TipoAlimento> alimentos = new HashSet<>();
        Set<TipoJuego> juegos = new HashSet<>();
        for (String tipo : List.of("Perro", "Gato", "Muñeca", "Cuyo")) {
            Tamagotchi mascota = crearControlador(tipo).obtenerMascota(0);
            assertEquals(3, mascota.obtenerAlimentos().size(), tipo);
            assertEquals(3, mascota.obtenerJuegos().size(), tipo);
            assertEquals(EnumSet.allOf(TipoAlimento.Categoria.class),
                    mascota.obtenerAlimentos().stream().map(TipoAlimento::getCategoria).collect(Collectors.toSet()), tipo);
            assertEquals(EnumSet.allOf(TipoJuego.Categoria.class),
                    mascota.obtenerJuegos().stream().map(TipoJuego::getCategoria).collect(Collectors.toSet()), tipo);
            for (TipoAlimento alimento : mascota.obtenerAlimentos()) {
                assertTrue(alimentos.add(alimento), alimento.name());
            }
            for (TipoJuego juego : mascota.obtenerJuegos()) {
                assertTrue(juegos.add(juego), juego.name());
            }
        }
    }

    @Test
    void lasOpcionesDeOtraEspecieSeRechazanSinCambiarElEstado() {
        ControladorTamagotchi controlador = crearControlador("Muñeca");
        Tamagotchi mascota = controlador.obtenerMascota(0);
        assertTrue(controlador.jugarConMascota(0, TipoJuego.PINTAR));
        String estado = mascota.mostrarEstado();

        assertFalse(controlador.alimentarMascota(0, TipoAlimento.CROQUETAS));
        assertFalse(controlador.jugarConMascota(0, TipoJuego.PELOTA));
        mascota.alimentar(TipoAlimento.CROQUETAS);
        mascota.jugar(TipoJuego.PELOTA);

        assertEquals(estado, mascota.mostrarEstado());
    }

    @Test
    void dormirAvanzaSoloElDiaDeLaMascotaSeleccionada() {
        ControladorTamagotchi controlador = crearControlador("Gato");
        assertTrue(controlador.crearMascota("Cuyo", "Pipo"));
        Tamagotchi gato = controlador.obtenerMascota(0);
        Tamagotchi cuyo = controlador.obtenerMascota(1);

        assertFalse(controlador.alimentarMascota(0, null));
        assertFalse(controlador.jugarConMascota(-1, TipoJuego.PELOTA));
        assertTrue(controlador.alimentarMascota(0, TipoAlimento.ATUN));
        assertTrue(controlador.jugarConMascota(0, TipoJuego.PLUMA));
        assertTrue(controlador.realizarComportamientoEspecial(0));
        assertEquals(0, gato.getEdad());
        assertEquals(0, cuyo.getEdad());
        String estadoInicialDelCuyo = cuyo.toString();

        assertTrue(controlador.dormirMascota(0));
        assertEquals(1, gato.getEdad());
        assertEquals(20, gato.getHambre());
        assertEquals(100, gato.getEnergia());
        assertEquals(100, gato.getFelicidad());
        assertEquals(0, gato.getDiasSinDormir());
        assertEquals(0, gato.getDiasSinJugar());

        assertEquals(estadoInicialDelCuyo, cuyo.toString());

        assertFalse(controlador.dormirMascota(0));
        assertEquals(1, gato.getEdad());
        assertEquals(0, cuyo.getEdad());

        String estadoDelGatoDormido = gato.toString();
        assertTrue(controlador.dormirMascota(1));
        assertEquals(estadoDelGatoDormido, gato.toString());
        assertEquals(1, cuyo.getEdad());
        assertEquals(10, cuyo.getHambre());
        assertEquals(100, cuyo.getEnergia());
        assertEquals(90, cuyo.getFelicidad());
        assertTrue(controlador.despertarMascota(0));
        assertEquals(1, gato.getEdad());
    }

    @Test
    void toStringMuestraLosAtributosYElEstadoDeLaEspecie() {
        ControladorTamagotchi controlador = crearControlador("Gato");
        Tamagotchi mascota = controlador.obtenerMascota(0);
        assertTrue(controlador.realizarComportamientoEspecial(0));

        String descripcion = mascota.toString();
        for (String texto : List.of("Tama", "Tipo: Gato", "Edad: 0 días", "Hambre: 0/100",
                "Energía: 100/100", "Felicidad: 100/100", "Durmiendo: No", "Estado:", "Ronroneando: sí")) {
            assertTrue(descripcion.contains(texto), texto);
        }
    }

    @Test
    void elHambreSoloMataAlLlegarAlMaximo() {
        ControladorTamagotchi controlador = crearControlador("Cuyo");
        Tamagotchi mascota = controlador.obtenerMascota(0);
        for (int dia = 0; dia < 4; dia++) {
            assertTrue(controlador.jugarConMascota(0, TipoJuego.CORRAL));
            assertTrue(controlador.dormirMascota(0));
            assertTrue(controlador.despertarMascota(0));
        }
        assertTrue(controlador.dormirMascota(0));
        assertTrue(controlador.despertarMascota(0));
        assertEquals(90, mascota.getHambre());
        assertTrue(mascota.estaVivo());
        assertTrue(mascota.obtenerAvisosDeCuidado().contains("Necesita comer."));

        assertTrue(controlador.alimentarMascota(0, TipoAlimento.HENO));
        assertEquals(70, mascota.getHambre());
        assertTrue(mascota.estaVivo());
        assertTrue(controlador.jugarConMascota(0, TipoJuego.CORRAL));
        assertTrue(controlador.jugarConMascota(0, TipoJuego.CORRAL));
        assertTrue(mascota.estaVivo());
        assertTrue(controlador.jugarConMascota(0, TipoJuego.CORRAL));
        assertEquals(100, mascota.getHambre());
        assertEquals(5, mascota.getEdad());
        assertEquals(CausaMuerte.DESCUIDO, mascota.getCausaMuerte());
        assertTrue(mascota.getDescripcionMuerte().contains("hambre al máximo"));
        assertFalse(controlador.alimentarMascota(0, TipoAlimento.HENO));
        assertFalse(controlador.jugarConMascota(0, TipoJuego.CORRAL));
    }

    @Test
    void elAburrimientoSoloMataAlLlegarACeroFelicidad() {
        ControladorTamagotchi controlador = crearControlador("Gato");
        Tamagotchi mascota = controlador.obtenerMascota(0);

        for (int dia = 1; dia <= 10; dia++) {
            assertTrue(controlador.alimentarMascota(0, TipoAlimento.ATUN));
            assertTrue(controlador.dormirMascota(0));
            assertEquals(100 - 10 * dia, mascota.getFelicidad());
            if (dia < 10) {
                assertTrue(mascota.estaVivo());
                assertTrue(controlador.despertarMascota(0));
            }
        }
        assertEquals(10, mascota.getHambre());
        assertEquals(100, mascota.getEnergia());
        assertEquals(CausaMuerte.DESCUIDO, mascota.getCausaMuerte());
        assertTrue(mascota.getDescripcionMuerte().contains("felicidad en cero"));
    }

    @Test
    void laRecuperacionDeEnergiaDependeDelDescansoPropio() {
        ControladorTamagotchi controlador = prepararConDiasSinCuidado("Gato", 7);
        Tamagotchi mascota = controlador.obtenerMascota(0);
        assertEquals(30, mascota.getEnergiaMaxima());
        assertEquals(30, mascota.getFelicidadMaxima());

        assertTrue(controlador.jugarConMascota(0, TipoJuego.OVILLO));
        assertEquals(100, mascota.getFelicidadMaxima());
        assertTrue(mascota.getFelicidad() > 30);
        assertEquals(30, mascota.getEnergiaMaxima());
        assertEquals(7, mascota.getEdad());

        assertTrue(controlador.dormirMascota(0));
        assertEquals(100, mascota.getEnergiaMaxima());
        assertEquals(74, mascota.getEnergia());
        assertEquals(8, mascota.getEdad());
        assertTrue(controlador.crearMascota("Perro", "Toby"));
        assertTrue(controlador.dormirMascota(1));
        assertEquals(74, mascota.getEnergia());
        assertEquals(8, mascota.getEdad());

        assertTrue(controlador.despertarMascota(0));
        assertTrue(controlador.alimentarMascota(0, TipoAlimento.ATUN));
        assertTrue(controlador.dormirMascota(0));
        assertEquals(100, mascota.getEnergia());
        assertEquals(9, mascota.getEdad());
    }

    @Test
    void elCansancioSoloMataAlLlegarACeroEnergia() {
        ControladorTamagotchi controlador = crearControlador("Gato");
        Tamagotchi mascota = controlador.obtenerMascota(0);

        for (int dia = 1; dia <= 10; dia++) {
            assertTrue(controlador.alimentarMascota(0, TipoAlimento.ATUN));
            if (dia == 1 || dia == 4 || dia == 7) {
                assertTrue(controlador.jugarConMascota(0, TipoJuego.OVILLO));
            }
            mascota.avanzarDia();
            if (dia < 10) {
                assertTrue(mascota.estaVivo(), "Día " + dia);
            }
            if (dia == 9) {
                assertEquals(EstadoAnimo.MAL, mascota.obtenerEstadoAnimo());
                assertEquals(10, mascota.getEnergia());
                assertTrue(mascota.obtenerAvisosDeCuidado().contains("Necesita dormir."));
            }
        }
        assertEquals(10, mascota.getEdad());
        assertEquals(0, mascota.getEnergia());
        assertEquals(CausaMuerte.DESCUIDO, mascota.getCausaMuerte());
        assertTrue(mascota.getDescripcionMuerte().contains("energía en cero"));
    }

    @Test
    void cuidarLasTresNecesidadesPermiteLlegarALaVejez() {
        ControladorTamagotchi controlador = crearControlador("Perro");
        Tamagotchi mascota = controlador.obtenerMascota(0);

        for (int dia = 1; dia <= 60; dia++) {
            assertTrue(controlador.alimentarMascota(0, TipoAlimento.CROQUETAS));
            assertTrue(controlador.jugarConMascota(0, TipoJuego.PASEO));
            assertTrue(controlador.dormirMascota(0));
            assertEquals(dia, mascota.getEdad());
            if (dia < 60) {
                assertTrue(controlador.despertarMascota(0));
            }
        }
        assertEquals(CausaMuerte.VEJEZ, mascota.getCausaMuerte());
        assertEquals("Vejez", mascota.getDescripcionMuerte());
    }

    private ControladorTamagotchi crearControlador(String tipo) {
        ControladorTamagotchi controlador = new ControladorTamagotchi(mensaje -> {});
        assertTrue(controlador.crearMascota(tipo, "Tama"));
        return controlador;
    }

    private ControladorTamagotchi prepararConDiasSinCuidado(String tipo, int dias) {
        ControladorTamagotchi controlador = crearControlador(tipo);
        Tamagotchi mascota = controlador.obtenerMascota(0);
        for (int dia = 0; dia < dias; dia++) {
            mascota.avanzarDia();
        }
        return controlador;
    }
}
