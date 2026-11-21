package org.uabc.ic.Vista;

import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.IntConsumer;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import org.uabc.ic.Controlador.ControladorTamagotchi;
import org.uabc.ic.Modelo.Cuyo;
import org.uabc.ic.Modelo.EstadoAnimo;
import org.uabc.ic.Modelo.Gato;
import org.uabc.ic.Modelo.Muneca;
import org.uabc.ic.Modelo.Perro;
import org.uabc.ic.Modelo.Tamagotchi;
import org.uabc.ic.Modelo.TipoAlimento;
import org.uabc.ic.Modelo.TipoJuego;

public class VistaTamagotchiFX extends Application {
    private static final String TEXTO_DORMIR = "Dormir y comenzar el día siguiente";

    private final ListView<Tamagotchi> mascotas = new ListView<>();
    private final ListView<String> actividad = new ListView<>();
    private final RetratoMascota retrato = new RetratoMascota(176, 160);
    private final Label nombreMascota = etiqueta("", "nombre-mascota");
    private final Label resumenMascota = etiqueta("", "resumen");
    private final Label estado = etiqueta("", "estado");
    private final Label detalle = etiqueta("", "detalle");
    private final Label aviso = etiqueta("", "aviso");
    private final Label diasDeCuidado = etiqueta("", "resumen");
    private final Medidor hambre = new Medidor("Hambre", "hambre");
    private final Medidor energia = new Medidor("Energía", "energia");
    private final Medidor felicidad = new Medidor("Felicidad", "felicidad");

    private ControladorTamagotchi controlador;
    private ComboBox<TipoAlimento> alimentos;
    private ComboBox<TipoJuego> juegos;
    private Button alimentar;
    private Button jugar;
    private Button sueno;
    private Button comportamiento;

    @Override
    public void start(Stage ventana) {
        controlador = new ControladorTamagotchi(mensaje -> {
            actividad.getItems().add(mensaje);
            actividad.scrollTo(actividad.getItems().size() - 1);
        });

        BorderPane raiz = new BorderPane();
        raiz.setTop(encabezado());
        raiz.setCenter(contenido());

        Scene escena = new Scene(raiz, 1000, 742);
        escena.getStylesheets().add(getClass().getResource("tamagotchi.css").toExternalForm());
        ventana.setTitle("Tamagotchi");
        ventana.setMinWidth(860);
        ventana.setMinHeight(700);
        ventana.setScene(escena);

        mascotas.getSelectionModel().selectedItemProperty().addListener(
                (observable, anterior, actual) -> actualizarInterfaz());
        actualizarInterfaz();
        ventana.show();
    }

    private HBox encabezado() {
        HBox barra = new HBox(etiqueta("Tamagotchi", "titulo"));
        barra.setAlignment(Pos.CENTER_LEFT);
        barra.getStyleClass().add("encabezado");
        return barra;
    }

    private HBox contenido() {
        VBox barraLateral = barraLateral();
        VBox registro = panelActividad();
        VBox principal = new VBox(12, fichaMascota(), panelNecesidades(), panelCuidados(), registro);
        HBox.setHgrow(principal, Priority.ALWAYS);
        VBox.setVgrow(registro, Priority.ALWAYS);
        return new HBox(18, barraLateral, principal);
    }

