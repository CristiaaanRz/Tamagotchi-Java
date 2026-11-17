package org.uabc.ic.Modelo;

public class Muneca extends Tamagotchi implements TamagotchiActions {
    private boolean llorando;

    public Muneca(String nombre, int hambre, int salud, int felicidad, int energia, boolean llorando) {
        super(nombre, hambre, salud, felicidad, energia);
        this.llorando = llorando;
    }

    public Muneca() {
        super("Si", 0, 10, 5, 5);

    }

    public void expresarEmocion() {

    }

    public void llorar() {
        
    }

    @Override
    public void comer(int alimento) {
        // TODO Auto-generated method stub

    }

    @Override
    public int dormir(int horasSueno) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int jugar(int juego) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean morir() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int sanar() {
        // TODO Auto-generated method stub
        return 0;
    }

}
