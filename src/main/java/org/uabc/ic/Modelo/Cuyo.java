package org.uabc.ic.Modelo;

public class Cuyo extends Tamagotchi implements TamagotchiActions {
    private int maderaRoida;

    public Cuyo(String nombre, int hambre, int salud, int felicidad, int energia, int maderaRoida) {
        super(nombre, hambre, salud, felicidad, energia);
        this.maderaRoida = maderaRoida;
    }

    public Cuyo(int maderaRoida) {
        this.maderaRoida = maderaRoida;
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