    private VBox barraLateral() {
        mascotas.setAccessibleText("Mis mascotas");
        mascotas.setPlaceholder(etiqueta("Aún no hay mascotas", "resumen"));
        mascotas.setCellFactory(lista -> new ListCell<>() {
            private final RetratoMascota miniatura = new RetratoMascota(42, 42);
            private final Label nombre = etiqueta("", "nombre-lista");
            private final Label especie = etiqueta("", "especie-lista");
            private final HBox fila = new HBox(10, miniatura, new VBox(2, nombre, especie));

            @Override
            protected void updateItem(Tamagotchi mascota, boolean vacia) {
                super.updateItem(mascota, vacia);
                if (vacia || mascota == null) {
                    setGraphic(null);
                    setAccessibleText(null);
                    return;
                }
                nombre.setText(mascota.getNombre());
                nombre.setMaxWidth(126);
                especie.setText(mascota.estaVivo() ? mascota.getTipoVisible() : "Sin vida");
                miniatura.dibujar(mascota.getTipoVisible(), mascota.isDurmiendo(), mascota.estaVivo());
                fila.setAlignment(Pos.CENTER_LEFT);
                setGraphic(fila);
                setAccessibleText(mascota.getNombre() + ", " + especie.getText());
            }
        });
        VBox.setVgrow(mascotas, Priority.ALWAYS);

        VBox barra = new VBox(12, etiqueta("Mis mascotas", "subtitulo"), mascotas, formularioCreacion());
        barra.getStyleClass().add("barra-lateral");
        barra.setPrefWidth(228);
        barra.setMinWidth(228);
        return barra;
    }

    private VBox formularioCreacion() {
        ComboBox<String> tipos = selector(List.of("Perro", "Gato", "Muñeca", "Cuyo"), Function.identity(), "Especie");
        TextField nombre = new TextField();
        nombre.setPromptText("Nombre de la mascota");
        nombre.setAccessibleText("Nombre de la mascota");

        Button crear = boton("Crear mascota", "terracota", () -> {
            if (controlador.crearMascota(tipos.getValue(), nombre.getText())) {
                int indice = controlador.cantidadMascotas() - 1;
                mascotas.getItems().add(controlador.obtenerMascota(indice));
                mascotas.getSelectionModel().select(indice);
                mascotas.scrollTo(indice);
                nombre.clear();
            } else {
                nombre.requestFocus();
            }
        });
        crear.setMaxWidth(Double.MAX_VALUE);
        nombre.setOnAction(evento -> crear.fire());
        VBox formulario = new VBox(8, etiqueta("Nueva mascota", "subtitulo"), tipos, nombre, crear);
        formulario.getStyleClass().add("formulario");
        return formulario;
    }

    private HBox fichaMascota() {
        nombreMascota.setId("nombre-mascota");
        estado.setId("estado-mascota");
        aviso.setId("aviso-cuidado");
        detalle.setWrapText(true);
        aviso.setWrapText(true);
        aviso.managedProperty().bind(aviso.visibleProperty());
        VBox informacion = new VBox(5, nombreMascota, resumenMascota, estado, detalle, aviso);
        informacion.setAlignment(Pos.CENTER_LEFT);
        informacion.setMinWidth(0);
        HBox.setHgrow(informacion, Priority.ALWAYS);

        HBox ficha = new HBox(20, retrato, informacion);
        ficha.setAlignment(Pos.CENTER_LEFT);
        ficha.getStyleClass().add("ficha");
        return ficha;
    }

    private VBox panelNecesidades() {
        HBox indicadores = new HBox(10, hambre, energia, felicidad);
        for (var indicador : indicadores.getChildren()) {
            HBox.setHgrow(indicador, Priority.ALWAYS);
        }
        diasDeCuidado.setId("dias-de-cuidado");
        VBox panel = new VBox(7, indicadores, diasDeCuidado);
        return panel;
    }

    private VBox panelCuidados() {
        alimentos = selector(List.of(), TipoAlimento::getNombreVisible, "Alimento");
        juegos = selector(List.of(), TipoJuego::getNombreVisible, "Juego");

        alimentar = boton("Alimentar", "coral", () -> interactuar(
                indice -> controlador.alimentarMascota(indice, alimentos.getValue())));
        jugar = boton("Jugar", "verde", () -> interactuar(
                indice -> controlador.jugarConMascota(indice, juegos.getValue())));
        sueno = boton(TEXTO_DORMIR, "morado", () -> interactuar(indice -> {
            if (mascotas.getSelectionModel().getSelectedItem().isDurmiendo()) {
                controlador.despertarMascota(indice);
            } else {
                controlador.dormirMascota(indice);
            }
        }));
        comportamiento = boton("Comportamiento especial", "secundario",
                () -> interactuar(controlador::realizarComportamientoEspecial));

        HBox.setHgrow(alimentos, Priority.ALWAYS);
        HBox.setHgrow(juegos, Priority.ALWAYS);
        HBox opciones = new HBox(10, new HBox(6, alimentos, alimentar), new HBox(6, juegos, jugar));
        for (var opcion : opciones.getChildren()) {
            HBox.setHgrow(opcion, Priority.ALWAYS);
        }

        VBox panel = new VBox(9, etiqueta("Cuidados", "subtitulo"), opciones, new HBox(8, sueno, comportamiento));
        panel.getStyleClass().add("panel");
        return panel;
    }

