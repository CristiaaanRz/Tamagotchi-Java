package org.uabc.ic.Modelo;

public class Perro extends Tamagotchi implements TamagotchiActions{

    private int nivelAgresividad;
    private int nivelImperactividad;


        public Perro(String nombre, int hambre, int salud, int felicidad, int energia){
            super(nombre, hambre, salud, felicidad, energia);
            nivelAgresividad = 10;
            nivelImperactividad = 10;
        }

        public Perro(){
            super("Ramiro", 0, 10, 5, 5);
        }

    @Override
    public void comer(int alimento) {
        System.out.println("Comiendo" + alimento);
        hambre--;
        energia++;
    }

    @Override
    public int jugar(int juego) {
        return 0;
    }

    @Override
    public int dormir(int horasSueno) {
        return 0;
    }

    @Override
    public boolean morir() {
        return false;
    }

    @Override
    public int sanar() {
        return 0;
    }
}
