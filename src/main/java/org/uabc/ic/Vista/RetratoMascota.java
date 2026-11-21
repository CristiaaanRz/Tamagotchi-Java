package org.uabc.ic.Vista;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

final class RetratoMascota extends Canvas {
    private static final Color CONTORNO = Color.web("#4a3035");

    private enum Retrato {
        PERRO("#f4d8aa", "#c98253", "#f9deb0", "#e99a8b", "#a96648", """
                ....oo........oo....
                ...oppooooooooppo...
                ..oppspppppppspppo..
                .opospppppppppsopo.
                .opsopplppplppospo.
                .opsopofpppfopospo.
                ..ooopoplllpopooo..
                ....oplllolllpo....
                ....oplolloolpo....
                .....oolllloo.....
                .....opppapppo.....
                ....opppaaapppo....
                ...opplpwpplppo....
                ...opllpsspllpo....
                ....oooooooooo....
                """),
        GATO("#f4d1b4", "#df925e", "#ffe8c8", "#e7aaa2", "#b87558", """
                ...o..........o....
                ..opo........opo...
                ..oapo......opao...
                ..oaapoooooopao...
                ..oppppsppppppo...
                ..oppspppspsppo...
                .opppfppppfppppo..
                .oplppplwppplpo..
                .oplllololllpo...
                ..opllllllllpo...
                ...ooopppooo.....
                ....oppplppo..oo.
                ...opppplpppo.opo
                ...opllpsplpoopo
                ....ooooooooooo.
                """),
        MUNECA("#ecd2dd", "#a46a52", "#f9d5ad", "#c76f80", "#845043", """
                ......ooo..........
                ....oopppoo........
                ...opppppppo.......
                ..oppppppsppo......
                ..oppspppllpo......
                ..opsplllllpo......
                ..oppolfllflo......
                ..opplllalplo......
                ...opllllllo.......
                ....ooollooo.......
                ....oaaoaao........
                ...olaaaaalo.......
                ...olawawalao......
                ....oaaaaao........
                ....oololoo........
                """),
        CUYO("#e9d7a9", "#bd804c", "#f9e4be", "#e5a49a", "#9f633d", """
                .....oo.....oo.....
                ....oapo...opao....
                ....oppooooopo....
                ...oppppplppppo...
                ..ospppplllppppo..
                .oppsppplllpppppo.
                .opppppplllpppppo.
                .oppppfplllfppppo.
                .opplllllallllppo.
                .opllwlllolllwppo.
                .oplllllololllppo.
                ..oplllllllllpo..
                ...opplpplppo....
                ....oloooolo.....
                .....o....o......
                """);

        private final Color fondo;
        private final Color cuerpo;
        private final Color claro;
        private final Color acento;
        private final Color sombra;
        private final String[] pixeles;
        private final int anchoEnPixeles;

        Retrato(String fondo, String cuerpo, String claro, String acento, String sombra, String dibujo) {
            this.fondo = Color.web(fondo);
            this.cuerpo = Color.web(cuerpo);
            this.claro = Color.web(claro);
            this.acento = Color.web(acento);
            this.sombra = Color.web(sombra);
            this.pixeles = dibujo.stripTrailing().split("\n");
            int anchoMaximo = 0;
            for (String linea : pixeles) {
                anchoMaximo = Math.max(anchoMaximo, linea.length());
            }
            this.anchoEnPixeles = anchoMaximo;
        }

        private Color color(char pixel) {
            return switch (pixel) {
                case 'o', 'f' -> CONTORNO;
                case 'p' -> cuerpo;
                case 'l' -> claro;
                case 'a' -> acento;
                case 's' -> sombra;
                case 'w' -> Color.web("#fff1d6");
                default -> null;
            };
        }
    }

    RetratoMascota(int ancho, int alto) {
        super(ancho, alto);
        setMouseTransparent(true);
    }

    void dibujar(String tipo, boolean dormida, boolean viva) {
        Retrato retrato = switch (tipo) {
            case "Perro" -> Retrato.PERRO;
            case "Muñeca" -> Retrato.MUNECA;
            case "Cuyo" -> Retrato.CUYO;
            default -> Retrato.GATO;
        };
        GraphicsContext pincel = getGraphicsContext2D();
        double ancho = getWidth();
        double alto = getHeight();
        boolean miniatura = ancho < 60;
        pincel.clearRect(0, 0, ancho, alto);
        pincel.setGlobalAlpha(viva ? 1 : 0.46);
        pincel.setFill(retrato.fondo);
        pincel.fillRoundRect(0, 0, ancho, alto, miniatura ? 12 : 28, miniatura ? 12 : 28);

        if (!miniatura) {
            pincel.setFill(Color.web("#fff6e5", 0.62));
            pincel.fillOval(ancho * 0.21, alto * 0.10, ancho * 0.59, alto * 0.68);
            pincel.setFill(Color.web("#966d54", 0.18));
            pincel.fillOval(ancho * 0.18, alto * 0.78, ancho * 0.64, alto * 0.09);
            pincel.setFill(Color.web("#fff8e5"));
            destello(pincel, 24, 29, 3);
            destello(pincel, (int) ancho - 28, 45, 4);
            destello(pincel, (int) ancho - 33, (int) alto - 35, 2);
            destello(pincel, 35, (int) alto - 43, 2);
        }

        int escala = miniatura ? 2 : 6;
        double inicioX = Math.floor((ancho - retrato.anchoEnPixeles * escala) / 2);
        double inicioY = Math.floor((alto - retrato.pixeles.length * escala) / 2 + (miniatura ? 0 : 8));
        for (int fila = 0; fila < retrato.pixeles.length; fila++) {
            String linea = retrato.pixeles[fila];
            for (int columna = 0; columna < linea.length(); columna++) {
                char pixel = linea.charAt(columna);
                Color color = retrato.color(pixel);
                if (color != null) {
                    pincel.setFill(color);
                    double x = inicioX + columna * escala;
                    double y = inicioY + fila * escala;
                    if (pixel == 'f' && dormida) {
                        pincel.fillRect(x, y + escala - 2, escala, 2);
                    } else {
                        pincel.fillRect(x, y, escala, escala);
                    }
                }
            }
        }
        pincel.setGlobalAlpha(1);
    }

    private void destello(GraphicsContext pincel, int x, int y, int lado) {
        pincel.fillRect(x, y - lado, lado, lado * 3);
        pincel.fillRect(x - lado, y, lado * 3, lado);
    }
}