    private VBox panelActividad() {
        actividad.setAccessibleText("Actividad");
        actividad.setFocusTraversable(false);
        actividad.setPlaceholder(etiqueta("Sin actividad", "resumen"));
        actividad.setPrefHeight(90);
        VBox.setVgrow(actividad, Priority.ALWAYS);
        actividad.getStyleClass().add("actividad");

        VBox panel = new VBox(4, etiqueta("Actividad", "subtitulo"), actividad);
        panel.getStyleClass().add("panel");
        return panel;
    }

    private void interactuar(IntConsumer accion) {
        int indice = mascotas.getSelectionModel().getSelectedIndex();
        if (indice >= 0) {
            accion.accept(indice);
            actualizarInterfaz();
        }
    }

    private void actualizarInterfaz() {
        Tamagotchi mascota = mascotas.getSelectionModel().getSelectedItem();
        actualizarOpciones(alimentos, mascota == null ? List.of() : mascota.obtenerAlimentos());
        actualizarOpciones(juegos, mascota == null ? List.of() : mascota.obtenerJuegos());
        boolean viva = mascota != null && mascota.estaVivo();
        boolean despierta = viva && !mascota.isDurmiendo();
        boolean puedeJugar = viva && mascota.puedeJugar();
        alimentos.setDisable(!despierta);
        alimentar.setDisable(!despierta);
        juegos.setDisable(!puedeJugar);
        jugar.setDisable(!puedeJugar);
        sueno.setDisable(!viva);
        sueno.setText(viva && mascota.isDurmiendo() ? "Despertar" : TEXTO_DORMIR);
        comportamiento.setDisable(!despierta);

        actualizarFicha(mascota);
        mascotas.refresh();
    }

    private void actualizarFicha(Tamagotchi mascota) {
        boolean seleccionada = mascota != null;
        hambre.actualizar(seleccionada ? mascota.getHambre() : -1, 100, "");
        energia.actualizar(seleccionada ? mascota.getEnergia() : -1,
                seleccionada ? mascota.getEnergiaMaxima() : 100, "cansancio");
        felicidad.actualizar(seleccionada ? mascota.getFelicidad() : -1,
                seleccionada ? mascota.getFelicidadMaxima() : 100, "aburrimiento");
        retrato.dibujar(seleccionada ? mascota.getTipoVisible() : "Gato",
                seleccionada && mascota.isDurmiendo(), !seleccionada || mascota.estaVivo());

        estado.setVisible(seleccionada);
        estado.setManaged(seleccionada);
        if (!seleccionada) {
            nombreMascota.setText("Crea tu primera mascota");
            resumenMascota.setText("");
            detalle.setText("");
            diasDeCuidado.setText("");
            aviso.setVisible(false);
            return;
        }

        nombreMascota.setText(mascota.getNombre());
        resumenMascota.setText(mascota.getTipoVisible() + "  ·  " + mascota.getEdad()
                + (mascota.getEdad() == 1 ? " día de edad" : " días de edad"));
        EstadoAnimo animo = mascota.obtenerEstadoAnimo();
        estado.getStyleClass().setAll("label", "estado", animo.name().toLowerCase(Locale.ROOT));
        estado.setText(switch (animo) {
            case BIEN -> mascota.isDurmiendo() ? "Durmiendo" : "Está bien";
            case NECESITA_ATENCION -> "Necesita atención";
            case MAL -> "Está enferma";
            case MUERTO -> "Sin vida";
        });
        detalle.setText(mascota.estaVivo() ? detalleDeEspecie(mascota)
                : "Causa de muerte: " + mascota.getDescripcionMuerte());
        aviso.setText(String.join("  ", mascota.obtenerAvisosDeCuidado()));
        aviso.setVisible(!aviso.getText().isEmpty());
        diasDeCuidado.setText("Días sin jugar: " + mascota.getDiasSinJugar()
                + "     ·     Días sin dormir: " + mascota.getDiasSinDormir()
                + (mascota.isDurmiendo() && animo != EstadoAnimo.BIEN ? "     ·     Durmiendo" : ""));
    }

    private static String detalleDeEspecie(Tamagotchi mascota) {
        return switch (mascota) {
            case Perro perro -> "Ladridos: " + perro.getNivelLadridos();
            case Gato gato -> gato.isRonroneando() ? "Está ronroneando" : "Sin ronronear";
            case Cuyo cuyo -> "Trozos de madera roída: " + cuyo.getMaderaRoida();
            case Muneca muneca -> muneca.isLlorando() ? "Está llorando" : "Sin llanto";
            default -> "";
        };
    }

    private static Button boton(String texto, String estilo, Runnable accion) {
        Button boton = new Button(texto);
        boton.getStyleClass().add(estilo);
        boton.setOnAction(evento -> accion.run());
        return boton;
    }

    private static Label etiqueta(String texto, String estilo) {
        Label etiqueta = new Label(texto);
        etiqueta.getStyleClass().add(estilo);
        return etiqueta;
    }

    private static <T> ComboBox<T> selector(List<T> opciones, Function<T, String> nombreVisible, String etiqueta) {
        ComboBox<T> selector = new ComboBox<>(FXCollections.observableArrayList(opciones));
        selector.setAccessibleText(etiqueta);
        selector.setMaxWidth(Double.MAX_VALUE);
        selector.setConverter(new StringConverter<>() {
            @Override
            public String toString(T opcion) {
                return opcion == null ? "" : nombreVisible.apply(opcion);
            }

            @Override
            public T fromString(String texto) {
                return null;
            }
        });
        selector.getSelectionModel().selectFirst();
        return selector;
    }

    private static <T> void actualizarOpciones(ComboBox<T> selector, List<T> opciones) {
        if (selector.getItems().equals(opciones)) {
            return;
        }
        selector.getSelectionModel().clearSelection();
        selector.setValue(null);
        selector.getItems().setAll(opciones);
        selector.getSelectionModel().selectFirst();
    }

    private static final class Medidor extends VBox {
        private final ProgressBar barra = new ProgressBar(0);
        private final Label valor = etiqueta("—", "valor");
        private final Label limite = etiqueta("", "limite-medidor");

        private Medidor(String nombre, String color) {
            getStyleClass().addAll("medidor", color);
            setSpacing(7);
            setMinWidth(0);
            setPrefWidth(180);

            barra.setMaxWidth(Double.MAX_VALUE);
            barra.setAccessibleText(nombre);
            Region espacio = new Region();
            HBox.setHgrow(espacio, Priority.ALWAYS);
            HBox texto = new HBox(4, etiqueta(nombre, "nombre-medidor"), espacio, valor);
            texto.setAlignment(Pos.CENTER_LEFT);
            limite.managedProperty().bind(limite.visibleProperty());
            getChildren().addAll(texto, barra, limite);
        }

        private void actualizar(int nivel, int maximo, String causa) {
            valor.setText(nivel < 0 ? "—" : nivel + "/100");
            barra.setProgress(Math.max(nivel, 0) / 100.0);
            limite.setVisible(maximo < 100);
            limite.setText(maximo < 100 ? "Límite base: " + maximo + " por " + causa : "");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
